package ai.saai

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import retriever.ExpertStore
import retriever.Retriever
import java.io.File
import java.nio.file.Paths

// ─── Expert Persona Model ────────────────────────────────────────────────────

/**
 * Sealed hierarchy representing an SEO Expert Persona.
 * Each subclass encapsulates the identity and speciality of a real-world expert.
 */
sealed class ExpertPersona(
    val name: String,
    val specialty: String,
    val agentId: String,
    val isActive: Boolean = true
) {
    object SemanticTopicalAuthority : ExpertPersona(
        name = "Koray Tuğberk GÜBÜR",
        specialty = "Semantic SEO & Topical Authority",
        agentId = "SemanticTopicalAuthorityAgent"
    )

    object JSRenderingCrawl : ExpertPersona(
        name = "Jamie Indigo",
        specialty = "JavaScript Rendering & Crawl Budget",
        agentId = "JSRenderingCrawlAgent"
    )

    object TechnicalSpeed : ExpertPersona(
        name = "Jono Alderson",
        specialty = "Technical SEO & Core Web Vitals",
        agentId = "TechnicalSpeedAgent"
    )

    object EEATContent : ExpertPersona(
        name = "Lily Ray",
        specialty = "E-E-A-T & Content Quality",
        agentId = "EEATContentAgent"
    )

    object OnPageLogAnalysis : ExpertPersona(
        name = "Patrick Stox",
        specialty = "On-Page SEO & Log File Analysis",
        agentId = "OnPageLogAnalysisAgent"
    )

    object GoogleUpdate : ExpertPersona(
        name = "Marie Haynes",
        specialty = "Google Quality Updates & Penalties",
        agentId = "GoogleUpdateAgent"
    )

    object InternationalSEO : ExpertPersona(
        name = "Aleyda Solis",
        specialty = "International SEO & Hreflang",
        agentId = "InternationalSEOAgent"
    )

    object NewsSEO : ExpertPersona(
        name = "Barry Adams",
        specialty = "News SEO & Structured Data",
        agentId = "NewsSEOAgent"
    )
}

// ─── Active Module Registry ──────────────────────────────────────────────────

val ACTIVE_MODULES: List<ExpertPersona> = listOf(
    ExpertPersona.SemanticTopicalAuthority,
    ExpertPersona.JSRenderingCrawl,
    ExpertPersona.TechnicalSpeed,
    ExpertPersona.EEATContent,
    ExpertPersona.OnPageLogAnalysis,
    ExpertPersona.GoogleUpdate,
    ExpertPersona.InternationalSEO,
    ExpertPersona.NewsSEO
)

// ─── Banner ───────────────────────────────────────────────────────────────────

fun printBanner() {
    println("""
███████╗ █████╗  █████╗ ██╗
██╔════╝██╔══██╗██╔══██╗██║
███████╗███████║███████║██║
╚════██║██╔══██║██╔══██║██║
███████║██║  ██║██║  ██║██║
╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝╚═╝

SEO Agentic AI — Kotlin Implementation
Type-Safe Expert Personas + Kotlin Coroutines
""")
}

// ─── System Initialisation ────────────────────────────────────────────────────

fun initSystem(modules: List<ExpertPersona>) = runBlocking {
    println("⚙️  Runtime     : Kotlin / JVM (langchain4j + Ktor)")
    println("🏗️  Architecture: Type-Safe Expert Personas + Coroutines")
    println()

    // Simulate async persona initialisation using coroutines
    val initialisations = modules.map { persona ->
        async {
            // TODO Day 9: Replace with real LangChain4j agent init
            Thread.sleep(10) // Simulated init latency
            persona
        }
    }

    val loaded = initialisations.awaitAll()
    val activeCount = loaded.count { it.isActive }

    println("✅  System Initialized — $activeCount Expert SEO Modules Active")
    println("─".repeat(80))
    println("  ${"EXPERT".padEnd(30)} ${"SPECIALTY".padEnd(40)} AGENT ID")
    println("─".repeat(80))

    loaded.forEach { persona ->
        val status = if (persona.isActive) "🟢" else "🔴"
        println(
            "$status ${persona.name.padEnd(29)} " +
            "${persona.specialty.padEnd(40)} " +
            persona.agentId
        )
    }

    println("─".repeat(80))
    println()
    println("🚀  SAAI Persona Engine ready. Awaiting task assignment...")
}

// ─── Entry Point ─────────────────────────────────────────────────────────────

/**
 * Resolves the /docs/experts directory relative to the project root.
 * Works whether run via `./gradlew run` or an IDE.
 */
private fun docsDir(): File {
    // Walk up from kotlin-version/ to the repo root
    val base = File(System.getProperty("user.dir"))
    val candidate = File(base, "../docs/experts")
    return candidate.canonicalFile
}

fun main() {
    printBanner()
    initSystem(ACTIVE_MODULES)

    // ── Expert Knowledge Retriever ────────────────────────────────────────────
    println("\n📚  Loading Expert Knowledge Retriever...")
    val store     = ExpertStore(docsDir())
    val retriever = Retriever(store)
    println("    Loaded ${store.docs.size} expert docs → ${store.chunks.size} knowledge chunks indexed.")

    // Smoke-test query
    val smokeQuery = "how to improve topical authority and semantic coverage"
    println("\n🔍  Smoke-test query: \"$smokeQuery\"")
    retriever.query(smokeQuery, k = 3).forEachIndexed { i, result ->
        val preview = result.chunk.text.take(120).let { if (result.chunk.text.length > 120) "$it…" else it }
        println("    [${i + 1}] (score=%.3f) [${result.chunk.agentId}] $preview".format(result.score))
    }

    println("\n🚀  SAAI Persona Engine ready. Awaiting task assignment...")

    // TODO Day 8: Start Ktor server for API gateway
    // embeddedServer(Netty, port = 8080, module = Application::module).start(wait = true)
}
