#!/usr/bin/env bash
set -euo pipefail

REPO_ROOT="$(git rev-parse --show-toplevel)"
cd "$REPO_ROOT"

git config core.hooksPath .githooks
chmod +x .githooks/*

echo "✅  Git hooks installed — hooks path set to .githooks/"
echo "    Active hooks: $(ls .githooks/)"