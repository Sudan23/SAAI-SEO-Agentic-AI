# Lily Ray — E-E-A-T, Content Quality & Google Quality Updates

## Background

Lily Ray is the VP of SEO Strategy & Research at Amsive and one of the world's leading experts on **Google's Quality Rater Guidelines**, **E-E-A-T** (Experience, Expertise, Authoritativeness, Trustworthiness), and the impact of **core algorithm updates** on content-heavy sites. She tracks and documents the winners and losers of every major Google update.

## Core Methodology

### 1. E-E-A-T Framework

- **Experience**: Does the author have first-hand experience with the topic? (e.g., product reviews, travel guides, medical advice.)
- **Expertise**: Does the content demonstrate subject-matter expertise? Look for credentials, citations, depth of coverage.
- **Authoritativeness**: Is the site/author recognised by other authoritative sources through citations and links?
- **Trustworthiness**: Encompasses all of the above. The most critical dimension. Signals include: HTTPS, clear authorship, accurate contact info, transparent ownership, factual accuracy.

### 2. Content Quality Assessment

- **Helpful Content System**: Google's classifier penalises content created primarily for search engines, not people. Content must satisfy real user intent.
- **Thin Content**: Pages with little original value, excessive boilerplate, or insufficient coverage of the topic.
- **Duplicate/Near-Duplicate Content**: Dilutes authority and confuses crawlers about which page to rank.
- **Freshness**: Outdated statistics, stale advice, or inaccurate entity attributes erode trust signals.

### 3. Google Core Update Monitoring

- Track ranking position changes immediately after each core update.
- Segment analysis by site category: YMYL (Your Money Your Life) sites face heightened scrutiny.
- Identify patterns: which content types recover after updates, which do not.
- Cross-reference Wayback Machine snapshots with ranking changes to identify the exact content characteristics that caused movement.

### 4. YMYL Sites — Extra Diligence

- Health, finance, legal, and news content must meet the highest E-E-A-T bar.
- Medical content should be authored or reviewed by licensed professionals.
- Financial content must disclose conflicts of interest.

## SAAI Agent Design — `EEATContentAgent`

| Capability | Implementation |
|---|---|
| E-E-A-T scoring | NLP analysis of author signals, citations, credentials |
| Thin content detection | Word count + semantic depth scoring |
| Update impact tracker | Monitor rankings before/after algorithm update dates |
| YMYL classification | Classify pages by YMYL category for risk assessment |
| Authorship audit | Check author bio pages, bylines, LinkedIn verification |

## Prompt Principles (for LLM Agents)

```
You are an E-E-A-T and content quality analyst following Lily Ray's methodology.
When auditing a page:
1. Assess Experience, Expertise, Authoritativeness, and Trustworthiness signals.
2. Score content helpfulness: does it satisfy real user intent beyond ranking?
3. Flag thin, stale, or duplicate content issues.
4. Classify YMYL risk level.
5. Provide an actionable E-E-A-T improvement roadmap.
```
