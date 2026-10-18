# App Store metadata: Finnish

App Store Connect localization "Finnish". The English source and the field limits are in [APP_STORE.md](../APP_STORE.md). The terms follow [LOCALIZATION_GLOSSARY.md](../LOCALIZATION_GLOSSARY.md): "kohtaus" for attack and episode. The counts were made with Python `len()` and `len(text.encode("utf-8"))` on October 18, 2026.

## App information

| Field | Value | Count | Limit |
|---|---|---|---|
| Name | MigraTrack | 10 characters | 30 characters |
| Subtitle | Migreenipäiväkirja ja raportit | 30 characters | 30 characters |
| Privacy Policy URL | https://felalag.github.io/MirgaTrack/privacy/ (the English page, plan section 4.2) | | |

## Version information

### Promotional text

112 characters of 170.

```text
Kirjaa migreenikohtaukset iPhonella tai Apple Watchilla, tunnista toistuvat kuviot ja jaa raportti lääkärillesi.
```

### Description

1679 characters of 4000.

```text
MigraTrack on migreenikohtaustesi päiväkirja. Kirjaa kohtaus sekunneissa, seuraa kuvioitasi ajan mittaan ja vie selkeä raportti seuraavalle vastaanotolle.

KIRJAA KOHTAUS
- Alkamisaika, kivun voimakkuus 1–10, aura, oireet, laukaisijat ja lääkkeet.
- Tavallinen voimakkuutesi ja tavalliset akuuttilääkkeesi täytetään valmiiksi jokaiseen uuteen kohtaukseen, joten usein yksi napautus riittää.
- Kirjaa aiemman päivän kohtaus kalenterista tai muokkaa kohtausta myöhemmin.

TUNNISTA KUVIOT
- Yhteenveto näyttää kivuttomat päiväsi ja viimeisimmät kohtauksesi.
- Analyysi näyttää kohtaukset viikoittain, kuukausittain, puolivuosittain ja vuosittain sekä kuukausikalenterin.

TERVEYS-APPI
- Luvallasi MigraTrack lukee unesi Terveys-apista ja näyttää viime yön unen Yhteenvedossa.
- MigraTrack ei kirjoita mitään Terveys-appiin.

APPLE WATCH
- Kivuttomat päivät ranteessasi.
- Säädä voimakkuus Digital Crownia kääntämällä ja kirjaa kohtaus. Kohtaus näkyy iPhonessasi.
- Lisää komplikaatio ”Uusi kohtaus” kellotauluun tai käytä säädintä Ohjauskeskuksessa.

SIRI JA PIKAKOMENNOT
- Sano ”Kirjaa migreeni sovelluksessa MigraTrack”, niin kohtaus kirjataan tavallisella voimakkuudellasi.

RAPORTIT LÄÄKÄRILLE
- Vie PDF-raportti tai Excel-tiedosto valitsemaltasi jaksolta.
- Valitse, mitä raportti sisältää: lääkkeet, aura ja varoitusoireet sekä kipukaaviot.

YKSITYISYYS ALUSTA ALKAEN
- Ei tiliä, ei palvelinta, ei analytiikkaa, ei mainoksia.
- Tietosi pysyvät iPhonessasi ja Apple Watchissasi.
- Asetukset › Poista kaikki tiedot poistaa kaiken milloin tahansa.

MigraTrack ei ole lääkinnällinen laite eikä anna lääketieteellisiä neuvoja. Keskustele lääkärin kanssa ennen kuin muutat hoitoasi.
```

### Keywords

94 bytes in UTF-8 (86 characters) of 100 bytes. The name and the subtitle are searchable already, so the list does not repeat "migreeni", "päiväkirja" or "raportit".

```text
päänsärky,kohtaus,aura,kipu,laukaisija,uni,lääke,lääkäri,kalenteri,pdf,kello,triptaani
```

### What's New

For the version that adds this localization:

```text
MigraTrack on nyt suomeksi: appi, PDF- ja Excel-raportit, Apple Watch ja Siri.
```

## Siri

Siri speaks this language ([iOS feature availability](https://www.apple.com/ios/feature-availability/), Siri section, read on October 18, 2026). The description quotes the Siri phrase "Kirjaa migreeni sovelluksessa MigraTrack" from `iosApp/iosApp/AppShortcuts.xcstrings`. Check the phrase with Product › App Shortcuts Preview before the release.
