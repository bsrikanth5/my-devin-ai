#!/usr/bin/env bash
# Runs every lesson in order; stops at the first one that fails.
set -euo pipefail
cd "$(dirname "$0")"

for file in creational/*.java structural/*.java behavioral/*.java; do
  echo "===== $file ====="
  java "$file"
  echo
done
echo "All lessons ran successfully."
