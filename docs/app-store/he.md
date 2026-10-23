# App Store metadata: Hebrew

App Store Connect localization "Hebrew". The English source and the field limits are in [APP_STORE.md](../APP_STORE.md). The terms follow [LOCALIZATION_GLOSSARY.md](../LOCALIZATION_GLOSSARY.md): "התקף" for attack and episode, as Maccabi writes "התקף מיגרנה", and "גורם מעורר" for trigger. The counts were made with Python `len()` and `len(text.encode("utf-8"))` on October 23, 2026.

## App information

| Field | Value | Count | Limit |
|---|---|---|---|
| Name | MigraTrack | 10 characters | 30 characters |
| Subtitle | יומן מיגרנה ודוחות | 18 characters | 30 characters |
| Privacy Policy URL | https://felalag.github.io/MirgaTrack/privacy/ (the English page, plan section 4.2) | | |

## Version information

### Promotional text

79 characters of 170.

```text
תיעוד התקפי מיגרנה ב־iPhone או ב־Apple Watch, זיהוי דפוסים ושיתוף דוח עם הרופא.
```

### Description

1323 characters of 4000.

```text
MigraTrack הוא יומן להתקפי מיגרנה. מתעדים התקף בשניות, רואים דפוסים לאורך זמן ומביאים דוח ברור לפגישה הבאה עם הרופא.

תיעוד התקף
- שעת ההתחלה, עוצמת הכאב מ־1 עד 10, אאורה, תסמינים, גורמים מעוררים ותרופות.
- העוצמה הטיפוסית והתרופות הרגילות להתקף חד ממולאות מראש בכל תיעוד חדש, ולכן לרוב מספיקה הקשה אחת.
- אפשר לתעד התקף ביום קודם מלוח השנה, או לערוך התקף מאוחר יותר.

הדפוסים שלך
- הסיכום מציג את הימים ללא כאב ואת ההתקפים האחרונים.
- התובנות מציגות התקפים לפי שבוע, חודש, שישה חודשים ושנה, עם לוח שנה חודשי.

״בריאות״ של Apple
- בהרשאתך, MigraTrack קורא את השינה מהיישום ״בריאות״ ומציג בסיכום את השינה של הלילה האחרון.
- MigraTrack לא כותב דבר ליישום ״בריאות״.

APPLE WATCH
- הימים ללא כאב על פרק היד.
- סיבוב ה־Digital Crown קובע את העוצמה, ובהקשה ההתקף מתועד. ההתקף מגיע ל־iPhone.
- אפשר להוסיף לפני השעון את הסיבוך ״תיעוד מיגרנה״, או להשתמש בפקד במרכז הבקרה.

SIRI וקיצורים
- אפשר לומר ״תעד מיגרנה ב־MigraTrack״ כדי לתעד התקף בעוצמה הטיפוסית.

דוחות לרופא
- ייצוא דוח PDF או קובץ Excel לתקופה שנבחרה.
- בחירת תוכן הדוח: תרופות, אאורה ותסמינים מקדימים, תרשימי עוצמת כאב.

פרטיות מהיסוד
- בלי חשבון, בלי שרת, בלי כלי ניתוח ובלי פרסומות.
- הנתונים נשארים ב־iPhone וב־Apple Watch.
- ״הגדרות״ › ״מחיקת כל הנתונים״ מוחק הכול בכל עת.

MigraTrack אינו אביזר רפואי ואינו מספק ייעוץ רפואי. לפני שינוי הטיפול יש להתייעץ עם הרופא.
```

### Keywords

93 bytes in UTF-8 (53 characters) of 100 bytes. An Arabic, Hebrew or Urdu letter takes 2 bytes in UTF-8. The name and the subtitle are searchable already, so the list does not repeat "מיגרנה", "יומן" or "דוחות". "טריגר" is the common word that Maccabi uses next to the glossary term "גורם מעורר".

```text
כאב ראש,אאורה,התקף,כאב,טריגר,שינה,תרופה,רופא,מעקב,pdf
```

### What's New

For the version that adds this localization:

```text
MigraTrack זמין עכשיו בעברית: היישום, דוחות PDF ו־Excel, Apple Watch ו־Siri.
```

## Siri

Siri speaks this language ([iOS feature availability](https://www.apple.com/ios/feature-availability/), Siri section, read on October 18, 2026). The description quotes the Siri phrase "תעד מיגרנה ב־MigraTrack" from `iosApp/iosApp/AppShortcuts.xcstrings`. Check the phrase with Product › App Shortcuts Preview before the release.
