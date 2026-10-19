# App Store metadata: Malay

App Store Connect localization "Malay". The English source and the field limits are in [APP_STORE.md](../APP_STORE.md). The terms follow [LOCALIZATION_GLOSSARY.md](../LOCALIZATION_GLOSSARY.md): "serangan" for attack and episode, as Malaysian patient texts and the Cochrane summaries in Malay say it. The counts were made with Python `len()` and `len(text.encode("utf-8"))` on October 19, 2026.

## App information

| Field | Value | Count | Limit |
|---|---|---|---|
| Name | MigraTrack | 10 characters | 30 characters |
| Subtitle | Diari migrain dan laporan | 25 characters | 30 characters |
| Privacy Policy URL | https://felalag.github.io/MirgaTrack/privacy/ (the English page, plan section 4.2) | | |

## Version information

### Promotional text

108 characters of 170.

```text
Catat serangan migrain pada iPhone atau Apple Watch, lihat corak anda dan kongsi laporan dengan doktor anda.
```

### Description

1796 characters of 4000.

```text
MigraTrack ialah diari untuk serangan migrain anda. Catat serangan dalam beberapa saat, ikuti corak anda dari semasa ke semasa dan bawa laporan yang jelas ke temu janji seterusnya.

MENCATAT SERANGAN
- Masa mula, keamatan sakit 1 hingga 10, aura, simptom, pencetus dan ubat.
- Keamatan biasa dan ubat semasa serangan yang biasa anda ambil diisi terlebih dahulu dalam setiap serangan baharu, jadi selalunya cukup satu ketikan.
- Tambah serangan untuk hari yang telah berlalu daripada kalendar, atau edit serangan kemudian.

CORAK ANDA
- Ringkasan menunjukkan hari tanpa sakit dan serangan terakhir anda.
- Analisis menunjukkan serangan mengikut minggu, bulan, enam bulan dan tahun, bersama kalendar bulanan.

APP KESIHATAN
- Dengan kebenaran anda, MigraTrack membaca tidur anda daripada app Kesihatan dan menunjukkan tidur malam tadi dalam Ringkasan.
- MigraTrack tidak menulis apa-apa ke dalam app Kesihatan.

APPLE WATCH
- Hari tanpa sakit anda terus di pergelangan tangan.
- Putar Digital Crown untuk menetapkan keamatan dan catat serangan. Serangan dipaparkan pada iPhone anda.
- Tambah komplikasi "Serangan baru" pada muka jam, atau gunakan kawalan dalam Pusat Kawalan.

SIRI DAN PINTASAN
- Sebut "Catat migrain dalam MigraTrack" untuk mencatat serangan dengan keamatan biasa anda.

LAPORAN UNTUK DOKTOR ANDA
- Eksport laporan PDF atau fail Excel untuk tempoh yang anda pilih.
- Pilih kandungan laporan: ubat, aura dan simptom amaran, carta keamatan sakit.

PRIVASI SEJAK AWAL
- Tiada akaun, tiada pelayan, tiada analitik, tiada iklan.
- Data anda kekal dalam iPhone dan Apple Watch anda.
- Seting › Padam semua data memadam segala-galanya pada bila-bila masa.

MigraTrack bukan peranti perubatan dan tidak memberikan nasihat perubatan. Berbincang dengan doktor anda sebelum mengubah rawatan.
```

### Keywords

91 bytes in UTF-8 (91 characters) of 100 bytes. The name and the subtitle are searchable already, so the list does not repeat "migrain", "diari" or "laporan".

```text
sakit kepala,sakit kepala sebelah,serangan,aura,pencetus,tidur,ubat,doktor,kalendar,pdf,jam
```

### What's New

For the version that adds this localization:

```text
MigraTrack kini dalam bahasa Melayu: app, laporan PDF dan Excel, Apple Watch dan Siri.
```

## Siri

Siri speaks this language ([iOS feature availability](https://www.apple.com/ios/feature-availability/), Siri section, read on October 18, 2026). The description quotes the Siri phrase "Catat migrain dalam MigraTrack" from `iosApp/iosApp/AppShortcuts.xcstrings`. Check the phrase with Product › App Shortcuts Preview before the release.
