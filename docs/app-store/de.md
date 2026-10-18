# App Store metadata: German

App Store Connect localization "German". The English source and the field limits are in [APP_STORE.md](../APP_STORE.md). The terms follow [LOCALIZATION_GLOSSARY.md](../LOCALIZATION_GLOSSARY.md): "Attacke" for attack and episode. The counts were made with Python `len()` and `len(text.encode("utf-8"))` on October 18, 2026.

## App information

| Field | Value | Count | Limit |
|---|---|---|---|
| Name | MigraTrack | 10 characters | 30 characters |
| Subtitle | Migräne-Tagebuch & Berichte | 27 characters | 30 characters |
| Privacy Policy URL | https://felalag.github.io/MirgaTrack/privacy/ (the English page, plan section 4.2) | | |

## Version information

### Promotional text

146 characters of 170.

```text
Tragen Sie Migräneattacken auf dem iPhone oder der Apple Watch ein, erkennen Sie Muster und zeigen Sie Ihrer Ärztin oder Ihrem Arzt einen Bericht.
```

### Description

1903 characters of 4000.

```text
MigraTrack ist ein Tagebuch für Ihre Migräneattacken. Tragen Sie eine Attacke in Sekunden ein, erkennen Sie Muster und bringen Sie einen klaren Bericht zum nächsten Termin.

ATTACKE EINTRAGEN
- Beginn, Schmerzintensität von 1 bis 10, Aura, Symptome, Auslöser und Medikamente.
- Ihre typische Intensität und Ihre gewohnten Akutmedikamente sind bei jeder neuen Attacke schon ausgefüllt, oft genügt ein Tippen.
- Tragen Sie eine Attacke an einem früheren Tag im Kalender ein oder bearbeiten Sie sie später.

MUSTER ERKENNEN
- Die Übersicht zeigt Ihre schmerzfreien Tage und Ihre letzten Attacken.
- Die Analyse zeigt Attacken pro Woche, Monat, sechs Monate und Jahr, mit einem Monatskalender.

APPLE HEALTH
- Mit Ihrer Erlaubnis liest MigraTrack Ihren Schlaf aus der Health-App und zeigt den Schlaf der letzten Nacht in der Übersicht.
- MigraTrack schreibt nichts in die Health-App.

APPLE WATCH
- Ihre schmerzfreien Tage am Handgelenk.
- Drehen Sie die Digital Crown, um die Intensität einzustellen, und tragen Sie die Attacke ein. Sie erscheint auf Ihrem iPhone.
- Fügen Sie die Komplikation „Neue Attacke“ zu Ihrem Zifferblatt hinzu oder nutzen Sie die Steuerung im Kontrollzentrum.

SIRI UND KURZBEFEHLE
- Sagen Sie „Migräne in MigraTrack eintragen“, um eine Attacke mit Ihrer typischen Intensität einzutragen.

BERICHTE FÜR IHRE ÄRZTIN ODER IHREN ARZT
- Exportieren Sie einen PDF-Bericht oder eine Excel-Datei für den Zeitraum, den Sie wählen.
- Wählen Sie, was der Bericht enthält: Medikamente, Aura und Warnsymptome, Schmerzdiagramme.

DATENSCHUTZ VON ANFANG AN
- Kein Konto, kein Server, keine Analyse, keine Werbung.
- Ihre Daten bleiben auf Ihrem iPhone und Ihrer Apple Watch.
- „Einstellungen“ › „Alle Daten löschen“ löscht jederzeit alles.

MigraTrack ist kein Medizinprodukt und gibt keine medizinische Beratung. Sprechen Sie mit Ihrer Ärztin oder Ihrem Arzt, bevor Sie Ihre Behandlung ändern.
```

### Keywords

80 bytes in UTF-8 (79 characters) of 100 bytes. The name and the subtitle are searchable already, so the list does not repeat "Migräne", "Tagebuch" or "Berichte".

```text
Kopfschmerz,Attacke,Anfall,Aura,Auslöser,Schlaf,Arzt,Kalender,pdf,Watch,Schmerz
```

### What's New

For the version that adds this localization:

```text
MigraTrack ist jetzt auf Deutsch: App, PDF- und Excel-Berichte, Apple Watch und Siri.
```

## Siri

The description quotes the Siri phrase "Migräne in MigraTrack eintragen" from `iosApp/iosApp/AppShortcuts.xcstrings`. Siri speaks this language ([iOS feature availability](https://www.apple.com/ios/feature-availability/), Siri section, read on October 18, 2026). Check the phrase with Product › App Shortcuts Preview before the release.
