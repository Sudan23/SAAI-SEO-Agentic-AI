# Jono Alderson — Technical SEO, Core Web Vitals & Performance

## Background

Jono Alderson is a full-stack digital strategist and Technical SEO expert, formerly Head of SEO at Yoast. He specialises in the intersection of **web performance**, **Core Web Vitals**, **structured data**, and **technical infrastructure**. His philosophy treats SEO as an engineering discipline, not a marketing afterthought.

## Core Methodology

### 1. Core Web Vitals as a Ranking Signal

- **LCP (Largest Contentful Paint)**: The render time of the largest image or text block in the viewport. Target < 2.5s. Common causes of poor LCP: render-blocking resources, slow server response, unoptimised images.
- **INP (Interaction to Next Paint)**: Replaced FID in 2024. Measures responsiveness to user interactions across the page lifecycle. Target < 200ms.
- **CLS (Cumulative Layout Shift)**: Visual stability metric. Target < 0.1. Common causes: images without dimensions, dynamically injected content, web fonts causing FOUT.

### 2. Performance-First Architecture

- **Critical Rendering Path Optimisation**: Inline critical CSS; defer non-critical CSS; eliminate render-blocking scripts.
- **Resource Hints**: `<link rel="preload">` for critical assets; `<link rel="prefetch">` for next-page navigation; `dns-prefetch` for third-party origins.
- **Image Optimisation**: Use modern formats (WebP, AVIF); implement responsive images with `srcset`; lazy-load below-the-fold images.
- **Third-Party Script Audit**: Third-party scripts are often the #1 cause of INP and LCP degradation. Audit, defer, or remove them.

### 3. Structured Data Strategy

- Implement Schema.org markup to enable rich results and improve entity understanding.
- Use **JSON-LD** (Google's preferred format) injected in `<head>`.
- Test with Google's Rich Results Test and Schema Markup Validator.
- Prioritise: `Organization`, `WebSite`, `BreadcrumbList`, `Article`, `Product`, `FAQPage`.

### 4. Technical Infrastructure

- **HTTPS everywhere**: Mixed content kills trust and rankings.
- **Canonical management**: Every indexable URL must have a self-referencing canonical. Handle cross-domain canonicals carefully.
- **XML Sitemap hygiene**: Only include indexable, canonical, 200-status URLs. Exclude paginated pages unless they have standalone value.
- **Hreflang implementation**: For international sites, validate hreflang tags with reciprocal confirmation.

### 5. Crawl Efficiency at Scale

- Implement `robots.txt` to block low-value URL patterns.
- Use `Cache-Control` headers to reduce server load from re-crawls.
- Monitor GSC Coverage report for indexing anomalies weekly.

## SAAI Agent Design — `TechnicalSpeedAgent`

| Capability | Implementation |
|---|---|
| CWV audit | Lighthouse JSON API → parse LCP, INP, CLS scores |
| Render-blocking detection | Parse resource waterfall; flag parser-blocking scripts/styles |
| Structured data audit | Extract + validate JSON-LD against Schema.org spec |
| Image optimisation check | Detect unoptimised formats, missing `width`/`height`, no `lazy` |
| Third-party script impact | Attribute CWV degradation to specific third-party origins |
| Canonical audit | Validate canonical tags across crawled URL set |

## Key References

- [Jono Alderson's blog](https://www.jonoalderson.com/)
- [Google Search Central — Core Web Vitals](https://developers.google.com/search/docs/appearance/core-web-vitals)
- [web.dev Performance guides](https://web.dev/performance/)

## Prompt Principles (for LLM Agents)

```
You are a Technical SEO and web performance engineer following Jono Alderson's methodology.
When auditing a page:
1. Retrieve Lighthouse scores for LCP, INP, and CLS.
2. Identify the top 3 bottlenecks per metric.
3. Audit structured data completeness and validity.
4. Flag render-blocking resources and third-party script impact.
5. Output a prioritised technical remediation plan with effort/impact scores.
```
