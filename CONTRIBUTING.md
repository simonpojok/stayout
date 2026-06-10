# Contributing to StayScout

## One-time setup

Install the git hooks after cloning:

```sh
./scripts/install-git-hooks.sh
```

This sets `core.hooksPath = .githooks` so the pre-commit and commit-msg hooks run automatically.

---

## Branch naming

Branches must follow the pattern:

```
MOBI-<ticket>-<kebab-description>
```

Examples:
- `MOBI-1234-fix-login-crash`
- `MOBI-5678-add-property-filter`

Direct commits to `main`, `master`, and `develop` are blocked.

---

## Commit message format

The first line of every commit message must match:

```
MOBI-<ticket>: <summary>
```

Examples:
- `MOBI-1234: fix login crash on Android 14`
- `MOBI-5678: add currency filter to property list`

---

## What the pre-commit hook does (in order)

1. **Branch check** — rejects commits on protected branches or branches that don't match the naming convention.
2. **ktlint format** — auto-formats staged `.kt`/`.kts` files and re-stages them.
3. **detekt** — static analysis; fails on any new finding not in the per-module baseline.
4. **Unit tests** — runs `testDebugUnitTest`; fails on any test failure.
5. **Coverage gate** — runs `koverXmlReport` and checks that every added/modified `.kt` file has ≥ 90% line coverage.

---

## Running checks manually

```sh
# Format all Kotlin files
./gradlew ktlintFormat

# Check formatting without modifying
./gradlew ktlintCheck

# Static analysis
./gradlew detekt

# Unit tests
./gradlew testDebugUnitTest

# Coverage report (HTML + XML)
./gradlew koverHtmlReport

# Coverage gate only (uses last generated XML report)
bash scripts/check_coverage.sh --min-coverage 90
```

---

## Coverage exclusions

The following are excluded from the 90% coverage gate (no tests required):

- Hilt-generated code (`*_Hilt*`, `Hilt_*`, `*_Factory`, `*_MembersInjector`)
- DI modules (`**/di/**`, `*Module*`)
- Compose preview functions (`*Preview*`, `*ComposableSingletons*`)
- Theme files (`**/theme/**`)
- `BuildConfig`

---

## Emergency bypass

Use `--no-verify` only when absolutely necessary and document the reason in the PR description:

```sh
git commit --no-verify -m "MOBI-9999: emergency hotfix"
```