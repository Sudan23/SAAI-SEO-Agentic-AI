// SAAI — SEO Agentic AI (Go Implementation)
// High-concurrency Reasoning Loop architecture
// Powered by langchaingo + colly

package main

import (
	"fmt"
	"os"
	"path/filepath"
	"runtime"
	"time"

	"github.com/saai/go-version/internal/retriever"
)

// ExpertModule represents a loaded SEO Expert Agent.
type ExpertModule struct {
	Name      string
	Specialty string
	AgentID   string
	IsActive  bool
}

// activeModules returns the full registry of Expert SEO Agents.
func activeModules() []ExpertModule {
	return []ExpertModule{
		{Name: "Koray Tuğberk GÜBÜR", Specialty: "Semantic SEO & Topical Authority", AgentID: "SemanticTopicalAuthorityAgent", IsActive: true},
		{Name: "Jamie Indigo", Specialty: "JavaScript Rendering & Crawl Budget", AgentID: "JSRenderingCrawlAgent", IsActive: true},
		{Name: "Jono Alderson", Specialty: "Technical SEO & Core Web Vitals", AgentID: "TechnicalSpeedAgent", IsActive: true},
		{Name: "Lily Ray", Specialty: "E-E-A-T & Content Quality", AgentID: "EEATContentAgent", IsActive: true},
		{Name: "Patrick Stox", Specialty: "On-Page SEO & Log File Analysis", AgentID: "OnPageLogAnalysisAgent", IsActive: true},
		{Name: "Marie Haynes", Specialty: "Google Quality Updates & Penalties", AgentID: "GoogleUpdateAgent", IsActive: true},
		{Name: "Aleyda Solis", Specialty: "International SEO & Hreflang", AgentID: "InternationalSEOAgent", IsActive: true},
		{Name: "Barry Adams", Specialty: "News SEO & Structured Data", AgentID: "NewsSEOAgent", IsActive: true},
	}
}

func printBanner() {
	fmt.Println(`
███████╗ █████╗  █████╗ ██╗
██╔════╝██╔══██╗██╔══██╗██║
███████╗███████║███████║██║
╚════██║██╔══██║██╔══██║██║
███████║██║  ██║██║  ██║██║
╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝╚═╝

SEO Agentic AI — Go Implementation
High-Concurrency Reasoning Loop Architecture`)
}

// docsDir resolves the /docs/experts path relative to the go-version directory.
func docsDir() string {
	// When run with `go run`, __file__ lives in go-version/.
	// Walk one level up to reach the repo root, then into docs/experts.
	_, filename, _, _ := runtime.Caller(0)
	root := filepath.Join(filepath.Dir(filename), "..")
	return filepath.Join(root, "docs", "experts")
}

func main() {
	printBanner()

	fmt.Printf("\n🕐  Timestamp  : %s\n", time.Now().Format(time.RFC1123))
	fmt.Printf("⚙️  Runtime     : Go (langchaingo + colly)\n")
	fmt.Printf("🏗️  Architecture: High-Concurrency Reasoning Loop\n\n")

	// ── Agent Module Registry ────────────────────────────────────────────────
	modules := activeModules()
	activeCount := 0
	for _, m := range modules {
		if m.IsActive {
			activeCount++
		}
	}

	fmt.Printf("✅  System Initialized — %d Expert SEO Modules Active\n", activeCount)
	fmt.Println("─────────────────────────────────────────────────────────────────────")
	fmt.Printf("  %-30s %-40s %s\n", "EXPERT", "SPECIALTY", "AGENT ID")
	fmt.Println("─────────────────────────────────────────────────────────────────────")
	for _, m := range modules {
		status := "🟢"
		if !m.IsActive {
			status = "🔴"
		}
		fmt.Printf("%s %-29s %-40s %s\n", status, m.Name, m.Specialty, m.AgentID)
	}
	fmt.Println("─────────────────────────────────────────────────────────────────────")

	// ── Expert Knowledge Retriever ───────────────────────────────────────────
	fmt.Println("\n📚  Loading Expert Knowledge Retriever...")

	store, err := retriever.LoadExpertStore(docsDir())
	if err != nil {
		fmt.Fprintf(os.Stderr, "❌  Retriever error: %v\n", err)
		os.Exit(1)
	}

	ret := retriever.New(store)
	fmt.Printf("    Loaded %d expert docs → %d knowledge chunks indexed.\n",
		len(store.Docs), len(store.Chunks))

	// Smoke-test: query the retriever with a representative SEO question.
	smokeQuery := "how to improve topical authority and semantic coverage"
	fmt.Printf("\n🔍  Smoke-test query: %q\n", smokeQuery)
	results := ret.Query(smokeQuery, 3)
	for i, r := range results {
		preview := r.Text
		if len(preview) > 120 {
			preview = preview[:120] + "…"
		}
		fmt.Printf("    [%d] (score=%.3f) [%s] %s\n", i+1, r.Score, r.AgentID, preview)
	}

	fmt.Println("\n🚀  SAAI Reasoning Loop ready. Awaiting task assignment...")

	// TODO Day 3: Start the ReAct reasoning loop
	// loop := reasoningloop.New(modules, ret)
	// loop.Run(context.Background())

	os.Exit(0)
}
