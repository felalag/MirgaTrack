# App Store metadata: Danish

App Store Connect localization "Danish". The English source and the field limits are in [APP_STORE.md](../APP_STORE.md). The terms follow [LOCALIZATION_GLOSSARY.md](../LOCALIZATION_GLOSSARY.md): "anfald" for attack and episode. The counts were made with Python `len()` and `len(text.encode("utf-8"))` on October 18, 2026.

## App information

| Field | Value | Count | Limit |
|---|---|---|---|
| Name | MigraTrack | 10 characters | 30 characters |
| Subtitle | Migrænedagbog og rapporter | 26 characters | 30 characters |
| Privacy Policy URL | https://felalag.github.io/MirgaTrack/privacy/ (the English page, plan section 4.2) | | |

## Version information

### Promotional text

101 characters of 170.

```text
Registrer migræneanfald på iPhone eller Apple Watch, se dine mønstre, og del en rapport med din læge.
```

### Description

1639 characters of 4000.

```text
MigraTrack er en dagbog over dine migræneanfald. Registrer et anfald på få sekunder, se dine mønstre over tid, og tag en klar rapport med til næste aftale.

REGISTRER ET ANFALD
- Starttidspunkt, smerteintensitet fra 1 til 10, aura, symptomer, udløsere og medicin.
- Din typiske intensitet og din sædvanlige anfaldsmedicin udfyldes i hvert nyt anfald, så ét tryk er ofte nok.
- Registrer et anfald på en tidligere dag fra kalenderen, eller rediger det senere.

SE DINE MØNSTRE
- Oversigt viser dine smertefri dage og dine seneste anfald.
- Indsigt viser anfald pr. uge, måned, seks måneder og år med en månedskalender.

APPEN SUNDHED
- Med din tilladelse læser MigraTrack din søvn fra appen Sundhed og viser nattens søvn i Oversigt.
- MigraTrack skriver intet til appen Sundhed.

APPLE WATCH
- Se dine smertefri dage på håndleddet.
- Drej på Digital Crown for at indstille intensiteten, og registrer anfaldet. Anfaldet vises på din iPhone.
- Føj komplikationen "Nyt anfald" til urskiven, eller brug knappen i Kontrolcenter.

SIRI OG GENVEJE
- Sig "Registrer migræne i MigraTrack" for at registrere et anfald med din typiske intensitet.

RAPPORTER TIL DIN LÆGE
- Eksportér en PDF-rapport eller en Excel-fil for den periode, du vælger.
- Vælg, hvad rapporten indeholder: medicin, aura og varselssymptomer samt smertegrafer.

PRIVATLIV FRA BEGYNDELSEN
- Ingen konto, ingen server, ingen analyse, ingen reklamer.
- Dine data bliver på din iPhone og dit Apple Watch.
- Indstillinger › Slet alle data sletter alt når som helst.

MigraTrack er ikke medicinsk udstyr og giver ikke lægelig rådgivning. Tal med din læge, før du ændrer din behandling.
```

### Keywords

81 bytes in UTF-8 (78 characters) of 100 bytes. The name and the subtitle are searchable already, so the list does not repeat "migræne", "dagbog" or "rapporter".

```text
hovedpine,anfald,aura,smerte,udløser,søvn,medicin,læge,kalender,pdf,ur,triptan
```

### What's New

For the version that adds this localization:

```text
MigraTrack findes nu på dansk: app, PDF- og Excel-rapporter, Apple Watch og Siri.
```

## Siri

Siri speaks this language ([iOS feature availability](https://www.apple.com/ios/feature-availability/), Siri section, read on October 18, 2026). The description quotes the Siri phrase "Registrer migræne i MigraTrack" from `iosApp/iosApp/AppShortcuts.xcstrings`. Check the phrase with Product › App Shortcuts Preview before the release.
