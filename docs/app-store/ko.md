# App Store metadata: Korean

App Store Connect localization "Korean". The English source and the field limits are in [APP_STORE.md](../APP_STORE.md). The terms follow [LOCALIZATION_GLOSSARY.md](../LOCALIZATION_GLOSSARY.md): "발작" for attack and episode, as the Korean ICHD-3 of the Korean Headache Society writes it ("편두통 발작"), and "유발 요인" for trigger. The counts were made with Python `len()` and `len(text.encode("utf-8"))` on October 20, 2026.

## App information

| Field | Value | Count | Limit |
|---|---|---|---|
| Name | MigraTrack | 10 characters | 30 characters |
| Subtitle | 편두통 일지와 진료용 보고서 | 15 characters | 30 characters |
| Privacy Policy URL | https://felalag.github.io/MirgaTrack/privacy/ (the English page, plan section 4.2) | | |

## Version information

### Promotional text

66 characters of 170.

```text
iPhone이나 Apple Watch에서 편두통 발작을 기록하고, 나의 패턴을 확인하고, 보고서를 의사와 공유하십시오.
```

### Description

902 characters of 4000.

```text
MigraTrack은 편두통 일지입니다. 몇 초 만에 발작을 기록하고, 시간에 따른 패턴을 추적하고, 다음 진료 때 알아보기 쉬운 보고서를 가져갈 수 있습니다.

발작 기록
- 시작 시각, 1~10의 강도, 조짐, 증상, 유발 요인, 약.
- 일반적인 강도와 자주 쓰는 급성기 치료제가 새 기록마다 미리 입력되므로 대부분 한 번만 탭하면 됩니다.
- 캘린더에서 지난 날의 발작을 추가하거나 나중에 편집할 수 있습니다.

나의 패턴
- 요약에 통증 없는 날과 마지막 발작이 표시됩니다.
- 분석에서 주, 월, 6개월, 년 단위의 발작을 월간 캘린더와 함께 볼 수 있습니다.

건강 앱
- 허용하면 MigraTrack이 건강 앱에서 수면을 읽어 어젯밤 수면을 요약에 표시합니다.
- MigraTrack은 건강 앱에 아무것도 기록하지 않습니다.

APPLE WATCH
- 손목에서 통증 없는 날을 확인할 수 있습니다.
- Digital Crown을 돌려 강도를 설정하고 발작을 기록하면 iPhone에 표시됩니다.
- 시계 페이스에 "편두통 기록" 컴플리케이션을 추가하거나 제어 센터의 제어 항목을 사용할 수 있습니다.

SIRI 및 단축어
- "MigraTrack에서 편두통 기록"이라고 말하면 일반적인 강도로 발작을 기록합니다.

의사를 위한 보고서
- 선택한 기간의 PDF 보고서나 Excel 파일을 내보낼 수 있습니다.
- 보고서 내용 선택: 약, 조짐 및 경고 증상, 통증 강도 차트.

개인정보 보호 우선
- 계정, 서버, 분석, 광고가 없습니다.
- 데이터는 사용자의 iPhone과 Apple Watch에만 저장됩니다.
- 설정 › 모든 데이터 삭제에서 언제든지 모두 삭제할 수 있습니다.

MigraTrack은 의료기기가 아니며 의학적 조언을 제공하지 않습니다. 치료를 변경하기 전에 의사와 상담하십시오.
```

### Keywords

62 bytes in UTF-8 (28 characters) of 100 bytes. A Chinese, Japanese or Korean character takes 3 bytes in UTF-8, so the list is short. The name and the subtitle are searchable already, so the list does not repeat the words of the subtitle.

```text
두통,조짐,유발요인,발작,약,의사,수면,일기,pdf
```

### What's New

For the version that adds this localization:

```text
이제 MigraTrack을 한국어로 사용할 수 있습니다. 앱, PDF 및 Excel 보고서, Apple Watch, Siri를 지원합니다.
```

## Siri

Siri speaks this language ([iOS feature availability](https://www.apple.com/ios/feature-availability/), Siri section, read on October 18, 2026). The description quotes the Siri phrase "MigraTrack에서 편두통 기록" from `iosApp/iosApp/AppShortcuts.xcstrings`. Check the phrase with Product › App Shortcuts Preview before the release.
