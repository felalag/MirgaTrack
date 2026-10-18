# App Store metadata: Turkish

App Store Connect localization "Turkish". The English source and the field limits are in [APP_STORE.md](../APP_STORE.md). The terms follow [LOCALIZATION_GLOSSARY.md](../LOCALIZATION_GLOSSARY.md): "atak" for attack and episode, as in the Turkish ICHD-3. The counts were made with Python `len()` and `len(text.encode("utf-8"))` on October 18, 2026.

## App information

| Field | Value | Count | Limit |
|---|---|---|---|
| Name | MigraTrack | 10 characters | 30 characters |
| Subtitle | Migren günlüğü ve raporlar | 26 characters | 30 characters |
| Privacy Policy URL | https://felalag.github.io/MirgaTrack/privacy/ (the English page, plan section 4.2) | | |

## Version information

### Promotional text

111 characters of 170.

```text
Migren ataklarını iPhone’da veya Apple Watch’ta kaydedin, örüntülerinizi görün ve raporu doktorunuzla paylaşın.
```

### Description

1641 characters of 4000.

```text
MigraTrack, migren ataklarınız için bir günlüktür. Bir atağı saniyeler içinde kaydedin, örüntülerinizi zaman içinde izleyin ve bir sonraki randevunuza açık bir rapor götürün.

ATAK KAYDI
- Başlangıç saati, 1’den 10’a ağrı şiddeti, aura, belirtiler, tetikleyiciler ve ilaçlar.
- Olağan şiddetiniz ve her zamanki akut ilaçlarınız her yeni atağa önceden eklenir; çoğu zaman tek dokunuş yeterlidir.
- Takvimden geçmiş bir güne atak ekleyin veya atağı daha sonra düzenleyin.

ÖRÜNTÜLERİNİZ
- Özet, ağrısız günlerinizi ve son ataklarınızı gösterir.
- Analiz, atakları haftalık, aylık, altı aylık ve yıllık olarak bir ay takvimiyle birlikte gösterir.

SAĞLIK UYGULAMASI
- İzninizle MigraTrack uykunuzu Sağlık uygulamasından okur ve dün geceki uykuyu Özet’te gösterir.
- MigraTrack, Sağlık uygulamasına hiçbir şey yazmaz.

APPLE WATCH
- Ağrısız günleriniz bileğinizde.
- Digital Crown’u çevirerek şiddeti ayarlayın ve atağı kaydedin. Atak iPhone’unuzda görünür.
- “Yeni atak” komplikasyonunu saat kadranına ekleyin veya Denetim Merkezi’ndeki denetimi kullanın.

SİRİ VE KESTİRMELER
- Olağan şiddetinizle bir atak kaydetmek için “MigraTrack ile migren kaydet” deyin.

DOKTORUNUZ İÇİN RAPORLAR
- Seçtiğiniz dönem için PDF raporu veya Excel dosyası dışa aktarın.
- Raporun neleri içereceğini seçin: ilaçlar, aura ve uyarıcı belirtiler, ağrı grafikleri.

BAŞTAN GİZLİLİK
- Hesap yok, sunucu yok, analiz yok, reklam yok.
- Verileriniz iPhone’unuzda ve Apple Watch’unuzda kalır.
- Ayarlar › Tüm verileri sil her şeyi istediğiniz zaman siler.

MigraTrack bir tıbbi cihaz değildir ve tıbbi tavsiye vermez. Tedavinizi değiştirmeden önce doktorunuzla konuşun.
```

### Keywords

91 bytes in UTF-8 (80 characters) of 100 bytes. The name and the subtitle are searchable already, so the list does not repeat "migren", "günlüğü" or "raporlar".

```text
baş ağrısı,başağrısı,atak,aura,ağrı,tetikleyici,uyku,ilaç,doktor,takvim,pdf,saat
```

### What's New

For the version that adds this localization:

```text
MigraTrack artık Türkçe: uygulama, PDF ve Excel raporları, Apple Watch ve Siri.
```

## Siri

Siri speaks this language ([iOS feature availability](https://www.apple.com/ios/feature-availability/), Siri section, read on October 18, 2026). The description quotes the Siri phrase "MigraTrack ile migren kaydet" from `iosApp/iosApp/AppShortcuts.xcstrings`. Check the phrase with Product › App Shortcuts Preview before the release.
