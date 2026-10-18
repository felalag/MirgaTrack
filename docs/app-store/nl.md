# App Store metadata: Dutch

App Store Connect localization "Dutch". The English source and the field limits are in [APP_STORE.md](../APP_STORE.md). The terms follow [LOCALIZATION_GLOSSARY.md](../LOCALIZATION_GLOSSARY.md): "aanval" for attack and episode. The counts were made with Python `len()` and `len(text.encode("utf-8"))` on October 18, 2026.

## App information

| Field | Value | Count | Limit |
|---|---|---|---|
| Name | MigraTrack | 10 characters | 30 characters |
| Subtitle | Migrainedagboek en rapporten | 28 characters | 30 characters |
| Privacy Policy URL | https://felalag.github.io/MirgaTrack/privacy/ (the English page, plan section 4.2) | | |

## Version information

### Promotional text

105 characters of 170.

```text
Noteer migraineaanvallen op uw iPhone of Apple Watch, ontdek uw patronen en deel een rapport met uw arts.
```

### Description

1731 characters of 4000.

```text
MigraTrack is een dagboek voor uw migraineaanvallen. Noteer een aanval in een paar seconden, ontdek uw patronen en neem een duidelijk rapport mee naar uw volgende afspraak.

AANVAL NOTEREN
- Begintijd, pijnintensiteit van 1 tot 10, aura, klachten, triggers en medicijnen.
- Uw gewone intensiteit en uw vaste aanvalsmedicatie staan al ingevuld bij elke nieuwe aanval, dus vaak is één tik genoeg.
- Noteer een aanval op een eerdere dag via de kalender of bewerk hem later.

UW PATRONEN
- Het overzicht toont uw pijnvrije dagen en uw laatste aanvallen.
- Inzichten toont aanvallen per week, maand, zes maanden en jaar, met een maandkalender.

GEZONDHEID-APP
- Met uw toestemming leest MigraTrack uw slaap uit de Gezondheid-app en toont de slaap van vannacht in het overzicht.
- MigraTrack schrijft niets naar de Gezondheid-app.

APPLE WATCH
- Uw pijnvrije dagen om uw pols.
- Draai aan de Digital Crown om de intensiteit in te stellen en noteer de aanval. De aanval verschijnt op uw iPhone.
- Voeg de complicatie 'Nieuwe aanval' toe aan uw wijzerplaat of gebruik de regelaar in het bedieningspaneel.

SIRI EN OPDRACHTEN
- Zeg 'Migraine noteren in MigraTrack' om een aanval met uw gewone intensiteit te noteren.

RAPPORTEN VOOR UW ARTS
- Exporteer een PDF-rapport of een Excel-bestand voor de periode die u kiest.
- Kies wat het rapport bevat: medicatie, aura en waarschuwingsklachten, pijngrafieken.

PRIVACY VANAF HET BEGIN
- Geen account, geen server, geen analyse, geen advertenties.
- Uw gegevens blijven op uw iPhone en uw Apple Watch.
- Instellingen › Alle gegevens verwijderen wist alles op elk moment.

MigraTrack is geen medisch hulpmiddel en geeft geen medisch advies. Overleg met uw arts voordat u uw behandeling verandert.
```

### Keywords

82 bytes in UTF-8 (82 characters) of 100 bytes. The name and the subtitle are searchable already, so the list does not repeat "migraine", "dagboek" or "rapporten".

```text
hoofdpijn,aanval,aura,pijn,trigger,slaap,arts,kalender,pdf,watch,medicatie,logboek
```

### What's New

For the version that adds this localization:

```text
MigraTrack is nu in het Nederlands: app, PDF- en Excel-rapporten, Apple Watch en Siri.
```

## Siri

The description quotes the Siri phrase "Migraine noteren in MigraTrack" from `iosApp/iosApp/AppShortcuts.xcstrings`. Siri speaks this language ([iOS feature availability](https://www.apple.com/ios/feature-availability/), Siri section, read on October 18, 2026). Check the phrase with Product › App Shortcuts Preview before the release.
