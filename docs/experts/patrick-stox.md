# Patrick Stox — On-Page SEO, Log File Analysis & Technical Auditing

## Background

Patrick Stox is a Product Advisor and Technical SEO expert at Ahrefs, and a frequent speaker at major SEO conferences. He is known for his rigorous, data-driven approach to **on-page optimisation**, **log file analysis**, and **technical SEO auditing**. He contributes extensively to Ahrefs' educational content.

## Core Methodology

### 1. On-Page Optimisation

- **Title Tags**: The single most impactful on-page signal. Should contain the primary keyword naturally, be unique across all pages, and fit within ~60 characters.
- **Meta Descriptions**: Not a ranking factor, but drives CTR. Should include a clear value proposition and call to action.
- **Header Hierarchy**: One `<h1>` per page; `<h2>` and below for supporting sections. Headers should use natural variations of target terms.
- **URL Structure**: Short, descriptive, keyword-relevant slugs. Use hyphens as word separators. Avoid dates in URLs unless freshness is a core differentiator.

### 2. Log File Analysis

- **What logs reveal**: The ground truth of what search engine bots are actually crawling — not what you *think* they're crawling.
- **Key metrics from logs**:
  - Crawl frequency per URL (high-value pages should be crawled most)
  - Bot segmentation (Googlebot Desktop vs. Mobile vs. Image bot)
  - Response code distribution (5xx errors reveal infrastructure problems)
  - Crawl budget waste (how much budget goes to low-value URLs)
- **Log → Indexing correlation**: Cross-reference crawled URLs with GSC indexing status to identify indexing bottlenecks.

### 3. Technical SEO Auditing

- **Redirect Audit**: Map all redirect chains; eliminate chains > 2 hops; fix redirect loops.
- **Pagination**: Implement `rel="next"` and `rel="prev"` where appropriate (though Google treats as hints). Ensure paginated pages aren't accidentally indexed without value.
- **Faceted Navigation**: The #1 source of crawl waste on e-commerce sites. Use `noindex`, `nofollow`, or `robots.txt` disallows for parameter-generated URLs.
- **Site Speed**: Audit TTFB (Time to First Byte) at the server level; CDN configuration; caching headers.

## SAAI Agent Design — `OnPageLogAnalysisAgent`

| Capability | Implementation |
|---|---|
| On-page element audit | Extract and score title, meta, headers, URL structure |
| Log file parser | Parse Apache/Nginx/CDN logs; segment by bot type |
| Crawl efficiency scorer | Pages crawled vs. pages indexed ratio analysis |
| Redirect chain mapper | Follow and visualise redirect chains |
| Faceted navigation detector | Identify parameter-URL crawl waste patterns |

## Prompt Principles (for LLM Agents)

```
You are a Technical SEO auditor following Patrick Stox's on-page and log analysis methodology.
When auditing a site:
1. Extract and evaluate all on-page elements (title, meta, headers, URL, content).
2. Parse bot access log data to identify crawl inefficiencies.
3. Map redirect chains and flag issues.
4. Identify faceted navigation or parameter-based crawl waste.
5. Produce a prioritised action list with specific fixes.
```
