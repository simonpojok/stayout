#!/usr/bin/env bash
# Per-file line-coverage gate against the Kover XML report (JaCoCo format).
# Usage: scripts/check_coverage.sh [--min-coverage 90] [--report path/to/report.xml]
set -euo pipefail

MIN_COVERAGE=90
REPORT="build/reports/kover/report.xml"

while [[ $# -gt 0 ]]; do
    case "$1" in
        --min-coverage) MIN_COVERAGE="$2"; shift 2 ;;
        --report)       REPORT="$2";       shift 2 ;;
        *) echo "Unknown arg: $1"; exit 1 ;;
    esac
done

REPO_ROOT="$(git rev-parse --show-toplevel)"
REPORT="$REPO_ROOT/$REPORT"

if [[ ! -f "$REPORT" ]]; then
    echo "❌  Coverage report not found: $REPORT"
    exit 1
fi

# Collect added/modified .kt files from the staged diff
CHANGED_FILES=()
while IFS= read -r line; do
    [[ -n "$line" ]] && CHANGED_FILES+=("$line")
done < <(
    git diff --cached --name-status --diff-filter=ACMR \
        | awk '{print $NF}' \
        | grep '\.kt$' \
        || true
)

if [[ ${#CHANGED_FILES[@]} -eq 0 ]]; then
    echo "ℹ️   No changed .kt files — skipping coverage gate."
    exit 0
fi

# Patterns to skip (mirrors Kover exclusions in build.gradle.kts)
is_excluded() {
    local path="$1"
    local name
    name="$(basename "$path" .kt)"
    # Hilt / generated
    [[ "$name" == *_Hilt*            ]] && return 0
    [[ "$name" == Hilt_*             ]] && return 0
    [[ "$name" == *_Factory          ]] && return 0
    [[ "$name" == *_MembersInjector  ]] && return 0
    [[ "$name" == *Module*           ]] && return 0
    [[ "$name" == *_HiltModules*     ]] && return 0
    [[ "$name" == *ComposableSingletons* ]] && return 0
    [[ "$name" == *Preview*          ]] && return 0
    [[ "$name" == *BuildConfig*      ]] && return 0
    # Android framework — untestable in unit tests
    [[ "$name" == *Activity          ]] && return 0
    [[ "$name" == *Application       ]] && return 0
    [[ "$name" == *Screen            ]] && return 0
    [[ "$name" == *Section           ]] && return 0
    [[ "$name" == *NavGraph          ]] && return 0
    [[ "$name" == *Database          ]] && return 0
    # Room migrations — require Android instrumented tests
    [[ "$path" == */migration/*      ]] && return 0
    # Sealed event/intent types — pure data declarations
    [[ "$name" == *Event             ]] && return 0
    [[ "$name" == *Intent            ]] && return 0
    # Abstract base classes — exercised indirectly through implementations
    [[ "$name" == Base*              ]] && return 0
    # Path-based exclusions
    [[ "$path" == */di/*             ]] && return 0
    [[ "$path" == */theme/*          ]] && return 0
    [[ "$path" == */components/*     ]] && return 0
    # Specific files with no independently testable logic
    [[ "$name" == "NetworkStatusRepositoryImpl" ]] && return 0
    [[ "$name" == "AndroidResourceProvider"     ]] && return 0
    [[ "$name" == "CachedResult"                ]] && return 0
    [[ "$name" == "PropertyDetailState"         ]] && return 0
    [[ "$name" == "GetExchangeRatesUseCase"     ]] && return 0
    [[ "$name" == "GetPropertiesUseCase"        ]] && return 0
    [[ "$name" == "ObserveNetworkStatusUseCase" ]] && return 0
    # JNI bridge — only external fun declarations, UnsatisfiedLinkError in JVM tests
    [[ "$name" == "NativeKeys"               ]] && return 0
    # Android Keystore provider unavailable in JVM unit tests
    [[ "$name" == "KeystorePassphraseCipher" ]] && return 0
    # Debug-only framework code — depends on Android runtime, not testable in JVM unit tests
    [[ "$name" == "DebugActivityFixer"       ]] && return 0
    [[ "$name" == "DebugNotificationHelper"  ]] && return 0
    [[ "$name" == "ShowCaseRoot"             ]] && return 0
    [[ "$name" == "ShowkaseLauncher"         ]] && return 0
    [[ "$name" == "HttpTransaction"          ]] && return 0
    # Pure @Serializable data models — no executable business logic beyond generated equals/copy
    [[ "$name" == *DataModel                 ]] && return 0
    return 1
}

# Extract LINE coverage for a given filename from the XML report.
# Returns "covered missed" or "" if not found / zero executable lines.
get_line_coverage() {
    local filename="$1"
    # Extract the sourcefile block for this file, then pull the LINE counter
    local block
    block="$(awk -v fname="$filename" '
        /<sourcefile name="/ { in_block = ($0 ~ "name=\"" fname "\"") }
        in_block { print }
        in_block && /<\/sourcefile>/ { in_block = 0 }
    ' "$REPORT")"

    [[ -z "$block" ]] && return 0

    local line_counter
    line_counter="$(echo "$block" | grep 'type="LINE"')"
    [[ -z "$line_counter" ]] && return 0

    local missed covered
    missed="$(echo "$line_counter" | sed 's/.*missed="\([0-9]*\)".*/\1/')"
    covered="$(echo "$line_counter" | sed 's/.*covered="\([0-9]*\)".*/\1/')"
    echo "$covered $missed"
}

FAILURES=()

for path in "${CHANGED_FILES[@]}"; do
    is_excluded "$path" && continue

    filename="$(basename "$path")"
    result="$(get_line_coverage "$filename")"
    [[ -z "$result" ]] && continue  # zero executable lines — skip

    covered="${result%% *}"
    missed="${result##* }"
    total=$(( covered + missed ))
    [[ "$total" -eq 0 ]] && continue

    # Integer percent (floor)
    pct=$(( covered * 100 / total ))

    if (( pct < MIN_COVERAGE )); then
        FAILURES+=("  $path  →  ${pct}%  (need ${MIN_COVERAGE}%)")
    fi
done

if [[ ${#FAILURES[@]} -gt 0 ]]; then
    echo "❌  Coverage below ${MIN_COVERAGE}% for ${#FAILURES[@]} file(s):"
    for f in "${FAILURES[@]}"; do echo "$f"; done
    exit 1
fi