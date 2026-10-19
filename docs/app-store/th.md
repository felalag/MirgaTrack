# App Store metadata: Thai

App Store Connect localization "Thai". The English source and the field limits are in [APP_STORE.md](../APP_STORE.md). The terms follow [LOCALIZATION_GLOSSARY.md](../LOCALIZATION_GLOSSARY.md): "อาการปวด" for attack and episode, counted with "ครั้ง", as the Thai migraine guideline of 2022 writes it. The counts were made with Python `len()` and `len(text.encode("utf-8"))` on October 19, 2026.

## App information

| Field | Value | Count | Limit |
|---|---|---|---|
| Name | MigraTrack | 10 characters | 30 characters |
| Subtitle | บันทึกไมเกรนและรายงาน | 21 characters | 30 characters |
| Privacy Policy URL | https://felalag.github.io/MirgaTrack/privacy/ (the English page, plan section 4.2) | | |

## Version information

### Promotional text

83 characters of 170.

```text
บันทึกอาการปวดไมเกรนบน iPhone หรือ Apple Watch ดูรูปแบบของคุณ และแชร์รายงานให้แพทย์
```

### Description

1443 characters of 4000.

```text
MigraTrack เป็นสมุดบันทึกอาการปวดไมเกรนของคุณ บันทึกอาการปวดได้ในไม่กี่วินาที ติดตามรูปแบบของคุณตามเวลา และนำรายงานที่อ่านง่ายไปพบแพทย์ครั้งถัดไป

การบันทึกอาการปวด
- เวลาเริ่มปวด ความรุนแรง 1 ถึง 10 ออร่า อาการ สิ่งกระตุ้น และยา
- ความรุนแรงโดยทั่วไปและยาบรรเทาอาการปวดที่ใช้ประจำจะใส่ไว้ล่วงหน้าในทุกครั้งที่บันทึกใหม่ ส่วนใหญ่จึงแตะเพียงครั้งเดียว
- เพิ่มอาการปวดของวันที่ผ่านมาจากปฏิทิน หรือแก้ไขภายหลัง

รูปแบบของคุณ
- หน้าสรุปแสดงวันที่ไม่ปวดและอาการปวดครั้งล่าสุด
- หน้าวิเคราะห์แสดงอาการปวดรายสัปดาห์ รายเดือน หกเดือน และรายปี พร้อมปฏิทินรายเดือน

แอปสุขภาพ
- เมื่อคุณอนุญาต MigraTrack จะอ่านข้อมูลการนอนจากแอปสุขภาพ และแสดงการนอนเมื่อคืนในหน้าสรุป
- MigraTrack ไม่เขียนข้อมูลใดๆ ลงในแอปสุขภาพ

APPLE WATCH
- ดูวันที่ไม่ปวดได้บนข้อมือ
- หมุน Digital Crown เพื่อตั้งความรุนแรงและบันทึกอาการปวด ข้อมูลจะแสดงใน iPhone ของคุณ
- เพิ่มคอมพลิเคชัน "บันทึกอาการ" บนหน้าปัด หรือใช้ปุ่มควบคุมในศูนย์ควบคุม

SIRI และคำสั่งลัด
- พูดว่า "บันทึกไมเกรนใน MigraTrack" เพื่อบันทึกอาการปวดด้วยความรุนแรงโดยทั่วไปของคุณ

รายงานสำหรับแพทย์
- ส่งออกรายงาน PDF หรือไฟล์ Excel ตามช่วงเวลาที่คุณเลือก
- เลือกเนื้อหาในรายงาน: ยา ออร่าและอาการเตือน กราฟความรุนแรงของอาการปวด

ความเป็นส่วนตัวตั้งแต่แรก
- ไม่มีบัญชี ไม่มีเซิร์ฟเวอร์ ไม่มีระบบวิเคราะห์ ไม่มีโฆษณา
- ข้อมูลของคุณอยู่ใน iPhone และ Apple Watch ของคุณ
- การตั้งค่า › ลบข้อมูลทั้งหมด ลบทุกอย่างได้ทุกเมื่อ

MigraTrack ไม่ใช่เครื่องมือแพทย์และไม่ให้คำแนะนำทางการแพทย์ โปรดปรึกษาแพทย์ก่อนเปลี่ยนการรักษา
```

### Keywords

96 bytes in UTF-8 (38 characters) of 100 bytes. A Thai character takes 3 bytes in UTF-8, so the list is short. The name and the subtitle are searchable already, so the list does not repeat "ไมเกรน", "บันทึก" or "รายงาน".

```text
ปวดหัว,ปวดศีรษะ,ออร่า,ยา,แพทย์,นอน,pdf
```

### What's New

For the version that adds this localization:

```text
MigraTrack มีภาษาไทยแล้ว ทั้งแอป รายงาน PDF และ Excel, Apple Watch และ Siri
```

## Siri

Siri speaks this language ([iOS feature availability](https://www.apple.com/ios/feature-availability/), Siri section, read on October 18, 2026). The description quotes the Siri phrase "บันทึกไมเกรนใน MigraTrack" from `iosApp/iosApp/AppShortcuts.xcstrings`. Check the phrase with Product › App Shortcuts Preview before the release.
