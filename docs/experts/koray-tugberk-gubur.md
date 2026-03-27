# Koray Tuğberk GÜBÜR — Semantic SEO & Topical Authority

## Background

Koray Tuğberk GÜBÜR is widely regarded as the leading practitioner of **Semantic SEO** — an approach that moves beyond keyword targeting to model entire knowledge domains the way search engines understand them. He publishes extensively at [Holistic SEO & Digital](https://www.holisticseo.digital/) and has demonstrated dramatic ranking improvements through topical authority construction.

## Core Methodology

### 1. Topical Authority Framework

- **Topical Map Construction**: Model an entire subject domain as a graph of interconnected concepts. Every entity, attribute, and relationship relevant to a topic should be documented before a single URL is written.
- **Semantic Distance Minimisation**: Choose content titles and headings that minimise the semantic distance between your document and the canonical representation of a concept in Google's Knowledge Graph.
- **Context Vectors**: Each page should signal its context through co-occurrence patterns of semantically related terms — not keyword stuffing, but natural concept coverage.

### 2. Entity-First Writing

- Identify the **head entity** of each page (what the page *is about* at its core).
- Declare entity attributes explicitly and consistently across all content.
- Use structured data (Schema.org) to bridge human language and machine-readable entity definitions.

### 3. Content Network Architecture

- Build **hub pages** (pillar content) that connect to **spoke pages** (supporting content).
- Internal links should follow the topical map — linking concepts to their related attributes and sub-topics.
- Avoid orphan pages; every document must participate in the knowledge network.

### 4. Quality Signals

- **Completeness**: Cover all sub-questions and facets of a topic within a content series.
- **Uniqueness**: Add proprietary data, original research, or expert commentary not found elsewhere.
- **Freshness**: Maintain entity attributes as facts change; stale entity data erodes topical authority.

## SAAI Agent Design — `SemanticTopicalAuthorityAgent`

| Capability | Implementation |
|---|---|
| Topic graph generation | LLM-driven entity extraction → graph database storage |
| Semantic gap detection | Compare domain entity coverage vs. competitor URLs |
| Content brief generation | Output structured briefs aligned with topical map nodes |
| Schema.org recommendation | Suggest appropriate schema types per entity |
| Internal link audit | Score existing internal links against topical map edges |

## Key References

- [Topical Authority & Semantic SEO](https://www.holisticseo.digital/theoretical-seo/topical-authority/) — Holistic SEO & Digital
- Koray's work on Google's Knowledge Graph and entity disambiguation
- The Semantic SEO methodology thread series on Twitter/X

## Prompt Principles (for LLM Agents)

```
You are a Semantic SEO specialist following Koray Tuğberk GÜBÜR's topical authority framework.
When analysing a domain:
1. First identify all head entities and their attributes.
2. Map relationships between entities.
3. Identify coverage gaps relative to the topic graph.
4. Suggest content nodes that close those gaps.
5. Output a structured topical map in JSON-LD compatible format.
```
