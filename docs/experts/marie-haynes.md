# Marie Haynes — Google Quality Updates, Penalties & Algorithm Analysis

## Background

Marie Haynes is a renowned SEO consultant specialising in **Google algorithm updates**, **manual penalties**, and **site quality recovery**. She runs Marie Haynes Consulting and publishes one of the most detailed algorithm update newsletters in the SEO industry. She has helped hundreds of sites recover from Google penalties and core update losses.

## Core Methodology

### 1. Algorithm Update Impact Analysis

- **Timeline mapping**: Correlate traffic drops precisely with known algorithm update dates using Google's public announcement history.
- **Segmentation**: Analyse traffic impact by page type, content category, and site section to isolate which content lost rankings.
- **Competitive comparison**: Identify which competitors *gained* during updates — their content characteristics reveal what Google is now rewarding.
- **Historical pattern recognition**: Each update type (core, helpful content, spam, product reviews) has distinct impact fingerprints.

### 2. Manual Penalty Diagnosis & Recovery

- Types: Unnatural inbound links, thin content, spammy structured data, cloaking, user-generated spam.
- **Link audit methodology**: Use multiple link data sources (GSC, Ahrefs, Majestic). Classify each link as natural/unnatural. Disavow last resort — attempt manual removal first.
- **Reconsideration requests**: Must be factual, show concrete remediation steps taken, and not make excuses.

### 3. Site Quality Signals

- **Page experience signals**: Beyond CWV — mobile friendliness, HTTPS, no intrusive interstitials.
- **Content policy compliance**: No deceptive redirects, no hidden text, no link schemes.
- **Brand signals**: Brand search volume, mention patterns in authoritative publications, entity prominence in Knowledge Graph.

## SAAI Agent Design — `GoogleUpdateAgent`

| Capability | Implementation |
|---|---|
| Update timeline correlator | Match traffic dips to known update dates |
| Winner/loser analyser | SERP position tracking around update windows |
| Link profile auditor | Classify backlink profiles by link quality |
| Penalty signal detector | Flag known spam patterns in structured data and content |
| Recovery playbook generator | Produce step-by-step recovery plan based on update type |

## Prompt Principles (for LLM Agents)

```
You are a Google algorithm update specialist following Marie Haynes's methodology.
When analysing a traffic drop:
1. Identify the closest Google algorithm update date.
2. Determine the update type and its known impact patterns.
3. Compare the site's content vs. SERP winners.
4. Check for manual action indicators.
5. Generate a prioritised recovery action plan.
```
