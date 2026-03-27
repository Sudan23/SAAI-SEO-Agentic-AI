# SAAI — SEO Agentic AI

> A multi-agent SEO intelligence platform inspired by the methodologies of the world's leading SEO experts.  
> Built with a **dual-architecture** approach: high-concurrency Go and type-safe Kotlin.

---

## 🏗️ Dual-Architecture Overview

| Dimension | Go Implementation | Kotlin Implementation |
|---|---|---|
| **Framework** | `langchaingo` + `colly` | `langchain4j` + `Ktor` |
| **Paradigm** | High-concurrency Reasoning Loop | Type-safe Expert Personas + Coroutines |
| **Runtime** | Native binary (Go 1.21+) | JVM 17+ / GraalVM |
| **Concurrency** | Goroutines + channels | Kotlin Coroutines (`async`/`await`) |
| **Crawling** | `colly` (concurrent HTML/JS scraper) | `Ktor` HTTP client + Ktor plugins |
| **Agent Arch** | ReAct loop with tool registry | Persona-based agents with sealed classes |
| **Strength** | Raw throughput, low latency crawling | Domain modelling, readability, DI |

### Why Dual-Architecture?

Large-scale SEO operations demand two very different computing profiles:

1. **Go's Reasoning Loop** — ideal for high-volume crawling, parallel link analysis, and real-time SERP monitoring where microsecond overhead matters.
2. **Kotlin's Expert Personas** — ideal for complex domain logic, structured agent personas with rich type systems, and LLM-orchestrated multi-step reasoning tasks.

Both implementations share the same **Expert Knowledge Base** (`/docs/experts`) and are designed to be orchestrated together behind a unified API gateway.

---

## 🧠 Expert SEO Modules

| Expert | Specialty | Module Name |
|---|---|---|
| **Koray Tuğberk GÜBÜR** | Semantic SEO & Topical Authority | `SemanticTopicalAuthorityAgent` |
| **Jamie Indigo** | JavaScript Rendering & Crawl Budget | `JSRenderingCrawlAgent` |
| **Jono Alderson** | Technical SEO & Core Web Vitals | `TechnicalSpeedAgent` |
| **Lily Ray** | E-E-A-T & Content Quality | `EEATContentAgent` |
| **Patrick Stox** | On-Page & Log File Analysis | `OnPageLogAnalysisAgent` |
| **Marie Haynes** | Google Quality Updates & Penalties | `GoogleUpdateAgent` |
| **Aleyda Solis** | International SEO & Hreflang | `InternationalSEOAgent` |
| **Barry Adams** | News SEO & Structured Data | `NewsSEOAgent` |

---

## 📁 Project Structure

```
SAAI/
├── README.md                  ← You are here
├── docs/
│   └── experts/               ← Expert methodology markdown files
│       ├── koray-tugberk-gubur.md
│       ├── jamie-indigo.md
│       ├── jono-alderson.md
│       ├── lily-ray.md
│       ├── patrick-stox.md
│       ├── marie-haynes.md
│       ├── aleyda-solis.md
│       └── barry-adams.md
├── go-version/
│   ├── go.mod
│   ├── main.go
│   ├── agents/                ← Agent implementations
│   ├── tools/                 ← LangChain tool wrappers
│   └── crawler/               ← colly-based crawl engine
└── kotlin-version/
    ├── build.gradle.kts
    ├── settings.gradle.kts
    ├── src/main/kotlin/
    │   └── ai/saai/
    │       ├── Main.kt
    │       ├── agents/        ← Expert persona agents
    │       └── tools/         ← LangChain4j tool definitions
    └── src/main/resources/
        └── application.conf
```

---

## 🗓️ 15-Day Implementation Milestones

| Day | Milestone | Deliverable |
|---|---|---|
| **1** | Project Scaffold | Repo structure, go.mod, build.gradle.kts, README |
| **2** | Expert Knowledge Base | All 8 expert markdown files complete |
| **3** | Go: Core Agent Loop | ReAct reasoning loop skeleton with tool registry |
| **4** | Go: Colly Crawler | Concurrent crawler respecting robots.txt + politeness |
| **5** | Go: Semantic SEO Agent | Koray-inspired topical map builder |
| **6** | Go: JS Rendering Agent | Jamie Indigo–style render vs. raw diff analysis |
| **7** | Go: Technical Speed Agent | CWV auditor using Lighthouse JSON output |
| **8** | Kotlin: Project Setup | Ktor server, DI with Koin, agent trait hierarchy |
| **9** | Kotlin: Expert Personas | Sealed class persona system, LangChain4j integration |
| **10** | Kotlin: E-E-A-T Agent | Lily Ray–inspired content quality scorer |
| **11** | Kotlin: International SEO | Aleyda-inspired hreflang validator |
| **12** | Shared: API Contract | OpenAPI spec bridging Go ↔ Kotlin services |
| **13** | Shared: Evaluation Suite | Prompt evaluation harness for each agent |
| **14** | Integration | End-to-end pipeline: crawl → analyse → report |
| **15** | Docs & Release | Full README, architecture diagram, v0.1.0 tag |

---

## 🚀 Quick Start

### Go

```bash
cd go-version
go run main.go
```

### Kotlin

```bash
cd kotlin-version
./gradlew run
```

---

## 📄 License

MIT — see [LICENSE](LICENSE).
