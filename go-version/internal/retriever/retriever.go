// Package retriever implements the SAAI Expert Knowledge Retriever.
//
// It loads all expert methodology documents from the /docs/experts directory,
// chunks them into passages, and exposes a Query interface so agents can
// retrieve the most relevant expert knowledge for a given SEO task.
//
// Architecture:
//
//	ExpertDoc → []Chunk → InMemoryStore → BM25Retriever
//	                                    ↕
//	                              SemanticRetriever (optional, via embeddings)
package retriever

import (
	"bufio"
	"fmt"
	"math"
	"os"
	"path/filepath"
	"sort"
	"strings"
)

// ─── Types ────────────────────────────────────────────────────────────────────

// ExpertDoc is a fully loaded expert methodology document.
type ExpertDoc struct {
	ExpertName string // e.g. "Koray Tuğberk GÜBÜR"
	AgentID    string // e.g. "SemanticTopicalAuthorityAgent"
	FilePath   string
	Content    string
}

// Chunk is a paragraph-level passage from an ExpertDoc.
type Chunk struct {
	ExpertName string
	AgentID    string
	Text       string
	Source     string // filepath + heading context
}

// Result is a retrieved chunk with a relevance score.
type Result struct {
	Chunk
	Score float64
}

// ─── ExpertStore ─────────────────────────────────────────────────────────────

// ExpertStore holds all loaded expert documents and their derived chunks.
type ExpertStore struct {
	Docs   []ExpertDoc
	Chunks []Chunk
}

// expertAgentMap maps expert markdown file stems to their AgentIDs.
var expertAgentMap = map[string]string{
	"koray-tugberk-gubur": "SemanticTopicalAuthorityAgent",
	"jamie-indigo":        "JSRenderingCrawlAgent",
	"jono-alderson":       "TechnicalSpeedAgent",
	"lily-ray":            "EEATContentAgent",
	"patrick-stox":        "OnPageLogAnalysisAgent",
	"marie-haynes":        "GoogleUpdateAgent",
	"aleyda-solis":        "InternationalSEOAgent",
	"barry-adams":         "NewsSEOAgent",
}

// LoadExpertStore reads all markdown files from docsDir and builds the store.
// docsDir should point to the /docs/experts directory.
func LoadExpertStore(docsDir string) (*ExpertStore, error) {
	entries, err := os.ReadDir(docsDir)
	if err != nil {
		return nil, fmt.Errorf("retriever: cannot read docs dir %q: %w", docsDir, err)
	}

	store := &ExpertStore{}

	for _, entry := range entries {
		if entry.IsDir() || !strings.HasSuffix(entry.Name(), ".md") {
			continue
		}

		filePath := filepath.Join(docsDir, entry.Name())
		content, readErr := os.ReadFile(filePath)
		if readErr != nil {
			return nil, fmt.Errorf("retriever: reading %q: %w", filePath, readErr)
		}

		stem := strings.TrimSuffix(entry.Name(), ".md")
		agentID, ok := expertAgentMap[stem]
		if !ok {
			agentID = "UnknownAgent"
		}

		// Extract expert name from first H1 heading
		expertName := extractH1(string(content))
		if expertName == "" {
			expertName = stem
		}

		doc := ExpertDoc{
			ExpertName: expertName,
			AgentID:    agentID,
			FilePath:   filePath,
			Content:    string(content),
		}
		store.Docs = append(store.Docs, doc)

		// Chunk the document into paragraphs
		chunks := chunkDocument(doc)
		store.Chunks = append(store.Chunks, chunks...)
	}

	return store, nil
}

// ─── Chunking ─────────────────────────────────────────────────────────────────

// chunkDocument splits an ExpertDoc into non-empty paragraph chunks.
func chunkDocument(doc ExpertDoc) []Chunk {
	var chunks []Chunk
	currentHeading := ""

	scanner := bufio.NewScanner(strings.NewReader(doc.Content))
	var paragraphLines []string

	flush := func() {
		text := strings.TrimSpace(strings.Join(paragraphLines, "\n"))
		if len(text) > 30 { // ignore tiny fragments
			chunks = append(chunks, Chunk{
				ExpertName: doc.ExpertName,
				AgentID:    doc.AgentID,
				Text:       text,
				Source:     doc.FilePath + " → " + currentHeading,
			})
		}
		paragraphLines = nil
	}

	for scanner.Scan() {
		line := scanner.Text()

		// Track current heading context
		if strings.HasPrefix(line, "#") {
			flush()
			currentHeading = strings.TrimLeft(line, "# ")
			continue
		}

		// Blank line = paragraph boundary
		if strings.TrimSpace(line) == "" {
			flush()
			continue
		}

		paragraphLines = append(paragraphLines, line)
	}
	flush()

	return chunks
}

