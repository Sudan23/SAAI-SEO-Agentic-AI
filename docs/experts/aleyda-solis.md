# Aleyda Solis — International SEO, Hreflang & Global Strategy

## Background

Aleyda Solis is an internationally recognised SEO consultant, speaker, and author. She is the founder of Orainti, a consultancy specialising in **international SEO**, and the creator of the [SEOFOMO newsletter](https://www.seofomo.co/) and [hreflang tool](https://www.aleydasolis.com/en/seo-tools/hreflang-tags-generator/). She is one of the most authoritative voices on global SEO strategy.

## Core Methodology

### 1. International SEO Architecture

Three URL structure options for international sites:
- **ccTLD** (e.g., `example.de`, `example.fr`): Strongest geographic signal; most expensive to maintain.
- **Subdomain** (e.g., `de.example.com`): Easier to implement; moderate geo-signal.
- **Subdirectory** (e.g., `example.com/de/`): Consolidates domain authority; Google's recommended default for most sites.

### 2. Hreflang Implementation

- `hreflang` tells Google which language/country variant to serve to which user.
- **Reciprocal requirement**: Every hreflang tag must be confirmed by the target page's own hreflang set.
- **x-default**: Always include a fallback `x-default` pointing to the most generic language version.
- Common errors: missing reciprocal tags, wrong ISO language/country codes, pointing to non-canonical URLs.

```html
<link rel="alternate" hreflang="en-us" href="https://example.com/en-us/page/" />
<link rel="alternate" hreflang="de" href="https://example.com/de/page/" />
<link rel="alternate" hreflang="x-default" href="https://example.com/page/" />
```

### 3. Localisation vs. Translation

- **Localisation** goes beyond word-for-word translation: adapting cultural references, currencies, date formats, measurement units, local terminology.
- Thin machine-translated content is a quality signal red flag. Each locale should have genuinely localised content.
- **Local keyword research**: Never assume the same keywords perform across locales. Research in the target language, using local search volumes.

### 4. International Crawl Efficiency

- Ensure Googlebot can crawl all locale variants without geo-blocking.
- Use the International Targeting report in Google Search Console to verify geo-targeting.
- Implement locale-specific XML sitemaps for large international sites.

## SAAI Agent Design — `InternationalSEOAgent`

| Capability | Implementation |
|---|---|
| Hreflang validator | Parse hreflang tags; verify reciprocal links; flag errors |
| URL structure analyser | Evaluate ccTLD/subdomain/subdirectory setup |
| Locale keyword research | LLM-powered keyword localisation per target market |
| Translation quality audit | Detect thin or machine-only translated content |
| GSC geo-targeting checker | Validate International Targeting report signals |

## Prompt Principles (for LLM Agents)

```
You are an International SEO specialist following Aleyda Solis's methodology.
When auditing an international site:
1. Identify the URL structure (ccTLD/subdomain/subdirectory).
2. Validate all hreflang tags for completeness and reciprocity.
3. Check for geo-blocking of crawler access.
4. Assess localisation quality vs. raw translation.
5. Output a market-by-market international SEO health report.
```
