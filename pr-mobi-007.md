# MOBI-007 — PR Artifacts

## Branch name

```
MOBI-007-ui-polish-tests-and-accessibility
```

---

## Commit message

```
MOBI-007: Polish UI components, complete test coverage, and fix accessibility gaps

- Rewrote CurrencySelector as a custom animated segmented control (spring indicator, surfaceVariant container, no ripple)
- Redesigned PropertyCard and ShimmerCard with surfaceVariant Surface and shapes.medium
- Extracted all PropertyDetailScreen composables into detail/components/ (one file each)
- Replaced PropertyListScreen with ExploreSection (pagination, search, pull-to-refresh, offline banner)
- Added StarRating, FacilityChip, FacilityDetailBottomSheet, PropertyMapView (OSMDroid + user location), button library, and app bar components
- Added HomeScreen with bottom navigation (Explore / Saved / Bookings / Profile)
- Added tests for GetPropertiesUseCase, GetExchangeRatesUseCase, and ObserveNetworkStatusUseCase
- Added Compose UI tests for PropertyCard, CurrencySelector, EmptyState, and ErrorState
- Fixed accessibility: CurrencySelector tabs now expose Role.Tab + selected state; FacilityChip exposes contentDescription

Co-Authored-By: Claude Sonnet 4.6 <noreply@anthropic.com>
```

---

## PR title

```
MOBI-007: UI polish, complete test coverage, and accessibility fixes
```

---

## PR description

```markdown
## Summary

- **UI overhaul**: `CurrencySelector` rewritten as a custom animated segmented control; `PropertyCard` and `ShimmerCard` redesigned with `surfaceVariant` + `shapes.medium`; `FacilityChip` pinned to 80×80 square with single-line label
- **Detail screen refactor**: All composables extracted from `PropertyDetailScreen` into `detail/components/` (one file per composable); facilities displayed as an auto-scrolling horizontal `LazyRow` with icons, tappable bottom sheet descriptions, and an interactive OSMDroid map with user location overlay
- **Explore screen**: Old `PropertyListScreen` replaced with `ExploreSection` — pagination (6-item pages), debounced search, pull-to-refresh, offline banner, and empty/error states
- **Navigation**: New `HomeScreen` with four-tab bottom nav (Explore, Saved, Bookings, Profile); `AppNavGraph` updated to Home → Detail flow
- **Tests**: Added `GetPropertiesUseCaseImplTest`, `GetExchangeRatesUseCaseImplTest`, `ObserveNetworkStatusUseCaseImplTest`; added Compose UI tests for `PropertyCard`, `CurrencySelector`, `EmptyState`, `ErrorState`
- **Accessibility**: `CurrencySelector` tabs annotated with `Role.Tab` + `selected` semantics; `FacilityChip` exposes `contentDescription` for TalkBack

## Test plan

- [ ] `./gradlew :stayout-domain:test` — all use case tests pass
- [ ] `./gradlew :stayout-presentation:test` — all VM/mapper/state tests pass
- [ ] `./gradlew :stayout-presentation:connectedAndroidTest` — all 4 Compose UI test classes pass on device
- [ ] Manual smoke: launch app → browse list → search → open detail → switch currencies → tap facility chip
- [ ] TalkBack: CurrencySelector announces tab name + selected state; FacilityChip announces chip name

🤖 Generated with [Claude Code](https://claude.com/claude-code)
```