// extractH1 returns the text of the first H1 heading in a markdown document.
func extractH1(content string) string {
	for _, line := range strings.Split(content, "\n") {
		if strings.HasPrefix(line, "# ") {
			return strings.TrimPrefix(line, "# ")
		}
	}
	return ""
}

// ─── BM25 Retriever ───────────────────────────────────────────────────────────

// Retriever provides BM25-based lexical search over the expert knowledge base.
// BM25 constants (Okapi BM25)
const (
	bm25K1 = 1.5
	bm25B  = 0.75
)

// Retriever wraps an ExpertStore with BM25 scoring capability.
type Retriever struct {
	store       *ExpertStore
	tf          []map[string]float64 // term frequency per chunk
	df          map[string]int       // document frequency per term
	avgLen      float64              // average chunk length (in words)
}

// New creates a Retriever from a loaded ExpertStore and pre-computes BM25 stats.
func New(store *ExpertStore) *Retriever {
	r := &Retriever{
		store: store,
		df:    make(map[string]int),
	}

	var totalLen float64
	r.tf = make([]map[string]float64, len(store.Chunks))

	for i, chunk := range store.Chunks {
		tokens := tokenize(chunk.Text)
		totalLen += float64(len(tokens))
		freq := make(map[string]float64)
		seen := make(map[string]bool)

		for _, tok := range tokens {
			freq[tok]++
			if !seen[tok] {
				r.df[tok]++
				seen[tok] = true
			}
		}
		r.tf[i] = freq
	}

	if len(store.Chunks) > 0 {
		r.avgLen = totalLen / float64(len(store.Chunks))
	}

	return r
}

// Query retrieves the top-k most relevant chunks for a natural language query.
func (r *Retriever) Query(query string, topK int) []Result {
	queryTokens := tokenize(query)
	N := float64(len(r.store.Chunks))

	scores := make([]float64, len(r.store.Chunks))

	for _, qt := range queryTokens {
		df := float64(r.df[qt])
		if df == 0 {
			continue
		}
		// IDF with smoothing
		idf := math.Log((N-df+0.5)/(df+0.5) + 1)

		for i, freq := range r.tf {
			tf := freq[qt]
			if tf == 0 {
				continue
			}
			docLen := float64(len(tokenize(r.store.Chunks[i].Text)))
			norm := 1 - bm25B + bm25B*(docLen/r.avgLen)
			scores[i] += idf * ((tf * (bm25K1 + 1)) / (tf + bm25K1*norm))
		}
	}

	// Build and sort results
	results := make([]Result, len(r.store.Chunks))
	for i, chunk := range r.store.Chunks {
		results[i] = Result{Chunk: chunk, Score: scores[i]}
	}
	sort.Slice(results, func(i, j int) bool {
		return results[i].Score > results[j].Score
	})

	if topK > len(results) {
		topK = len(results)
	}
	// Filter zero-score results
	filtered := results[:0]
	for _, r := range results[:topK] {
		if r.Score > 0 {
			filtered = append(filtered, r)
		}
	}
	return filtered
}

// QueryByExpert retrieves the top-k chunks from a specific expert's documents.
func (r *Retriever) QueryByExpert(query, agentID string, topK int) []Result {
	all := r.Query(query, len(r.store.Chunks))
	var filtered []Result
	for _, res := range all {
		if res.AgentID == agentID {
			filtered = append(filtered, res)
			if len(filtered) >= topK {
				break
			}
		}
	}
	return filtered
}

// ListExperts returns a summary of all loaded expert documents.
func (r *Retriever) ListExperts() []ExpertDoc {
	return r.store.Docs
}

// ─── Helpers ──────────────────────────────────────────────────────────────────

// tokenize lowercases and splits text into terms, stripping markdown punctuation.
func tokenize(text string) []string {
	var tokens []string
	text = strings.ToLower(text)

	// Replace common markdown/punctuation with spaces
	replacer := strings.NewReplacer(
		"#", " ", "*", " ", "`", " ", "_", " ",
		".", " ", ",", " ", ":", " ", ";", " ",
		"(", " ", ")", " ", "[", " ", "]", " ",
		"\n", " ", "\t", " ", "\"", " ", "'", " ",
		"-", " ", "/", " ", "\\", " ", "|", " ",
	)
	text = replacer.Replace(text)

	for _, tok := range strings.Fields(text) {
		if len(tok) > 2 { // skip very short tokens
			tokens = append(tokens, tok)
		}
	}
	return tokens
}
