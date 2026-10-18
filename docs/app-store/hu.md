# App Store metadata: Hungarian

App Store Connect localization "Hungarian". The English source and the field limits are in [APP_STORE.md](../APP_STORE.md). The terms follow [LOCALIZATION_GLOSSARY.md](../LOCALIZATION_GLOSSARY.md): "roham" for attack and episode. The counts were made with Python `len()` and `len(text.encode("utf-8"))` on October 18, 2026.

## App information

| Field | Value | Count | Limit |
|---|---|---|---|
| Name | MigraTrack | 10 characters | 30 characters |
| Subtitle | Migrénnapló és jelentések | 25 characters | 30 characters |
| Privacy Policy URL | https://felalag.github.io/MirgaTrack/privacy/ (the English page, plan section 4.2) | | |

## Version information

### Promotional text

114 characters of 170.

```text
Rögzítse migrénrohamait iPhone-on vagy Apple Watchon, ismerje meg mintázatait, és ossza meg a jelentést orvosával.
```

### Description

1763 characters of 4000.

```text
A MigraTrack a migrénrohamai naplója. Rögzítsen egy rohamot másodpercek alatt, kövesse mintázatait az idő során, és vigyen áttekinthető jelentést a következő vizsgálatra.

ROHAM RÖGZÍTÉSE
- Kezdés, fájdalomerősség 1-től 10-ig, aura, tünetek, kiváltó tényezők és gyógyszerek.
- Szokásos erőssége és szokásos akut gyógyszerei minden új rohamnál előre ki vannak töltve, így gyakran egy koppintás elég.
- Korábbi napra a naptárból rögzíthet rohamot, és bármelyik rohamot később szerkesztheti.

AZ ÖN MINTÁZATAI
- Az Összegzés a fájdalommentes napokat és a legutóbbi rohamokat mutatja.
- Az Elemzés a rohamokat hetente, havonta, féléves és éves bontásban mutatja, havi naptárral.

EGÉSZSÉG APP
- Az Ön engedélyével a MigraTrack beolvassa az alvását az Egészség appból, és az Összegzésben megmutatja a tegnap éjjeli alvást.
- A MigraTrack semmit sem ír az Egészség appba.

APPLE WATCH
- Fájdalommentes napok a csuklóján.
- A Digital Crown elforgatásával beállíthatja az erősséget és rögzítheti a rohamot. A roham megjelenik az iPhone-on.
- Tegye a számlapra az „Új roham” komplikációt, vagy használja a Vezérlőközpont vezérlőjét.

PARANCSOK
- Az „Új roham” művelet a Parancsok appban vagy a Spotlightban rögzít egy rohamot a szokásos erősséggel.

JELENTÉSEK ORVOSÁNAK
- Exportáljon PDF-jelentést vagy Excel-fájlt a választott időszakra.
- Válassza ki, mit tartalmazzon a jelentés: gyógyszerek, aura és figyelmeztető tünetek, fájdalomdiagramok.

ADATVÉDELEM AZ ALAPOKTÓL
- Nincs fiók, nincs szerver, nincs analitika, nincs hirdetés.
- Az adatai az iPhone-ján és az Apple Watchán maradnak.
- A Beállítások › Minden adat törlése bármikor mindent töröl.

A MigraTrack nem orvostechnikai eszköz, és nem ad orvosi tanácsot. Mielőtt változtat a kezelésén, beszéljen orvosával.
```

### Keywords

82 bytes in UTF-8 (73 characters) of 100 bytes. The name and the subtitle are searchable already, so the list does not repeat "migrén", "napló" or "jelentések".

```text
fejfájás,roham,aura,fájdalom,kiváltó,alvás,gyógyszer,orvos,naptár,pdf,óra
```

### What's New

For the version that adds this localization:

```text
A MigraTrack mostantól magyarul is elérhető: app, PDF- és Excel-jelentések, Apple Watch.
```

## Siri

Siri does not speak Hungarian ([iOS feature availability](https://www.apple.com/ios/feature-availability/), Siri section, read on October 18, 2026). The description names the App Shortcut "Új roham" in the Shortcuts app and Spotlight instead.
