# Player Rating Formula v1 — Purdue 32-0

## Scope
48 player-season entries across 2019-20, 2020-21, 2021-22, 2022-23 and 2023-24. This is a curated rotation-player pool, not every roster member. Include players with at least 20 appearances and at least 8.0 minutes per game in Purdue's final all-games table. Use each player's own games played, not the team's games played. Postseason games are included where played. The 2019-20 season ended with the postseason canceled.

## Files
- data/players.csv: game input, retaining the existing four-column format.
- data/player_stats_audit.csv: original totals, games, displayed per-game averages, raw rating, final rating and source for every entry.
- docs/PLAYER_RATING_FORMULA.md: this documentation.

## Formula
raw = 40 + 2*PPG + RPG + 2*APG + 3*SPG + 3*BPG - 2*TOPG
rating = max(40, min(99, floor(raw + 0.5)))

Calculate averages directly from season totals divided by that player's games played. Do not round averages before calculating. Audit averages and raw scores are displayed to six decimal places only; the calculation uses unrounded ratios. Round the final score to the nearest integer, with halves rounding up (Java Math.round for these positive scores), then clamp to 40–99.

Equivalent calculation:
raw = 40 + (2*points_total + rebounds_total + 2*assists_total + 3*steals_total + 3*blocks_total - 2*turnovers_total) / games
Use floating-point division in Java.

## Why these weights?
40 is the baseline. Points get weight 2; assists get weight 2; rebounds get weight 1; steals and blocks earn 3 'menace points'; turnovers lose 2. These weights are custom game design choices, not Purdue's ratings, a validated basketball model, or a formula derived through statistical fitting. The underlying counts are real; the rating is calculated by our custom formula. No subjective bonuses were added.

## Worked example: 2023-24 Braden Smith
39 games; 468 points; 226 rebounds; 292 assists; 62 steals; 5 blocks; 104 turnovers.
raw = 40 + (936 + 226 + 584 + 186 + 15 - 208)/39
raw = 84.5897435897; rounded rating = 85.

## Positions and identity
PG, SG, SF, PF and C are simplified game slot assignments, not official five-position designations. In particular, wing guards may be assigned SF for draft coverage. Each season has at least one option in every slot. Same names across seasons are intentional: identify a player-season by season plus name. Decide separately whether the game allows two seasons of the same real player on a team; this dataset does not impose that rule.

## Draft rule needed to prevent dead ends
If seasons cannot repeat within a draft, only show players eligible for an open position and not already taken. Only spin among unused seasons containing an eligible player. If banning repeated real players, apply that filter too. Reset used seasons and lineup when starting a new draft. Five seasons support five unique season spins for ONE five-player team. For two teams sharing a ten-pick draft, reset season availability per team or allow season reuse across teams; five globally unique seasons cannot support ten picks.

## Limitations
The score measures box-score production per game, not complete ability. Minutes, pace, role and opponent strength affect it. It omits shooting efficiency, spacing and defense not captured by steals/blocks. Centers can still score highly; the earlier claim that this ensures positional balance was too strong. The 99 ceiling compresses star ratings: both 2022-23 and 2023-24 Edey reach the cap. No balance testing or game simulation calibration has been performed. Keep this as v1 and document future changes rather than quietly changing weights.

## Sources and extraction
Official Purdue Athletics final all-games statistics, reached through https://purduesports.com/mbb-archives. Counts were transcribed from the Team Box Score tables. Position assignments are the game's own labels. Retrieved November 24, 2026.

- 2019-20: PDF page 9 (1-based): https://storage.googleapis.com/purduesports-com-prod/2025/05/16/eGDgmy8MCDAI3gGru9RGtAjpmSUKjN71qnQz0EJk.pdf
- 2020-21: PDF page 2 (1-based): https://storage.googleapis.com/purduesports-com-prod/2025/05/16/Ww4JF6frYDVScZR2tWYNRXWmtcLtMWNbFkiVzb08.pdf
- 2021-22: PDF page 6 (1-based): https://storage.googleapis.com/purduesports-com-prod/2025/05/16/t0Da03iWBpXWdDkJRd8DJ4M6i2Ltr8wSYzNk9vMk.pdf
- 2022-23: PDF page 1 (1-based): https://storage.googleapis.com/purduesports-com-prod/2025/05/16/MOliT6BRmimoyeYR7vY9EyQ1cWeuwBMQ5WKgwmRQ.pdf
- 2023-24: PDF page 3 (1-based): https://storage.googleapis.com/purduesports-com-prod/2025/05/16/L6rE8G0Zhs8eWIxXupib7mmHA8EGq6MVOdUJvC8f.pdf

## Validation performed
48 unique season/name pairs; five seasons; valid position and 40–99 rating range; all five positions represented each season; ratings generated from integer totals with the stated formula. This checks data structure and arithmetic, not predictive accuracy.
