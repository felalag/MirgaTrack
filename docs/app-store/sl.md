# App Store metadata: Slovenian

App Store Connect localization "Slovenian". The English source and the field limits are in [APP_STORE.md](../APP_STORE.md). The terms follow [LOCALIZATION_GLOSSARY.md](../LOCALIZATION_GLOSSARY.md): "napad" for attack and episode. The counts were made with Python `len()` and `len(text.encode("utf-8"))` on October 18, 2026.

## App information

| Field | Value | Count | Limit |
|---|---|---|---|
| Name | MigraTrack | 10 characters | 30 characters |
| Subtitle | Dnevnik migrene in poročila | 27 characters | 30 characters |
| Privacy Policy URL | https://felalag.github.io/MirgaTrack/privacy/ (the English page, plan section 4.2) | | |

## Version information

### Promotional text

109 characters of 170.

```text
Beležite napade migrene v iPhonu ali uri Apple Watch, spoznajte svoje vzorce in poročilo delite z zdravnikom.
```

### Description

1689 characters of 4000.

```text
MigraTrack je dnevnik vaših napadov migrene. Napad zabeležite v nekaj sekundah, spremljajte svoje vzorce skozi čas in na naslednji pregled prinesite jasno poročilo.

BELEŽENJE NAPADA
- Začetek, jakost bolečine od 1 do 10, avra, simptomi, sprožilci in zdravila.
- Vaša običajna jakost in običajna zdravila za akutno zdravljenje so vnaprej vnesena v vsak nov napad, zato pogosto zadošča en dotik.
- Napad iz prejšnjega dne zabeležite iz koledarja, vsak napad pa lahko pozneje uredite.

VAŠI VZORCI
- Povzetek prikazuje dneve brez bolečin in zadnje napade.
- Analiza prikazuje napade po tednih, mesecih, polletjih in letih ter mesečni koledar.

APLIKACIJA ZDRAVJE
- Z vašim dovoljenjem MigraTrack prebere spanec iz aplikacije Zdravje in v Povzetku prikaže sinočnji spanec.
- MigraTrack v aplikacijo Zdravje ne zapiše ničesar.

APPLE WATCH
- Dnevi brez bolečin na zapestju.
- Z vrtenjem krone Digital Crown nastavite jakost in zabeležite napad. Napad se prikaže v iPhonu.
- Na številčnico dodajte zaplet »Nov napad« ali uporabite kontrolnik v Nadzornem središču.

BLIŽNJICE
- Dejanje »Nov napad« v aplikaciji Bližnjice ali v Spotlightu zabeleži napad z vašo običajno jakostjo.

POROČILA ZA ZDRAVNIKA
- Izvozite poročilo PDF ali datoteko Excel za obdobje, ki ga izberete.
- Izberite, kaj poročilo vsebuje: zdravila, avro in opozorilne simptome ter grafe bolečine.

ZASEBNOST OD ZAČETKA
- Brez računa, brez strežnika, brez analitike, brez oglasov.
- Vaši podatki ostanejo v iPhonu in uri Apple Watch.
- Nastavitve › Izbriši vse podatke kadar koli izbriše vse.

MigraTrack ni medicinski pripomoček in ne daje zdravniških nasvetov. Preden spremenite zdravljenje, se posvetujte s svojim zdravnikom.
```

### Keywords

81 bytes in UTF-8 (79 characters) of 100 bytes. The name and the subtitle are searchable already, so the list does not repeat "migrene", "dnevnik" or "poročila".

```text
glavobol,napad,avra,bolečina,sprožilec,spanec,zdravila,zdravnik,koledar,pdf,ura
```

### What's New

For the version that adds this localization:

```text
MigraTrack je zdaj v slovenščini: aplikacija, poročila PDF in Excel ter Apple Watch.
```

## Siri

Siri does not speak Slovenian ([iOS feature availability](https://www.apple.com/ios/feature-availability/), Siri section, read on October 18, 2026). The description names the App Shortcut "Nov napad" in the Shortcuts app and Spotlight instead.
