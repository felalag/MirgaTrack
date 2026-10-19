# App Store metadata: Japanese

App Store Connect localization "Japanese". The English source and the field limits are in [APP_STORE.md](../APP_STORE.md). The terms follow [LOCALIZATION_GLOSSARY.md](../LOCALIZATION_GLOSSARY.md): "発作" for attack and episode, as the Japanese ICHD-3 writes it ("片頭痛発作"), and "誘因" for trigger. The counts were made with Python `len()` and `len(text.encode("utf-8"))` on October 20, 2026.

## App information

| Field | Value | Count | Limit |
|---|---|---|---|
| Name | MigraTrack | 10 characters | 30 characters |
| Subtitle | 片頭痛の記録と受診用レポート | 14 characters | 30 characters |
| Privacy Policy URL | https://felalag.github.io/MirgaTrack/privacy/ (the English page, plan section 4.2) | | |

## Version information

### Promotional text

54 characters of 170.

```text
iPhoneやApple Watchで片頭痛発作を記録。自分のパターンを知り、レポートを医師と共有できます。
```

### Description

819 characters of 4000.

```text
MigraTrackは片頭痛の日記です。発作を数秒で記録し、パターンを長期的に把握して、次の受診にわかりやすいレポートを持って行けます。

発作の記録
- 開始時刻、1～10の痛みの強さ、前兆、症状、誘因、薬。
- 通常の痛みの強さとよく使う急性期治療薬が新しい記録に自動で入力されるので、ほとんどの場合タップ1回で済みます。
- カレンダーから過去の日の発作を追加したり、あとで編集したりできます。

あなたのパターン
- 概要には痛みのない日と前回の発作が表示されます。
- 分析では、週、月、6か月、年ごとの発作を月間カレンダーとともに表示します。

ヘルスケア
- 許可すると、MigraTrackはヘルスケアから睡眠を読み取り、昨夜の睡眠を概要に表示します。
- MigraTrackはヘルスケアに何も書き込みません。

APPLE WATCH
- 痛みのない日を手首で確認できます。
- Digital Crownを回して強さを設定し、発作を記録できます。記録はiPhoneに表示されます。
- 文字盤に「片頭痛を記録」のコンプリケーションを追加するか、コントロールセンターのコントロールを使えます。

SIRIとショートカット
- 「MigraTrackで片頭痛を記録」と話しかけると、通常の強さで発作を記録します。

医師のためのレポート
- 選択した期間のPDFレポートまたはExcelファイルを書き出せます。
- 内容を選べます：薬、前兆と予兆症状、痛みの強さのグラフ。

プライバシーを最優先に
- アカウントなし、サーバーなし、分析なし、広告なし。
- データはあなたのiPhoneとApple Watchの中だけに保存されます。
- 「設定」›「すべてのデータを削除」で、いつでもすべてを削除できます。

MigraTrackは医療機器ではなく、医学的な助言も行いません。治療を変更する前に医師にご相談ください。
```

### Keywords

56 bytes in UTF-8 (26 characters) of 100 bytes. A Chinese, Japanese or Korean character takes 3 bytes in UTF-8, so the list is short. The name and the subtitle are searchable already, so the list does not repeat the words of the subtitle.

```text
頭痛,前兆,誘因,発作,薬,医師,睡眠,日記,pdf
```

### What's New

For the version that adds this localization:

```text
MigraTrackが日本語に対応しました：App、PDFとExcelのレポート、Apple Watch、Siri。
```

## Siri

Siri speaks this language ([iOS feature availability](https://www.apple.com/ios/feature-availability/), Siri section, read on October 18, 2026). The description quotes the Siri phrase "MigraTrackで片頭痛を記録" from `iosApp/iosApp/AppShortcuts.xcstrings`. Check the phrase with Product › App Shortcuts Preview before the release.
