# App Store metadata: Czech

App Store Connect localization "Czech". The English source and the field limits are in [APP_STORE.md](../APP_STORE.md). The terms follow [LOCALIZATION_GLOSSARY.md](../LOCALIZATION_GLOSSARY.md): "záchvat" for attack and episode. The counts were made with Python `len()` and `len(text.encode("utf-8"))` on October 18, 2026.

## App information

| Field | Value | Count | Limit |
|---|---|---|---|
| Name | MigraTrack | 10 characters | 30 characters |
| Subtitle | Deník migrény a zprávy | 22 characters | 30 characters |
| Privacy Policy URL | https://felalag.github.io/MirgaTrack/privacy/ (the English page, plan section 4.2) | | |

## Version information

### Promotional text

110 characters of 170.

```text
Zaznamenávejte záchvaty migrény v iPhonu nebo na Apple Watch, sledujte své vzorce a sdílejte zprávu s lékařem.
```

### Description

1613 characters of 4000.

```text
MigraTrack je deník vašich záchvatů migrény. Zaznamenejte záchvat během pár sekund, sledujte své vzorce v čase a přineste na další návštěvu přehlednou zprávu.

ZÁZNAM ZÁCHVATU
- Začátek, intenzita bolesti od 1 do 10, aura, příznaky, spouštěče a léky.
- Vaše obvyklá intenzita a obvyklé akutní léky se předvyplní u každého nového záchvatu, často stačí jedno klepnutí.
- Záchvat z dřívějšího dne zapíšete z kalendáře a každý záchvat můžete později upravit.

VAŠE VZORCE
- Přehled ukazuje dny bez bolesti a poslední záchvaty.
- Analýza ukazuje záchvaty za týden, měsíc, šest měsíců a rok spolu s měsíčním kalendářem.

APLIKACE ZDRAVÍ
- S vaším svolením MigraTrack čte spánek z aplikace Zdraví a v Přehledu ukazuje spánek z minulé noci.
- MigraTrack do aplikace Zdraví nic nezapisuje.

APPLE WATCH
- Dny bez bolesti na zápěstí.
- Otáčením korunky Digital Crown nastavíte intenzitu a záchvat zapíšete. Záchvat se objeví ve vašem iPhonu.
- Přidejte si na ciferník komplikaci „Nový záchvat“ nebo použijte ovladač v Ovládacím centru.

ZKRATKY
- Akce „Nový záchvat“ v aplikaci Zkratky nebo ve Spotlightu zapíše záchvat s vaší obvyklou intenzitou.

ZPRÁVY PRO LÉKAŘE
- Exportujte zprávu PDF nebo soubor Excel za období, které si vyberete.
- Vyberte, co zpráva obsahuje: léky, auru a varovné příznaky a grafy bolesti.

SOUKROMÍ OD ZAČÁTKU
- Žádný účet, žádný server, žádná analytika, žádné reklamy.
- Vaše data zůstávají ve vašem iPhonu a na Apple Watch.
- Nastavení › Smazat všechna data kdykoli smaže vše.

MigraTrack není zdravotnický prostředek a neposkytuje lékařské rady. Než změníte léčbu, poraďte se se svým lékařem.
```

### Keywords

90 bytes in UTF-8 (80 characters) of 100 bytes. The name and the subtitle are searchable already, so the list does not repeat "migrény", "deník" or "zprávy".

```text
bolest hlavy,záchvat,aura,bolest,spouštěč,spánek,léky,lékař,kalendář,pdf,hodinky
```

### What's New

For the version that adds this localization:

```text
MigraTrack je nyní v češtině: aplikace, zprávy PDF a Excel i Apple Watch.
```

## Siri

Siri does not speak Czech ([iOS feature availability](https://www.apple.com/ios/feature-availability/), Siri section, read on October 18, 2026). The description names the App Shortcut "Nový záchvat" in the Shortcuts app and Spotlight instead.
