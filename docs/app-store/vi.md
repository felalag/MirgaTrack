# App Store metadata: Vietnamese

App Store Connect localization "Vietnamese". The English source and the field limits are in [APP_STORE.md](../APP_STORE.md). The terms follow [LOCALIZATION_GLOSSARY.md](../LOCALIZATION_GLOSSARY.md): "cơn" and "cơn migraine" for attack and episode, as in the Vietnamese ICHD-3. The counts were made with Python `len()` and `len(text.encode("utf-8"))` on October 19, 2026.

## App information

| Field | Value | Count | Limit |
|---|---|---|---|
| Name | MigraTrack | 10 characters | 30 characters |
| Subtitle | Nhật ký migraine và báo cáo | 27 characters | 30 characters |
| Privacy Policy URL | https://felalag.github.io/MirgaTrack/privacy/ (the English page, plan section 4.2) | | |

## Version information

### Promotional text

102 characters of 170.

```text
Ghi lại cơn migraine trên iPhone hoặc Apple Watch, xem quy luật của bạn và chia sẻ báo cáo với bác sĩ.
```

### Description

1668 characters of 4000.

```text
MigraTrack là nhật ký cho các cơn migraine của bạn. Ghi lại một cơn chỉ trong vài giây, theo dõi quy luật theo thời gian và mang một báo cáo rõ ràng đến buổi khám tiếp theo.

GHI LẠI CƠN
- Thời điểm bắt đầu, cường độ đau từ 1 đến 10, thoáng báo (aura), triệu chứng, yếu tố kích thích và thuốc.
- Cường độ thường gặp và thuốc cắt cơn quen dùng được điền sẵn vào mỗi cơn mới, nên thường chỉ cần một lần chạm.
- Thêm cơn cho một ngày đã qua từ lịch hoặc sửa cơn sau.

QUY LUẬT CỦA BẠN
- Tổng quan hiển thị những ngày không đau và cơn gần nhất.
- Phân tích hiển thị các cơn theo tuần, tháng, sáu tháng và năm, cùng lịch tháng.

ỨNG DỤNG SỨC KHỎE
- Khi bạn cho phép, MigraTrack đọc giấc ngủ từ ứng dụng Sức khỏe và hiển thị giấc ngủ đêm qua trong Tổng quan.
- MigraTrack không ghi gì vào ứng dụng Sức khỏe.

APPLE WATCH
- Những ngày không đau ngay trên cổ tay.
- Xoay Digital Crown để đặt cường độ và ghi lại cơn. Cơn xuất hiện trên iPhone của bạn.
- Thêm tiện ích "Ghi cơn mới" vào mặt đồng hồ hoặc dùng nút điều khiển trong Trung tâm điều khiển.

SIRI VÀ PHÍM TẮT
- Nói "Ghi migraine trong MigraTrack" để ghi lại một cơn với cường độ thường gặp của bạn.

BÁO CÁO CHO BÁC SĨ
- Xuất báo cáo PDF hoặc tệp Excel cho khoảng thời gian bạn chọn.
- Chọn nội dung báo cáo: thuốc, thoáng báo và triệu chứng báo trước, biểu đồ cường độ đau.

QUYỀN RIÊNG TƯ LÀ MẶC ĐỊNH
- Không tài khoản, không máy chủ, không công cụ phân tích, không quảng cáo.
- Dữ liệu của bạn ở trên iPhone và Apple Watch của bạn.
- Cài đặt › Xóa toàn bộ dữ liệu xóa mọi thứ bất cứ lúc nào.

MigraTrack không phải là thiết bị y tế và không đưa ra lời khuyên y tế. Hãy hỏi bác sĩ trước khi thay đổi việc điều trị.
```

### Keywords

92 bytes in UTF-8 (67 characters) of 100 bytes. The name and the subtitle are searchable already, so the list does not repeat "migraine", "nhật ký" or "báo cáo". "đau nửa đầu" is the common word for migraine.

```text
đau đầu,đau nửa đầu,nhức đầu,cơn đau,aura,ngủ,thuốc,bác sĩ,lịch,pdf
```

### What's New

For the version that adds this localization:

```text
MigraTrack giờ đã có tiếng Việt: ứng dụng, báo cáo PDF và Excel, Apple Watch và Siri.
```

## Siri

Siri speaks this language ([iOS feature availability](https://www.apple.com/ios/feature-availability/), Siri section, read on October 18, 2026). The description quotes the Siri phrase "Ghi migraine trong MigraTrack" from `iosApp/iosApp/AppShortcuts.xcstrings`. Check the phrase with Product › App Shortcuts Preview before the release.
