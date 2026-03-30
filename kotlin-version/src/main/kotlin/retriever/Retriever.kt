package retriever

import java.io.File
import kotlin.math.ln
import kotlin.math.sqrt

// ─── Data Models ─────────────────────────────────────────────────────────────

/** A fully loaded expert methodology document. */
data class ExpertDoc(
    val expertName: String,
    val agentId: String,
    val filePath: String,
    val content: String
)

/** A paragraph-level passage derived from an [ExpertDoc]. */
data class Chunk(
    val expertName: String,
    val agentId: String,
    val text: String,
    val source: String       // filePath → heading context
)

/** A retrieved [Chunk] alongside its BM25 relevance score. */
data class Result(
    val chunk: Chunk,
    val score: Double
)

// ─── Expert → Agent Mapping ───────────────────────────────────────────────────

private val EXPERT_AGENT_MAP = mapOf(
    "koray-tugberk-gubur" to "SemanticTopicalAuthorityAgent",
    "jamie-indigo"        to "JSRenderingCrawlAgent",
    "jono-alderson"       to "TechnicalSpeedAgent",
    "lily-ray"            to "EEATContentAgent",
    "patrick-stox"        to "OnPageLogAnalysisAgent",
    "marie-haynes"        to "GoogleUpdateAgent",
    "aleyda-solis"        to "InternationalSEOAgent",
    "barry-adams"         to "NewsSEOAgent",
)

// ─── ExpertStore ─────────────────────────────────────────────────────────────

/**
 * Loads all `*.md` files from [docsDir] and chunks them into paragraphs,
 * building the in-memory knowledge base used by the BM25 [Retriever].
 */
class ExpertStore(private val docsDir: File) {

    val docs: List<ExpertDoc>
    val chunks: List<Chunk>

    init {
        require(docsDir.isDirectory) {
            "ExpertStore: '$docsDir' is not a directory"
        }

        val loadedDocs = docsDir
            .listFiles { f -> f.extension == "md" }
            ?.sortedBy { it.name }
            ?.map { file ->
                val stem    = file.nameWithoutExtension
                val content = file.readText()
                ExpertDoc(
                    expertName = extractH1(content) ?: stem,
                    agentId    = EXPERT_AGENT_MAP[stem] ?: "UnknownAgent",
                    filePath   = file.absolutePath,
                    content    = content
                )
            }
            ?: emptyList()

        docs   = loadedDocs
        chunks = loadedDocs.flatMap { chunkDocument(it) }
    }

    // ── Chunking ──────────────────────────────────────────────────────────────

    private fun chunkDocument(doc: ExpertDoc): List<Chunk> {
        val result      = mutableListOf<Chunk>()
        var heading     = ""
        val lines       = mutableListOf<String>()

        fun flush() {
            val text = lines.joinToString("\n").trim()
            if (text.length > 30) {
                result += Chunk(
                    expertName = doc.expertName,
                    agentId    = doc.agentId,
                    text       = text,
                    source     = "${doc.filePath} → $heading"
                )
            }
            lines.clear()
        }

        for (line in doc.content.lines()) {
            when {
                line.startsWith("#") -> {
                    flush()
                    heading = line.trimStart('#', ' ')
                }
                line.isBlank() -> flush()
                else           -> lines += line
            }
        }
        flush()

        return result
    }

    private fun extractH1(content: String): String? =
        content.lines()
            .firstOrNull { it.startsWith("# ") }
            ?.removePrefix("# ")
            ?.trim()
}

// ─── BM25 Retriever ───────────────────────────────────────────────────────────

private const val BM25_K1 = 1.5
private const val BM25_B  = 0.75

/**
 * Okapi BM25 retriever over an [ExpertStore].
 *
 * On construction it pre-computes term frequencies (TF),
 * document frequencies (DF), and average document length
 * so that [query] runs in O(|queryTerms| × |chunks|) time.
 */
class Retriever(private val store: ExpertStore) {

    private val tf:     List<Map<String, Double>>   // TF per chunk
    private val df:     Map<String, Int>            // DF per term
    private val avgLen: Double                      // avg chunk length in tokens

    init {
        val tfMutable = mutableListOf<Map<String, Double>>()
        val dfMutable = mutableMapOf<String, Int>()
        var totalLen  = 0.0

        for (chunk in store.chunks) {
            val tokens = tokenize(chunk.text)
            totalLen  += tokens.size
            val freq   = mutableMapOf<String, Double>()
            val seen   = mutableSetOf<String>()

            for (tok in tokens) {
                freq[tok] = (freq[tok] ?: 0.0) + 1.0
                if (seen.add(tok)) dfMutable[tok] = (dfMutable[tok] ?: 0) + 1
            }
            tfMutable += freq
        }

        tf     = tfMutable
        df     = dfMutable
        avgLen = if (store.chunks.isNotEmpty()) totalLen / store.chunks.size else 1.0
    }

    /**
     * Returns the top-[k] most relevant [Result]s for a natural-language [query].
     */
    fun query(query: String, k: Int = 5): List<Result> {
        val queryTokens = tokenize(query)
        val n           = store.chunks.size.toDouble()
        val scores      = DoubleArray(store.chunks.size)

        for (qt in queryTokens) {
            val docFreq = df[qt]?.toDouble() ?: continue
            val idf     = ln((n - docFreq + 0.5) / (docFreq + 0.5) + 1.0)

            for ((i, chunkTf) in tf.withIndex()) {
                val termFreq = chunkTf[qt] ?: continue
                val docLen   = tokenize(store.chunks[i].text).size.toDouble()
                val norm     = 1 - BM25_B + BM25_B * (docLen / avgLen)
                scores[i]   += idf * ((termFreq * (BM25_K1 + 1)) / (termFreq + BM25_K1 * norm))
            }
        }

        return store.chunks.indices
            .map { Result(store.chunks[it], scores[it]) }
            .filter { it.score > 0 }
            .sortedByDescending { it.score }
            .take(k)
    }

    /**
     * Retrieves top-[k] results restricted to a single expert's documents.
     */
    fun queryByExpert(query: String, agentId: String, k: Int = 3): List<Result> =
        query(query, store.chunks.size)
            .filter { it.chunk.agentId == agentId }
            .take(k)

    /** Returns all loaded [ExpertDoc]s for display / debug. */
    fun listExperts(): List<ExpertDoc> = store.docs
}

// ─── Tokeniser ───────────────────────────────────────────────────────────────

private val STRIP_REGEX = Regex("""[#*`_.,;:()\[\]"\n\t'\\|/\-]+""")

private fun tokenize(text: String): List<String> =
    STRIP_REGEX.replace(text.lowercase(), " ")
        .split(" ")
        .filter { it.length > 2 }
