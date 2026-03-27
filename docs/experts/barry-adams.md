# Barry Adams — News SEO, Structured Data & Publisher Strategy

## Background

Barry Adams is a specialist in **News SEO** and publisher/media site optimisation. He runs Polemic Digital and is one of the foremost experts on Google News, Google Discover, and structured data for media organisations. He has worked with major publishers across Europe and North America.

## Core Methodology

### 1. Google News Optimisation

- **Inclusion requirements**: Unique, original reporting; transparent authorship; no paywalls blocking crawlers; consistent publication cadence.
- **Publishing velocity**: News sites should publish on a consistent schedule. Gaps in publication can reduce Googlebot crawl frequency.
- **News-specific sitemaps**: `<news:news>` sitemap extension is critical for timely indexing of news articles. Update within minutes of publication.
- **Article freshness**: Update titles and content for developing stories; ensure the canonical reflects the most current version.

### 2. Google Discover Strategy

- Discover surfaces content based on interest graphs, not search queries.
- **Triggers for Discover eligibility**: High-quality images (≥1200px wide, `max-image-preview:large` robots meta), strong E-E-A-T signals, compelling headlines that don't resort to clickbait.
- **Evergreen vs. trending**: Discover rewards both breaking news and deeply informative evergreen content.

### 3. Structured Data for Publishers

- `NewsArticle` schema: Required for Google News rich results. Include `datePublished`, `dateModified`, `author`, `publisher`, `image`.
- `Article` and `BlogPosting` schemas for non-news editorial content.
- `Speakable` schema: Marks sections suitable for text-to-speech (Google Assistant integration).
- Paywalled content: Use `isAccessibleForFree` and `hasPart` with `isAccessibleForFree: false` to declare metered content correctly.

### 4. Crawl & Indexing Speed for News

- Serve clean, fast HTML for news articles — JS-rendered news content is indexed with delay, unacceptable for breaking news.
- Use **Pub/Sub** (Google's Indexing API) for real-time indexing notifications for news and job posting content.
- Monitor Crawl Stats in GSC for news-specific crawl health.

## SAAI Agent Design — `NewsSEOAgent`

| Capability | Implementation |
|---|---|
| News sitemap validator | Parse and validate `<news:news>` sitemap extension |
| NewsArticle schema auditor | Extract + validate NewsArticle JSON-LD completeness |
| Discover eligibility checker | Assess image size, `max-image-preview`, E-E-A-T signals |
| Indexing API trigger | Auto-submit new article URLs via Google Indexing API |
| Publication cadence monitor | Track publish frequency; flag gaps that risk crawl reduction |

## Prompt Principles (for LLM Agents)

```
You are a News SEO specialist following Barry Adams's publisher optimisation methodology.
When auditing a news/media site:
1. Validate news sitemap format and freshness.
2. Audit NewsArticle schema completeness.
3. Check Discover eligibility signals (image, E-E-A-T, headline quality).
4. Assess crawl speed and indexing latency for new articles.
5. Recommend a publication cadence and structured data improvement plan.
```
