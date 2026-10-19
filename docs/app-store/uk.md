# App Store metadata: Ukrainian

App Store Connect localization "Ukrainian". The English source and the field limits are in [APP_STORE.md](../APP_STORE.md). The terms follow [LOCALIZATION_GLOSSARY.md](../LOCALIZATION_GLOSSARY.md): "напад" for attack and episode. The counts were made with Python `len()` and `len(text.encode("utf-8"))` on October 19, 2026.

## App information

| Field | Value | Count | Limit |
|---|---|---|---|
| Name | MigraTrack | 10 characters | 30 characters |
| Subtitle | Щоденник мігрені та звіти | 25 characters | 30 characters |
| Privacy Policy URL | https://felalag.github.io/MirgaTrack/privacy/ (the English page, plan section 4.2) | | |

## Version information

### Promotional text

101 characters of 170.

```text
Записуйте напади мігрені на iPhone або Apple Watch, знаходьте закономірності й показуйте звіт лікарю.
```

### Description

1634 characters of 4000.

```text
MigraTrack — щоденник ваших нападів мігрені. Записуйте напад за кілька секунд, стежте за закономірностями й беріть на прийом зрозумілий звіт.

ЗАПИС НАПАДУ
- Час початку, інтенсивність болю від 1 до 10, аура, симптоми, тригери та ліки.
- Ваша звичайна інтенсивність і звичні ліки для купірування нападу заповнюються в кожному новому записі, тому часто вистачає одного дотику.
- Записуйте напад за минулий день із календаря або змінюйте запис пізніше.

ЗАКОНОМІРНОСТІ
- Зведення показує дні без болю й останні напади.
- Аналітика показує напади за тиждень, місяць, пів року й рік, а також календар місяця.

ПРОГРАМА «ЗДОРОВʼЯ»
- З вашого дозволу MigraTrack зчитує дані про сон із програми «Здоровʼя» і показує у зведенні сон минулої ночі.
- MigraTrack нічого не записує в програму «Здоровʼя».

APPLE WATCH
- Дні без болю на вашому запʼясті.
- Обертайте Digital Crown, щоб задати інтенсивність, і запишіть напад. Запис зʼявиться на iPhone.
- Додайте ускладнення «Новий напад» на циферблат або використовуйте елемент у Пункті керування.

ШВИДКІ КОМАНДИ
- Дія «Новий напад» у програмі «Швидкі команди» та в Spotlight записує напад зі звичайною інтенсивністю.

ЗВІТИ ДЛЯ ЛІКАРЯ
- Експортуйте звіт у PDF або файл Excel за вибраний період.
- Виберіть, що додати до звіту: ліки, ауру й попереджувальні симптоми, графіки болю.

КОНФІДЕНЦІЙНІСТЬ ЗА ЗАМОВЧУВАННЯМ
- Без облікового запису, сервера, аналітики й реклами.
- Ваші дані залишаються на вашому iPhone і Apple Watch.
- «Параметри» › «Видалити всі дані» видаляє все будь-коли.

MigraTrack не є медичним виробом і не дає медичних порад. Поговоріть із лікарем, перш ніж змінювати лікування.
```

### Keywords

97 bytes in UTF-8 (54 characters) of 100 bytes. A Cyrillic letter takes 2 bytes. The name and the subtitle are searchable already, so the list does not repeat "мігрень", "щоденник" or "звіт".

```text
головний біль,напад,аура,тригер,сон,лікар,pdf,годинник
```

### What's New

For the version that adds this localization:

```text
MigraTrack тепер українською: програма, звіти PDF і Excel та Apple Watch.
```

## Siri

Siri does not speak Ukrainian ([iOS feature availability](https://www.apple.com/ios/feature-availability/), Siri section, read on October 18, 2026). The description names the App Shortcut "Новий напад" in the Shortcuts app and Spotlight instead.
