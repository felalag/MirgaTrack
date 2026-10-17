#!/bin/zsh
# Takes the screenshots of docs/LOCALIZATION_PLAN.md, section 5.3, for each language.
#
#   scripts/l10n-screenshots.sh <iphone-udid> <out-dir> <language>[:<locale>] ...
#   scripts/l10n-screenshots.sh 7DA7DC22-... build/l10n-screenshots en:en_US ar:ar_SA en-XA ru-XC th-XT
#
# Install a Debug build first: the start screen comes from a launch argument that only Debug builds read.
# For the pseudo-locales, build it with MIGRATRACK_PSEUDO=true. Set WATCH=<watch-udid> to add the watch summary;
# the watch shows the pseudo-locales only through the Xcode scheme option App Language.
set -euo pipefail

udid=$1 out=$2
shift 2
screens=(Summary Log Insights Export Settings)
xcrun simctl status_bar "$udid" override --time 9:41 --batteryState charged --batteryLevel 100 --cellularBars 4 --wifiBars 3
[[ -n ${WATCH:-} ]] && xcrun simctl status_bar "$WATCH" override --time 9:41

for spec in "$@"; do
  language=${spec%%:*}
  locale=${spec#*:}
  [[ $locale == "$spec" ]] && locale=${language//-/_}
  mkdir -p "$out/$language"
  for screen in $screens; do
    xcrun simctl terminate "$udid" com.migratrack.app 2>/dev/null || true
    xcrun simctl launch "$udid" com.migratrack.app -AppleLanguages "($language)" -AppleLocale "$locale" -MigraTrackScreen "$screen" >/dev/null
    sleep 3
    xcrun simctl io "$udid" screenshot "$out/$language/$screen.png" >/dev/null 2>&1
  done
  if [[ -n ${WATCH:-} ]]; then
    xcrun simctl terminate "$WATCH" com.migratrack.app.watchkitapp 2>/dev/null || true
    xcrun simctl launch "$WATCH" com.migratrack.app.watchkitapp -AppleLanguages "($language)" -AppleLocale "$locale" >/dev/null
    sleep 3
    xcrun simctl io "$WATCH" screenshot "$out/$language/Watch.png" >/dev/null 2>&1
  fi
  echo "$language: $(ls "$out/$language" | wc -l | tr -d ' ') screenshots"
done
