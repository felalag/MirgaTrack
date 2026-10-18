# App Store metadata: Polish

App Store Connect localization "Polish". The English source and the field limits are in [APP_STORE.md](../APP_STORE.md). The terms follow [LOCALIZATION_GLOSSARY.md](../LOCALIZATION_GLOSSARY.md): "napad" for attack and episode. The counts were made with Python `len()` and `len(text.encode("utf-8"))` on October 18, 2026.

## App information

| Field | Value | Count | Limit |
|---|---|---|---|
| Name | MigraTrack | 10 characters | 30 characters |
| Subtitle | Dziennik migreny i raporty | 26 characters | 30 characters |
| Privacy Policy URL | https://felalag.github.io/MirgaTrack/privacy/ (the English page, plan section 4.2) | | |

## Version information

### Promotional text

88 characters of 170.

```text
Zapis napadów migreny na iPhonie lub Apple Watch, przegląd wzorców i raport dla lekarza.
```

### Description

1540 characters of 4000.

```text
MigraTrack to dziennik napadów migreny. Zapis napadu zajmuje kilka sekund, wzorce widać w czasie, a na wizytę można zabrać przejrzysty raport.

ZAPIS NAPADU
- Początek, natężenie bólu od 1 do 10, aura, objawy, czynniki wyzwalające i leki.
- Typowe natężenie i zwykłe leki doraźne są wpisywane do każdego nowego napadu, więc często wystarczy jedno dotknięcie.
- Napad z wcześniejszego dnia można dodać z kalendarza, a każdy napad można później edytować.

WZORCE
- Przegląd pokazuje dni bez bólu i ostatnie napady.
- Analiza pokazuje napady w tygodniu, miesiącu, pół roku i roku oraz kalendarz miesiąca.

APLIKACJA ZDROWIE
- Za zgodą użytkownika MigraTrack odczytuje sen z aplikacji Zdrowie i pokazuje w Przeglądzie sen z ostatniej nocy.
- MigraTrack nic nie zapisuje w aplikacji Zdrowie.

APPLE WATCH
- Dni bez bólu na nadgarstku.
- Natężenie ustawia się koronką Digital Crown, a napad trafia na iPhone’a.
- Komplikacja „Nowy napad” na tarczy zegarka i przycisk w Centrum sterowania.

SKRÓTY
- Czynność „Nowy napad” w aplikacji Skróty i w Spotlight zapisuje napad z typowym natężeniem.

RAPORTY DLA LEKARZA
- Raport PDF lub plik Excel za wybrany okres.
- Wybór zawartości raportu: leki, aura i objawy ostrzegawcze, wykresy bólu.

PRYWATNOŚĆ OD POCZĄTKU
- Bez konta, bez serwera, bez analityki, bez reklam.
- Dane pozostają na iPhonie i Apple Watch.
- Ustawienia › Usuń wszystkie dane usuwa wszystko w dowolnej chwili.

MigraTrack nie jest wyrobem medycznym i nie udziela porad medycznych. Przed zmianą leczenia należy porozmawiać z lekarzem.
```

### Keywords

80 bytes in UTF-8 (77 characters) of 100 bytes. The name and the subtitle are searchable already, so the list does not repeat "migreny", "dziennik" or "raporty".

```text
ból głowy,napad,atak,aura,ból,wyzwalacz,sen,leki,lekarz,kalendarz,pdf,zegarek
```

### What's New

For the version that adds this localization:

```text
MigraTrack jest teraz po polsku: aplikacja, raporty PDF i Excel oraz Apple Watch.
```

## Siri

Siri does not speak Polish ([iOS feature availability](https://www.apple.com/ios/feature-availability/), Siri section, read on October 18, 2026). The description names the App Shortcut "Nowy napad" in the Shortcuts app and Spotlight instead.
