# StayScout

An Android app for browsing and exploring hostel/property listings, built as part of a code challenge. It fetches properties from a remote API, caches them locally for offline use, displays live exchange rates, and lets users explore property details with multi-currency pricing.

---

## Table of Contents

- [Screenshots](#screenshots)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [Module Map](#module-map)
- [Project Setup](#project-setup)
- [Running the App](#running-the-app)
- [Developer Onboarding](#developer-onboarding)
- [Quality Gates](#quality-gates)
- [Implementation Status](#implementation-status)
- [Planned Features](#planned-features)

---

## Tech Stack

| Area | Library / Tool | Version |
|---|---|---|
| Language | Kotlin | 2.3.21 |
| UI | Jetpack Compose + Material 3 | BOM 2026.02.01 |
| DI | Hilt | 2.59.2 |
| Navigation | Navigation Compose | 2.9.8 |
| Networking | Retrofit + OkHttp | 2.11.0 / 4.12.0 |
| Serialization | kotlinx-serialization-json | 1.7.3 |
| Image loading | Coil | 2.7.0 |
| Local database | Room | 2.7.1 |
| Async | Kotlin Coroutines + Flow | 1.9.0 |
| HTTP inspector | Chucker (debug only) | 4.0.0 |
| Code generation | KSP | 2.3.9 |
| Java 8+ APIs on API 24 | Core Library Desugaring | 2.1.4 |
| Linting | ktlint + Twitter Compose Rules | 1.5.0 / 0.6.0 |
| Static analysis | detekt | 2.0.0-alpha.3 |
| Code coverage | Kover | 0.9.8 |
| Testing | JUnit 4 + MockK + Coroutines Test | — |

---

## Architecture

StayScout uses **Clean Architecture** split across four Gradle modules, combined with the **MVI (Model-View-Intent)** pattern in the presentation layer.

```
┌────────────────────────────────────────────────────────────┐
│                          :app                              │
│  Entry point · Hilt setup · Navigation graph              │
└───────────────────────┬────────────────────────────────────┘
                        │ depends on
          ┌─────────────┴──────────────┐
          ▼                            ▼
┌──────────────────┐      ┌───────────────────────────┐
│ :stayout-data    │      │  :stayout-presentation    │
│                  │      │                           │
│ Retrofit APIs    │      │  Compose screens          │
│ Room DAOs/DB     │      │  MVI ViewModels           │
│ Repository impls │      │  UI components            │
│ Remote mappers   │      │  Presentation mappers     │
│ Local mappers    │      │  UI models                │
└────────┬─────────┘      └──────────┬────────────────┘
         │                           │
         └──────────┬────────────────┘
                    │ depends on
                    ▼
          ┌──────────────────────┐
          │   :stayout-domain    │
          │                      │
          │  Domain models       │
          │  Repository interfaces│
          │  Use cases (abstract)│
          └──────────────────────┘
```

### MVI pattern

Every screen follows a strict three-file MVI contract:

```
PropertyListState.kt   ← sealed interface (Loading | Success | Error)
PropertyListIntent.kt  ← sealed interface (user actions)
PropertyListEvent.kt   ← sealed interface (one-shot side effects)
```

`BaseViewModel<State, Intent, Event>` wires them together. ViewModels expose:
- `state: StateFlow<State>` — collected in Composables via `collectAsStateWithLifecycle`
- `events: Flow<Event>` — one-shot events collected in `LaunchedEffect`
- `onIntent(intent)` — single entry point for all user interactions

### Data flow

```
UI Intent
   │
   ▼
ViewModel.onIntent()
   │
   ▼
UseCase (suspend / Flow)
   │
   ▼
Repository (interface in :domain, impl in :data)
   │
   ├── Remote (Retrofit API)  ─┐
   └── Local  (Room DAO)      ─┴── mapper → domain model → state update
```

### Offline-first caching

Both `PropertyRepositoryImpl` and `RatesRepositoryImpl` attempt the network first, persist the response to Room, and fall back to the cached data on failure. The app is fully usable without a connection if it has been opened at least once online.

### Network status

`NetworkStatusRepositoryImpl` wraps `ConnectivityManager.NetworkCallback` in a `callbackFlow`. `ObserveNetworkStatusUseCase` exposes this as a `Flow<Boolean>` and both ViewModels subscribe to it on init, reflecting offline status in the UI via an `OfflineBanner` component.

---

## Module Map

```
StayScout/
├── app/                            # :app module
│   └── src/main/
│       ├── MainActivity.kt         # Single Activity, theme state holder
│       ├── StayScoutApplication.kt # Hilt @HiltAndroidApp entry point
│       ├── di/AppModule.kt         # App-level Hilt bindings
│       └── navigation/AppNavGraph.kt # Compose navigation host
│
├── stayout-domain/                 # :stayout-domain module (pure Kotlin)
│   └── src/main/java/.../domain/
│       ├── model/                  # Domain models (PropertyDomainModel, etc.)
│       ├── repository/             # Repository interfaces
│       └── usecase/                # Abstract use cases + base classes
│
├── stayout-data/                   # :stayout-data module
│   └── src/main/java/.../data/
│       ├── di/                     # Hilt modules (Network, Database, Mapper, Repository)
│       ├── local/
│       │   ├── StayScoutDatabase.kt  # Room database
│       │   ├── dao/                  # Room DAOs
│       │   ├── entity/               # Room entities
│       │   ├── mapper/               # Entity ↔ Domain mappers
│       │   └── converter/            # FacilitiesConverter (JSON ↔ list)
│       ├── remote/
│       │   ├── api/                  # Retrofit service interfaces
│       │   └── model/                # Network response data models
│       ├── mapper/                   # Network model → Domain mappers
│       ├── repository/               # Repository implementations
│       └── serializer/               # BigDecimalSerializer, LocalDateSerializer
│
└── stayout-presentation/           # :stayout-presentation module
    └── src/main/java/.../presentation/
        ├── base/                   # BaseViewModel, BaseState/Intent/Event
        ├── components/             # Reusable Compose components
        ├── list/                   # Property list screen (MVI: State/Intent/Event/ViewModel/Screen)
        ├── detail/                 # Property detail screen (MVI: State/Intent/Event/ViewModel/Screen)
        ├── mapper/                 # Domain → UI model mappers
        ├── model/                  # UI models (PropertyUiModel, etc.)
        ├── preview/                # PreviewData for Compose previews
        ├── provider/               # AndroidResourceProvider (string resources in ViewModels)
        └── theme/                  # Colors, Typography, Shapes, Dimens, PreviewThemes
```

---

## Project Setup

### Prerequisites

| Tool | Version |
|---|---|
| Android Studio | Meerkat 2025.1.1+ (or any version supporting AGP 9.1.1) |
| JDK | 11 (bundled in Android Studio JBR) |
| Android SDK | API 24 (min) — API 36 (compile / target) |
| Git | 2.x+ |

### 1. Clone the repository

```bash
git clone <repository-url>
cd StayScout
```

### 2. Install git hooks (required once per clone)

```bash
./scripts/install-git-hooks.sh
```

This sets `core.hooksPath = .githooks` so the pre-commit and commit-msg hooks are active.

### 3. Environment / build configuration

The app reads API base URLs from per-variant properties files:

| File | Variant |
|---|---|
| `config/debug.properties` | `debug` |
| `config/qa.properties` | `qa` |
| `config/release.properties` | `release` |

Each file must contain:
```properties
BASE_URL=https://gist.githubusercontent.com/
```

These files are committed to the repository. Do **not** put secrets in these files — they are version-controlled.

### 4. Open in Android Studio

Open the root `StayScout/` directory. Android Studio will sync Gradle automatically.

---

## Running the App

### From Android Studio

Select the `debug` or `qa` build variant from the **Build Variants** panel, then click **Run**.

### From the command line

```bash
# Build and install the debug APK
./gradlew installDebug

# Build the QA APK
./gradlew assembleQa

# Run all unit tests
./gradlew testDebugUnitTest

# Generate coverage report (HTML)
./gradlew koverHtmlReport
# Report → build/reports/kover/html/index.html

# Run ktlint auto-formatter
./gradlew ktlintFormat

# Run static analysis
./gradlew detekt
```

> **Note:** If you see `Unable to locate a Java Runtime`, export `JAVA_HOME` first:
> ```bash
> export JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home"
> ```

---

## Developer Onboarding

### Step 1 — Understand the module boundaries

Before writing any code, read this rule: **no module may depend on a higher-level module.**

```
:app → :stayout-presentation → :stayout-domain  ✅
:app → :stayout-data         → :stayout-domain  ✅
:stayout-domain → anything else                  ❌
:stayout-data   → :stayout-presentation          ❌
```

### Step 2 — Understand naming conventions

| Layer | Suffix | Example |
|---|---|---|
| Network response | `DataModel` | `PropertyDataModel` |
| Room entity | `Entity` | `PropertyEntity` |
| Domain model | `DomainModel` | `PropertyDomainModel` |
| UI model | `UiModel` | `PropertyUiModel` |
| Mapper (network→domain) | `ToDomainMapper` | `PropertyToDomainMapper` |
| Mapper (domain→entity) | `DomainToEntityMapper` | `PropertyDomainToEntityMapper` |
| Mapper (entity→domain) | `EntityToDomainMapper` | `PropertyEntityToDomainMapper` |
| Mapper (domain→UI) | `ToPresentationMapper` | `PropertyToPresentationMapper` |

### Step 3 — Adding a new screen

Every new screen needs exactly **5 files** in its own package under `stayout-presentation`:

```
myfeature/
  MyFeatureState.kt    ← sealed interface : BaseState
  MyFeatureIntent.kt   ← sealed interface : BaseIntent
  MyFeatureEvent.kt    ← sealed interface : BaseEvent
  MyFeatureViewModel.kt
  MyFeatureScreen.kt
```

Never merge State/Intent/Event into a single file.

### Step 4 — Adding a new use case

1. Define the abstract class in `:stayout-domain/usecase/`:

```kotlin
abstract class GetSomethingUseCase(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : BaseNoParamUseCase<Result<SomethingDomainModel>>(dispatcher)
```

2. Implement it in `:stayout-data` (or `:stayout-domain` if it has no I/O):

```kotlin
class GetSomethingUseCaseImpl @Inject constructor(
    private val repository: SomethingRepository,
) : GetSomethingUseCase() {
    override suspend fun execute(): Result<SomethingDomainModel> =
        repository.getSomething()
}
```

3. Bind the impl in a Hilt module (`DomainModule.kt`).

### Step 5 — Writing tests

- Mock every dependency with `mockk` — never instantiate real collaborators.
- ViewModel tests: `Dispatchers.setMain(UnconfinedTestDispatcher())` in `@Before`, `resetMain()` in `@After`.
- One-shot events: collect with `viewModel.events.first()`, not `collect {}`.
- Coverage requirement: every modified or added `.kt` file must have ≥ 90% line coverage.

### Step 6 — Branch and commit conventions

```
Branch:  MOBI-<ticket>-<kebab-description>
         e.g. MOBI-1234-add-booking-flow

Commit:  MOBI-<ticket>: <summary>
         e.g. MOBI-1234: add booking flow skeleton
```

Direct commits to `main`, `master`, or `develop` are blocked by the pre-commit hook.

### Step 7 — Before pushing

The pre-commit hook runs these checks automatically, but you can run them manually at any time:

```bash
./gradlew ktlintFormat          # auto-fix formatting
./gradlew ktlintCheck           # verify formatting (read-only)
./gradlew detekt                # static analysis
./gradlew testDebugUnitTest     # unit tests
./gradlew koverXmlReport        # coverage report
bash scripts/check_coverage.sh  # coverage gate (≥ 90%)
```

---

## Quality Gates

The pre-commit hook enforces (in order):

| Step | Check | Failure action |
|---|---|---|
| 1 | Branch name matches `MOBI-<N>-<kebab>` | Commit blocked |
| 2 | ktlint auto-formats staged files | Auto-fixed, re-staged |
| 3 | detekt passes with no new findings | Commit blocked |
| 4 | All unit tests pass | Commit blocked |
| 5 | Every changed `.kt` file has ≥ 90% line coverage | Commit blocked |

The commit-msg hook enforces that the first line matches `MOBI-<N>: <message>`.

**Coverage exclusions** (no tests required):
- Hilt-generated and DI module files
- Compose screens, components, theme files
- Android framework wrappers (`*Activity`, `*Application`, `*Database`)
- Sealed event/intent declarations
- Abstract base classes
- Pure delegation use cases (`GetPropertiesUseCase`, `GetExchangeRatesUseCase`, `ObserveNetworkStatusUseCase`)
- Framework adapter files (`AndroidResourceProvider`, `NetworkStatusRepositoryImpl`)

---

## Implementation Status

### Challenge requirements

| # | Requirement | Status | Notes |
|---|---|---|---|
| 1 | Fetch and display list of properties | ✅ Done | `PropertyListScreen` with `PropertyListViewModel` |
| 2 | Property list with name, rating, price, featured badge, type | ✅ Done | `PropertyCard` component |
| 3 | Property detail screen | ✅ Done | `PropertyDetailScreen` with full detail body |
| 4 | Property detail: image, name, address, rating, type, overview | ✅ Done | Hero image via Coil, all fields displayed |
| 5 | Property detail: facilities list | ✅ Done | `FlowRow` chips grouped by category |
| 6 | Currency conversion (EUR / USD / GBP) | ✅ Done | Live rates from API, `CurrencySelector` component |
| 7 | Exchange rates API integration | ✅ Done | `RatesRepositoryImpl` + `GetExchangeRatesUseCase` |
| 8 | Offline support / caching | ✅ Done | Room DB for properties, location, and rates; cache fallback on network failure |
| 9 | Stats tracking (load durations) | ✅ Done | `StatsRepositoryImpl` fires-and-forgets via `@ApplicationScope` coroutine |
| 10 | Search / filter properties by name | ✅ Done | `UpdateSearch` intent, case-insensitive filter |
| 11 | Loading states | ✅ Done | Shimmer skeleton on detail, `LoadingState` spinner on list |
| 12 | Error states with retry | ✅ Done | `ErrorState` component with retry callback |

### Beyond the challenge requirements

These were added on top of the challenge spec:

| Feature | Description |
|---|---|
| Clean Architecture modules | Four Gradle modules with enforced dependency direction |
| MVI pattern with base classes | `BaseViewModel<State, Intent, Event>` shared across all screens |
| Pull-to-refresh | `PullToRefreshBox` on the property list |
| Infinite scroll / pagination | Loads 6 properties per page, triggers `LoadMore` near end of list |
| Scroll position restoration | `SavedStateHandle` persists the first visible list item across back-stack |
| Dark / light theme toggle | Persistent in-session toggle via `MainActivity` state hoisting |
| Offline banner | `OfflineBanner` component shown at the top of both screens when offline |
| Free cancellation badge | Highlighted in detail screen with a themed `tertiaryContainer` surface |
| Shimmer skeleton loading | Animated placeholder on detail screen while data loads |
| Rates unavailable warning | Error label shown if exchange rates could not be fetched |
| HTML overview rendering | `HtmlCompat.fromHtml` strips HTML tags from the overview text |
| Chucker (debug/QA) | In-app HTTP inspector; excluded from release builds |
| ktlint + Twitter Compose Rules | Formatting enforced with Compose-specific rule set |
| detekt static analysis | Custom config with Compose rule integration |
| Kover coverage gate | 90%+ line coverage required on every changed file |
| Pre-commit hooks | Branch name, commit message, format, lint, test, coverage all enforced locally |
| Multi-variant build config | `debug`, `qa`, and `release` variants with separate base URLs and app names |
| Unit test suite | ~90 tests across all modules (mappers, serializers, repositories, ViewModels, state) |
| Compose Multipreview | `@PreviewThemes` annotation for light + dark preview in one annotation |

---

## Planned Features

These are intentionally not yet implemented and will be added in future iterations:

| Feature | Notes |
|---|---|
| Image gallery carousel | The API response includes `imageGallery`; a swipeable full-screen gallery is planned |
| Booking / reservation flow | A complete booking flow with availability check |
| Map view | Property locations on an interactive map |
| Favourites | Locally saved favourite properties with Room |
| Filter and sort | Filter by type, rating range, price range; sort by price / rating |
| Share property | Deep-link share via Android `ShareSheet` |
| Instrumented / UI tests | Compose UI tests with Espresso and `createAndroidComposeRule` |
| CI/CD pipeline | GitHub Actions workflow for pull request checks |
| Accessibility audit | TalkBack + screen reader support; content descriptions review |
| Analytics | Replace fire-and-forget stats with a proper analytics SDK |
| Pagination from API | Currently the API returns all properties at once; move to server-side pagination when available |