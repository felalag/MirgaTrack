# App Store metadata: Spanish (Spain)

App Store Connect localization "Spanish (Spain)". The English source and the field limits are in [APP_STORE.md](../APP_STORE.md). The terms follow [LOCALIZATION_GLOSSARY.md](../LOCALIZATION_GLOSSARY.md): "crisis" for attack and episode. The counts were made with Python `len()` and `len(text.encode("utf-8"))` on October 18, 2026.

The Spanish text with the words of Spain: "Añada", "Ajustes", "gráficos".

## App information

| Field | Value | Count | Limit |
|---|---|---|---|
| Name | MigraTrack | 10 characters | 30 characters |
| Subtitle | Diario de migraña e informes | 28 characters | 30 characters |
| Privacy Policy URL | https://felalag.github.io/MirgaTrack/privacy/ (the English page, plan section 4.2) | | |

## Version information

### Promotional text

115 characters of 170.

```text
Registre sus crisis de migraña en el iPhone o el Apple Watch, vea sus patrones y comparta un informe con su médico.
```

### Description

1687 characters of 4000.

```text
MigraTrack es un diario de sus crisis de migraña. Registre una crisis en segundos, vea sus patrones y lleve un informe claro a su próxima consulta.

REGISTRAR UNA CRISIS
- Hora de inicio, intensidad del dolor de 1 a 10, aura, síntomas, desencadenantes y medicamentos.
- Su intensidad habitual y sus medicamentos habituales para la crisis ya aparecen en cada crisis nueva, así que a menudo basta un toque.
- Registre una crisis de un día anterior desde el calendario o edítela después.

VER SUS PATRONES
- El resumen muestra sus días sin dolor y sus últimas crisis.
- El análisis muestra las crisis por semana, mes, seis meses y año, con un calendario mensual.

APP SALUD
- Con su permiso, MigraTrack lee su sueño de la app Salud y muestra el sueño de anoche en el resumen.
- MigraTrack no escribe nada en la app Salud.

APPLE WATCH
- Sus días sin dolor en la muñeca.
- Gire la Digital Crown para ajustar la intensidad y registre la crisis. Aparece en su iPhone.
- Añada la complicación «Nueva crisis» a la carátula o use el control del Centro de control.

SIRI Y ATAJOS
- Diga «Registrar una migraña en MigraTrack» para registrar una crisis con su intensidad habitual.

INFORMES PARA SU MÉDICO
- Exporte un informe PDF o un archivo de Excel del periodo que elija.
- Elija qué incluye el informe: medicamentos, aura y síntomas de aviso, y gráficos del dolor.

PRIVACIDAD DESDE EL DISEÑO
- Sin cuenta, sin servidor, sin análisis y sin publicidad.
- Sus datos se quedan en su iPhone y su Apple Watch.
- Ajustes › Eliminar todos los datos borra todo en cualquier momento.

MigraTrack no es un dispositivo médico y no da consejos médicos. Consulte a su médico antes de cambiar su tratamiento.
```

### Keywords

88 bytes in UTF-8 (86 characters) of 100 bytes. The name and the subtitle are searchable already, so the list does not repeat "migraña", "diario" or "informes".

```text
dolor de cabeza,cefalea,crisis,ataque,aura,dolor,desencadenante,sueño,médico,pdf,reloj
```

### What's New

For the version that adds this localization:

```text
MigraTrack ya está en español: app, informes PDF y Excel, Apple Watch y Siri.
```

## Siri

The description quotes the Siri phrase "Registrar una migraña en MigraTrack" from `iosApp/iosApp/AppShortcuts.xcstrings`. Siri speaks this language ([iOS feature availability](https://www.apple.com/ios/feature-availability/), Siri section, read on October 18, 2026). Check the phrase with Product › App Shortcuts Preview before the release.
