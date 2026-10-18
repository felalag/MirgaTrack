# App Store metadata: Russian

App Store Connect localization "Russian". The English source and the field limits are in [APP_STORE.md](../APP_STORE.md). The terms follow [LOCALIZATION_GLOSSARY.md](../LOCALIZATION_GLOSSARY.md): "приступ" for attack and episode. The counts were made with Python `len()` and `len(text.encode("utf-8"))` on October 18, 2026.

Belarusian has no App Store metadata: the App Store in Belarus shows English (U.K.) only ([LOCALIZATION_PLAN.md](../LOCALIZATION_PLAN.md), section 1.1).

## App information

| Field | Value | Count | Limit |
|---|---|---|---|
| Name | MigraTrack | 10 characters | 30 characters |
| Subtitle | Дневник мигрени и отчёты | 24 characters | 30 characters |
| Privacy Policy URL | https://felalag.github.io/MirgaTrack/privacy/ (the English page, plan section 4.2) | | |

## Version information

### Promotional text

106 characters of 170.

```text
Записывайте приступы мигрени на iPhone или Apple Watch, находите закономерности и показывайте отчёт врачу.
```

### Description

1732 characters of 4000.

```text
MigraTrack — дневник ваших приступов мигрени. Записывайте приступ за секунды, следите за закономерностями и берите на приём понятный отчёт.

ЗАПИСЬ ПРИСТУПА
- Время начала, интенсивность боли от 1 до 10, аура, симптомы, триггеры и лекарства.
- Ваша обычная интенсивность и привычные лекарства для купирования приступа заполняются в каждой новой записи, поэтому часто хватает одного касания.
- Записывайте приступ за прошедший день из календаря или изменяйте запись позже.

ЗАКОНОМЕРНОСТИ
- Сводка показывает дни без боли и последние приступы.
- Аналитика показывает приступы за неделю, месяц, полгода и год, а также календарь месяца.

ПРИЛОЖЕНИЕ «ЗДОРОВЬЕ»
- С вашего разрешения MigraTrack считывает данные о сне из приложения «Здоровье» и показывает в сводке сон прошлой ночи.
- MigraTrack ничего не записывает в приложение «Здоровье».

APPLE WATCH
- Дни без боли у вас на запястье.
- Поверните колёсико Digital Crown, чтобы задать интенсивность, и запишите приступ. Запись появится на iPhone.
- Добавьте усложнение «Новый приступ» на циферблат или используйте элемент в Пункте управления или в Смарт-стопке.

SIRI И БЫСТРЫЕ КОМАНДЫ
- Скажите «Записать мигрень в MigraTrack», чтобы записать приступ с обычной интенсивностью.

ОТЧЁТЫ ДЛЯ ВРАЧА
- Экспортируйте отчёт в PDF или файл Excel за выбранный период.
- Выберите, что включить в отчёт: лекарства, ауру и предупреждающие симптомы, графики боли.

КОНФИДЕНЦИАЛЬНОСТЬ ПО УМОЛЧАНИЮ
- Без учётной записи, сервера, аналитики и рекламы.
- Ваши данные остаются на вашем iPhone и Apple Watch.
- «Настройки» › «Удалить все данные» удаляет всё в любой момент.

MigraTrack не является медицинским изделием и не даёт медицинских рекомендаций. Поговорите с врачом, прежде чем менять лечение.
```

### Keywords

93 bytes in UTF-8 (52 characters) of 100 bytes. A Cyrillic letter takes 2 bytes. The name and the subtitle are searchable already, so the list does not repeat "мигрень", "дневник" or "отчёт".

```text
головная боль,приступ,аура,триггер,сон,врач,pdf,часы
```

### What's New

For the version that adds the Russian localization:

```text
MigraTrack теперь на русском языке: интерфейс, отчёты PDF и Excel, Apple Watch и Siri.
```

## Siri

The description quotes the Russian Siri phrase "Записать мигрень в MigraTrack" from `iosApp/iosApp/AppShortcuts.xcstrings`. Siri speaks Russian ([iOS feature availability](https://www.apple.com/ios/feature-availability/), Siri section, read on October 18, 2026). Check the phrase with Product › App Shortcuts Preview before the release.
