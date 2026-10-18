# App Store metadata: Norwegian

App Store Connect localization "Norwegian". The English source and the field limits are in [APP_STORE.md](../APP_STORE.md). The terms follow [LOCALIZATION_GLOSSARY.md](../LOCALIZATION_GLOSSARY.md): "anfall" for attack and episode. The counts were made with Python `len()` and `len(text.encode("utf-8"))` on October 18, 2026.

## App information

| Field | Value | Count | Limit |
|---|---|---|---|
| Name | MigraTrack | 10 characters | 30 characters |
| Subtitle | Migrenedagbok og rapporter | 26 characters | 30 characters |
| Privacy Policy URL | https://felalag.github.io/MirgaTrack/privacy/ (the English page, plan section 4.2) | | |

## Version information

### Promotional text

103 characters of 170.

```text
Registrer migreneanfall på iPhone eller Apple Watch, se mønstrene dine og del en rapport med legen din.
```

### Description

1657 characters of 4000.

```text
MigraTrack er en dagbok for migreneanfallene dine. Registrer et anfall på sekunder, se mønstrene dine over tid og ta med en tydelig rapport til neste time.

REGISTRER ET ANFALL
- Starttidspunkt, smerteintensitet fra 1 til 10, aura, symptomer, utløsere og legemidler.
- Den typiske intensiteten og de vanlige anfallsmedisinene dine fylles ut i hvert nytt anfall, så ett trykk er ofte nok.
- Registrer et anfall på en tidligere dag fra kalenderen, eller rediger det senere.

SE MØNSTRENE DINE
- Oversikt viser de smertefrie dagene og de siste anfallene dine.
- Innsikt viser anfall per uke, måned, seks måneder og år, med en månedskalender.

HELSE-APPEN
- Med din tillatelse leser MigraTrack søvnen din fra Helse-appen og viser nattens søvn i Oversikt.
- MigraTrack skriver ingenting til Helse-appen.

APPLE WATCH
- Se de smertefrie dagene dine på håndleddet.
- Vri på Digital Crown for å stille inn intensiteten, og registrer anfallet. Anfallet vises på iPhonen.
- Legg til komplikasjonen «Nytt anfall» på urskiven, eller bruk kontrollen i Kontrollsenter.

SIRI OG SNARVEIER
- Si «Registrer migrene i MigraTrack» for å registrere et anfall med den typiske intensiteten din.

RAPPORTER TIL LEGEN DIN
- Eksporter en PDF-rapport eller en Excel-fil for perioden du velger.
- Velg hva rapporten inneholder: legemidler, aura og varselsymptomer og smertegrafer.

PERSONVERN FRA STARTEN
- Ingen konto, ingen server, ingen analyse, ingen reklame.
- Dataene dine blir på iPhonen og Apple Watch.
- Innstillinger › Slett alle data sletter alt når som helst.

MigraTrack er ikke medisinsk utstyr og gir ikke medisinske råd. Snakk med legen din før du endrer behandlingen.
```

### Keywords

86 bytes in UTF-8 (84 characters) of 100 bytes. The name and the subtitle are searchable already, so the list does not repeat "migrene", "dagbok" or "rapporter".

```text
hodepine,anfall,aura,smerte,utløser,søvn,legemiddel,lege,kalender,pdf,klokke,triptan
```

### What's New

For the version that adds this localization:

```text
MigraTrack finnes nå på norsk: app, PDF- og Excel-rapporter, Apple Watch og Siri.
```

## Siri

Siri speaks this language ([iOS feature availability](https://www.apple.com/ios/feature-availability/), Siri section, read on October 18, 2026). The description quotes the Siri phrase "Registrer migrene i MigraTrack" from `iosApp/iosApp/AppShortcuts.xcstrings`. Check the phrase with Product › App Shortcuts Preview before the release.
