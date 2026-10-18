# App Store metadata: Swedish

App Store Connect localization "Swedish". The English source and the field limits are in [APP_STORE.md](../APP_STORE.md). The terms follow [LOCALIZATION_GLOSSARY.md](../LOCALIZATION_GLOSSARY.md): "anfall" for attack and episode. The counts were made with Python `len()` and `len(text.encode("utf-8"))` on October 18, 2026.

## App information

| Field | Value | Count | Limit |
|---|---|---|---|
| Name | MigraTrack | 10 characters | 30 characters |
| Subtitle | Migrändagbok och rapporter | 26 characters | 30 characters |
| Privacy Policy URL | https://felalag.github.io/MirgaTrack/privacy/ (the English page, plan section 4.2) | | |

## Version information

### Promotional text

104 characters of 170.

```text
Registrera migränanfall på iPhone eller Apple Watch, se dina mönster och dela en rapport med din läkare.
```

### Description

1696 characters of 4000.

```text
MigraTrack är en dagbok för dina migränanfall. Registrera ett anfall på några sekunder, se dina mönster över tid och ta med en tydlig rapport till nästa besök.

REGISTRERA ETT ANFALL
- Starttid, smärtintensitet från 1 till 10, aura, symtom, triggrar och läkemedel.
- Din typiska intensitet och dina vanliga anfallsläkemedel fylls i automatiskt i varje nytt anfall, så ett tryck räcker ofta.
- Registrera ett anfall på en tidigare dag från kalendern eller redigera det senare.

SE DINA MÖNSTER
- Översikt visar dina smärtfria dagar och dina senaste anfall.
- Insikter visar anfall per vecka, månad, sex månader och år, med en månadskalender.

APPEN HÄLSA
- Med ditt tillstånd läser MigraTrack din sömn från appen Hälsa och visar nattens sömn i Översikt.
- MigraTrack skriver ingenting till appen Hälsa.

APPLE WATCH
- Se dina smärtfria dagar på handleden.
- Vrid på Digital Crown för att ställa in intensiteten och registrera anfallet. Anfallet visas på din iPhone.
- Lägg till komplikationen ”Nytt anfall” på urtavlan eller använd reglaget i Kontrollcenter.

SIRI OCH GENVÄGAR
- Säg ”Registrera migrän i MigraTrack” för att registrera ett anfall med din typiska intensitet.

RAPPORTER TILL DIN LÄKARE
- Exportera en PDF-rapport eller en Excel-fil för den period som du väljer.
- Välj vad rapporten innehåller: läkemedel, aura och varningssymtom samt smärtdiagram.

INTEGRITET FRÅN BÖRJAN
- Inget konto, ingen server, ingen analys, ingen reklam.
- Dina data stannar på din iPhone och din Apple Watch.
- Inställningar › Radera alla data raderar allt när som helst.

MigraTrack är inte en medicinteknisk produkt och ger inte medicinsk rådgivning. Prata med din läkare innan du ändrar din behandling.
```

### Keywords

91 bytes in UTF-8 (86 characters) of 100 bytes. The name and the subtitle are searchable already, so the list does not repeat "migrän", "dagbok" or "rapporter".

```text
huvudvärk,anfall,aura,smärta,trigger,sömn,läkemedel,läkare,kalender,pdf,klocka,triptan
```

### What's New

For the version that adds this localization:

```text
MigraTrack finns nu på svenska: appen, PDF- och Excel-rapporter, Apple Watch och Siri.
```

## Siri

Siri speaks this language ([iOS feature availability](https://www.apple.com/ios/feature-availability/), Siri section, read on October 18, 2026). The description quotes the Siri phrase "Registrera migrän i MigraTrack" from `iosApp/iosApp/AppShortcuts.xcstrings`. Check the phrase with Product › App Shortcuts Preview before the release.
