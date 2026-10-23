# App Store metadata: Arabic

App Store Connect localization "Arabic". The English source and the field limits are in [APP_STORE.md](../APP_STORE.md). The terms follow [LOCALIZATION_GLOSSARY.md](../LOCALIZATION_GLOSSARY.md): "نوبة" for attack and episode, as Mayo Clinic in Arabic writes "نوبات الشقيقة", and "محفز" for trigger. The counts were made with Python `len()` and `len(text.encode("utf-8"))` on October 23, 2026.

## App information

| Field | Value | Count | Limit |
|---|---|---|---|
| Name | MigraTrack | 10 characters | 30 characters |
| Subtitle | مذكرات الصداع النصفي وتقاريره | 29 characters | 30 characters |
| Privacy Policy URL | https://felalag.github.io/MirgaTrack/privacy/ (the English page, plan section 4.2) | | |

## Version information

### Promotional text

90 characters of 170.

```text
سجّل نوبات الصداع النصفي على iPhone أو Apple Watch، واكتشف أنماطك، وشارك تقريرًا مع طبيبك.
```

### Description

1320 characters of 4000.

```text
MigraTrack مذكرات يومية لنوبات الصداع النصفي. سجّل النوبة في ثوانٍ، وتابع أنماطك مع الوقت، واحمل تقريرًا واضحًا إلى موعدك القادم.

تسجيل النوبة
- سجّل وقت البداية، وشدة الألم من 1 إلى 10، والأورة، والأعراض، والمحفزات، والأدوية.
- تُملأ شدتك المعتادة وأدوية النوبة الحادة المعتادة مسبقًا في كل تسجيل جديد، فتكفي لمسة واحدة غالبًا.
- سجّل نوبة في يوم سابق من التقويم، أو عدّل النوبة لاحقًا.

اكتشف أنماطك
- يعرض الملخص أيامك دون ألم وآخر نوباتك.
- تعرض التحليلات النوبات لكل أسبوع وشهر وستة أشهر وسنة، مع تقويم شهري.

تطبيق صحتي
- بإذنك، يقرأ MigraTrack نومك من تطبيق صحتي ويعرض نوم الليلة الماضية في الملخص.
- لا يكتب MigraTrack أي شيء في تطبيق صحتي.

APPLE WATCH
- شاهد أيامك دون ألم على معصمك.
- أدر Digital Crown لتحديد الشدة وسجّل النوبة. تنتقل النوبة إلى iPhone.
- أضف الإضافة «تسجيل نوبة» إلى واجهة الساعة، أو استخدم عنصر التحكم في مركز التحكم.

SIRI والاختصارات
- قل «سجّل صداعًا نصفيًا في MigraTrack» لتسجيل نوبة بشدتك المعتادة.

تقارير لطبيبك
- صدّر تقرير PDF أو ملف Excel للفترة التي تختارها.
- اختر محتوى التقرير: الأدوية، والأورة والأعراض التحذيرية، ورسوم شدة الألم.

الخصوصية أولًا
- لا حساب، ولا خادم، ولا أدوات تحليل، ولا إعلانات.
- تبقى بياناتك على iPhone وApple Watch.
- الإعدادات › حذف كل البيانات يحذف كل شيء في أي وقت.

MigraTrack ليس جهازًا طبيًا ولا يقدّم نصيحة طبية. استشر طبيبك قبل أن تغيّر علاجك.
```

### Keywords

93 bytes in UTF-8 (53 characters) of 100 bytes. An Arabic, Hebrew or Urdu letter takes 2 bytes in UTF-8. The name and the subtitle are searchable already, so the list does not repeat "الصداع النصفي", "مذكرات" or "تقارير". "شقيقة" is the second word for migraine that Mayo Clinic in Arabic uses.

```text
صداع,شقيقة,أورة,نوبة,ألم,محفز,نوم,دواء,طبيب,تقويم,pdf
```

### What's New

For the version that adds this localization:

```text
أصبح MigraTrack متاحًا بالعربية: التطبيق، وتقارير PDF وExcel، وApple Watch، وSiri.
```

## Siri

Siri speaks this language ([iOS feature availability](https://www.apple.com/ios/feature-availability/), Siri section, read on October 18, 2026). The description quotes the Siri phrase "سجّل صداعًا نصفيًا في MigraTrack" from `iosApp/iosApp/AppShortcuts.xcstrings`. Check the phrase with Product › App Shortcuts Preview before the release.
