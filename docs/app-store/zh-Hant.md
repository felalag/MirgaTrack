# App Store metadata: Chinese (Traditional)

App Store Connect localization "Chinese (Traditional)". The English source and the field limits are in [APP_STORE.md](../APP_STORE.md). The terms follow [LOCALIZATION_GLOSSARY.md](../LOCALIZATION_GLOSSARY.md): "發作" for attack and episode, and "誘發因子" for trigger, as the Traditional Chinese ICHD-3 of the Taiwan Headache Society writes them; the words are those of Taiwan (plan, section 6.1). The counts were made with Python `len()` and `len(text.encode("utf-8"))` on October 20, 2026.

## App information

| Field | Value | Count | Limit |
|---|---|---|---|
| Name | MigraTrack | 10 characters | 30 characters |
| Subtitle | 偏頭痛日記與就醫報告 | 10 characters | 30 characters |
| Privacy Policy URL | https://felalag.github.io/MirgaTrack/privacy/ (the English page, plan section 4.2) | | |

## Version information

### Promotional text

48 characters of 170.

```text
在 iPhone 或 Apple Watch 上記錄偏頭痛發作，了解您的模式，並與醫師分享報告。
```

### Description

645 characters of 4000.

```text
MigraTrack 是您的偏頭痛日記。幾秒鐘就能記錄一次發作，長期追蹤您的模式，並在下次看診時帶著一份清楚的報告。

記錄發作
- 開始時間、1 到 10 的強度、預兆、症狀、誘發因子和藥物。
- 您通常的強度和常用的急性期藥物會預先填入每次新的記錄，大多數時候只要點一下。
- 可以從行事曆補記之前某一天的發作，也可以稍後編輯。

您的模式
- 摘要顯示無痛日和上次發作。
- 分析依週、月、6 個月和年顯示發作，並附有月曆。

健康
- 經您許可，MigraTrack 會從「健康」讀取睡眠資料，並在摘要中顯示昨晚的睡眠。
- MigraTrack 不會寫入任何資料到「健康」。

APPLE WATCH
- 在手腕上查看無痛日。
- 轉動數位錶冠設定強度並記錄發作，紀錄會顯示在您的 iPhone 上。
- 在錶面上加入「記錄偏頭痛」複雜功能，或使用控制中心裡的控制項目。

SIRI 和捷徑
- 說「在 MigraTrack 中記錄偏頭痛」，即可用您通常的強度記錄一次發作。

給醫師的報告
- 依您選擇的期間匯出 PDF 報告或 Excel 檔案。
- 選擇報告內容：藥物、預兆和警示症狀、疼痛強度圖表。

隱私優先
- 沒有帳號、沒有伺服器、沒有分析、沒有廣告。
- 您的資料只保留在您的 iPhone 和 Apple Watch 上。
- 在「設定」›「刪除所有資料」中可隨時刪除全部資料。

MigraTrack 不是醫療器材，也不提供醫療建議。變更治療前，請先諮詢醫師。
```

### Keywords

65 bytes in UTF-8 (29 characters) of 100 bytes. A Chinese, Japanese or Korean character takes 3 bytes in UTF-8, so the list is short. The name and the subtitle are searchable already, so the list does not repeat the words of the subtitle.

```text
頭痛,預兆,誘發因子,發作,藥物,醫師,睡眠,日誌,pdf
```

### What's New

For the version that adds this localization:

```text
MigraTrack 現已支援繁體中文：App、PDF 和 Excel 報告、Apple Watch 和 Siri。
```

## Siri

Siri speaks this language ([iOS feature availability](https://www.apple.com/ios/feature-availability/), Siri section, read on October 18, 2026). The description quotes the Siri phrase "在 MigraTrack 中記錄偏頭痛" from `iosApp/iosApp/AppShortcuts.xcstrings`. Check the phrase with Product › App Shortcuts Preview before the release.
