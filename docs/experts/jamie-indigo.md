# Jamie Indigo — JavaScript Rendering & Crawl Budget Optimisation

## Background

Jamie Indigo is a Technical SEO specialist who focuses on the intersection of **JavaScript rendering**, **crawl efficiency**, and **Googlebot behaviour**. She has worked at DeepCrawl (now Lumar) and is known for demystifying how modern JavaScript frameworks interact with search engine crawlers. Her work helps developers understand what Googlebot actually sees vs. what users see.

## Core Methodology

### 1. Render vs. Raw HTML Analysis

- **The Two-Wave Problem**: Googlebot fetches pages in two waves — the initial HTML download, and the fully-rendered DOM after JavaScript execution. Critical content visible only after JS execution may be indexed with delay or missed entirely.
- **Raw HTML Audit**: Always compare the raw HTTP response body against the fully-rendered DOM to identify content that relies on client-side rendering.
- **Rendering Gap Detection**: Any content, links, or structured data present only in the rendered DOM but absent from raw HTML is at risk.

### 2. JavaScript SEO Best Practices

- **Server-Side Rendering (SSR)** or **Static Site Generation (SSG)** for critical content and links.
- **Progressive Enhancement**: Build pages so core content is accessible in raw HTML; enhance with JS for UX.
- **Dynamic Rendering**: Use as a temporary solution for heavy JS frameworks — serve pre-rendered pages to crawlers.
- Avoid JavaScript-only internal links (rendered via `<a>` tags injected by JS after load) as they may not be crawled.

### 3. Crawl Budget Management

- **Crawl Budget = Crawl Rate Limit × Crawl Demand**: Understand the two components and optimise both.
- Eliminate crawl waste: duplicate URLs, infinite scroll parameters, session IDs, tracking parameters.
- Use `robots.txt` and `noindex` strategically to protect crawl budget for high-value pages.
- Monitor **crawl anomalies** in Google Search Console: 404s, redirect chains, server errors consume budget without benefit.

### 4. Log File Analysis

- Parse server access logs to see exactly which URLs Googlebot crawls, how frequently, and with what response codes.
- Correlate crawl frequency with indexing status and ranking changes.
- Identify crawl traps: pagination loops, faceted navigation, auto-generated parameter URLs.

## SAAI Agent Design — `JSRenderingCrawlAgent`

| Capability | Implementation |
|---|---|
| Raw vs. rendered diff | Headless browser (Chromium) render → diff against raw HTTP response |
| JS dependency mapping | Identify which content blocks depend on JS execution |
| Crawl budget analysis | Parse log files; compute crawl efficiency score |
| Redirect chain detection | Follow redirect chains; flag chains > 2 hops |
| Crawl trap identification | Detect infinite loops in URL parameter patterns |
| Resource hint audit | Check for `<link rel="preload">`, `<script defer>` optimisations |

## Key References

- Jamie Indigo's content at [DeepCrawl/Lumar Blog](https://www.lumar.io/blog/)
- [Google's JavaScript SEO Basics](https://developers.google.com/search/docs/crawling-indexing/javascript/javascript-seo-basics)
- "How Googlebot Works" documentation series

## Prompt Principles (for LLM Agents)

```
You are a JavaScript SEO and crawl budget specialist following Jamie Indigo's methodology.
When auditing a URL:
1. Fetch raw HTML and note all content, links, and structured data.
2. Render with a headless browser and capture the full DOM.
3. Diff the two states and flag content/links only present post-render.
4. Assess crawl budget risk based on URL parameter patterns and redirect chains.
5. Produce a prioritised remediation list.
```
