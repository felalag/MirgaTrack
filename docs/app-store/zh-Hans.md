# App Store metadata: Chinese (Simplified)

App Store Connect localization "Chinese (Simplified)". The English source and the field limits are in [APP_STORE.md](../APP_STORE.md). The terms follow [LOCALIZATION_GLOSSARY.md](../LOCALIZATION_GLOSSARY.md): "发作" for attack and episode, as the Chinese ICHD-3 writes it ("偏头痛发作"); "诱因" for trigger, as the Chinese migraine guideline of 2022 writes it. The counts were made with Python `len()` and `len(text.encode("utf-8"))` on October 20, 2026.

## App information

| Field | Value | Count | Limit |
|---|---|---|---|
| Name | MigraTrack | 10 characters | 30 characters |
| Subtitle | 偏头痛日记与就医报告 | 10 characters | 30 characters |
| Privacy Policy URL | https://felalag.github.io/MirgaTrack/privacy/ (the English page, plan section 4.2) | | |

## Version information

### Promotional text

48 characters of 170.

```text
在 iPhone 或 Apple Watch 上记录偏头痛发作，了解您的规律，并与医生共享报告。
```

### Description

646 characters of 4000.

```text
MigraTrack 是您的偏头痛日记。几秒钟即可记录一次发作，随时间追踪您的规律，并在下次就诊时带上一份清晰的报告。

记录发作
- 开始时间、1 到 10 的强度、先兆、症状、诱因和药物。
- 您的通常强度和常用的急性期药物会预先填入每次新的记录，大多数时候只需轻点一下。
- 可以在日历中补记之前某一天的发作，也可以稍后编辑。

您的规律
- 概览显示无痛日和上次发作。
- 分析按周、月、6 个月和年显示发作，并附有月历。

健康
- 经您许可，MigraTrack 会从“健康”读取睡眠数据，并在概览中显示昨晚的睡眠。
- MigraTrack 不会向“健康”写入任何数据。

APPLE WATCH
- 在手腕上查看无痛日。
- 转动数码表冠设置强度并记录发作，记录会显示在您的 iPhone 上。
- 在表盘上添加“记录偏头痛”复杂功能，或使用控制中心中的控制。

SIRI 和快捷指令
- 说“在 MigraTrack 中记录偏头痛”，即可用您的通常强度记录一次发作。

给医生的报告
- 按您选择的时间范围导出 PDF 报告或 Excel 文件。
- 选择报告内容：药物、先兆和预警症状、疼痛强度图表。

隐私优先
- 没有账户，没有服务器，没有分析，没有广告。
- 您的数据只保存在您的 iPhone 和 Apple Watch 上。
- 在“设置”›“删除所有数据”中可随时删除全部数据。

MigraTrack 不是医疗器械，也不提供医疗建议。更改治疗前，请咨询医生。
```

### Keywords

59 bytes in UTF-8 (27 characters) of 100 bytes. A Chinese, Japanese or Korean character takes 3 bytes in UTF-8, so the list is short. The name and the subtitle are searchable already, so the list does not repeat the words of the subtitle.

```text
头痛,先兆,诱因,发作,药物,医生,睡眠,日志,pdf
```

### What's New

For the version that adds this localization:

```text
MigraTrack 现已支持简体中文：App、PDF 和 Excel 报告、Apple Watch 和 Siri。
```

## Siri

Siri speaks this language ([iOS feature availability](https://www.apple.com/ios/feature-availability/), Siri section, read on October 18, 2026). The description quotes the Siri phrase "在 MigraTrack 中记录偏头痛" from `iosApp/iosApp/AppShortcuts.xcstrings`. Check the phrase with Product › App Shortcuts Preview before the release.

## China mainland

The App Store in China mainland needs an ICP filing number for some apps ([App information](https://developer.apple.com/help/app-store-connect/reference/app-information/)). Without a valid number, App Store Connect shows "ICP Filing Number Missing" and the app is not available there. MigraTrack has no server and sends no data, but Apple does not say which apps are exempt. The project owner decided on October 20, 2026: the Simplified Chinese localization ships, and the China mainland availability is decided at the submission. If a filing number comes later, the name and the metadata of this localization must match the filing.
