# App Store metadata: Italian

App Store Connect localization "Italian". The English source and the field limits are in [APP_STORE.md](../APP_STORE.md). The terms follow [LOCALIZATION_GLOSSARY.md](../LOCALIZATION_GLOSSARY.md): "attacco" for attack and episode. The counts were made with Python `len()` and `len(text.encode("utf-8"))` on October 18, 2026.

## App information

| Field | Value | Count | Limit |
|---|---|---|---|
| Name | MigraTrack | 10 characters | 30 characters |
| Subtitle | Diario dell’emicrania e report | 30 characters | 30 characters |
| Privacy Policy URL | https://felalag.github.io/MirgaTrack/privacy/ (the English page, plan section 4.2) | | |

## Version information

### Promotional text

119 characters of 170.

```text
Annoti gli attacchi di emicrania su iPhone o Apple Watch, scopra le ricorrenze e condivida un report con il suo medico.
```

### Description

1783 characters of 4000.

```text
MigraTrack è un diario dei suoi attacchi di emicrania. Annoti un attacco in pochi secondi, scopra le ricorrenze nel tempo e porti un report chiaro alla prossima visita.

ANNOTARE UN ATTACCO
- Ora di inizio, intensità del dolore da 1 a 10, aura, sintomi, fattori scatenanti e farmaci.
- La sua intensità abituale e i farmaci per l’attacco sono già compilati in ogni nuovo attacco, così spesso basta un tocco.
- Annoti un attacco di un giorno passato dal calendario o lo modifichi in seguito.

SCOPRIRE LE RICORRENZE
- Il riepilogo mostra i giorni senza dolore e gli ultimi attacchi.
- Le analisi mostrano gli attacchi per settimana, mese, sei mesi e anno, con un calendario mensile.

APP SALUTE
- Con il suo permesso, MigraTrack legge il sonno dall’app Salute e mostra il sonno di stanotte nel riepilogo.
- MigraTrack non scrive nulla nell’app Salute.

APPLE WATCH
- I giorni senza dolore al polso.
- Ruoti la Digital Crown per impostare l’intensità e annoti l’attacco. L’attacco compare sul suo iPhone.
- Aggiunga la complicazione «Nuovo attacco» al quadrante o usi il controllo in Centro di Controllo.

SIRI E COMANDI RAPIDI
- Dica «Annota un’emicrania in MigraTrack» per annotare un attacco con la sua intensità abituale.

REPORT PER IL SUO MEDICO
- Esporti un report PDF o un file Excel per il periodo che sceglie.
- Scelga che cosa include il report: farmaci, aura e sintomi premonitori, grafici del dolore.

PRIVACY FIN DALL’INIZIO
- Nessun account, nessun server, nessuno strumento di analisi, nessuna pubblicità.
- I suoi dati restano sul suo iPhone e sul suo Apple Watch.
- Impostazioni › Elimina tutti i dati cancella tutto in qualsiasi momento.

MigraTrack non è un dispositivo medico e non fornisce consigli medici. Si rivolga al suo medico prima di modificare la terapia.
```

### Keywords

85 bytes in UTF-8 (85 characters) of 100 bytes. The name and the subtitle are searchable already, so the list does not repeat "emicrania", "diario" or "report".

```text
mal di testa,cefalea,attacco,aura,dolore,fattore scatenante,sonno,medico,pdf,orologio
```

### What's New

For the version that adds this localization:

```text
MigraTrack ora è in italiano: app, report PDF ed Excel, Apple Watch e Siri.
```

## Siri

The description quotes the Siri phrase "Annota un’emicrania in MigraTrack" from `iosApp/iosApp/AppShortcuts.xcstrings`. Siri speaks this language ([iOS feature availability](https://www.apple.com/ios/feature-availability/), Siri section, read on October 18, 2026). Check the phrase with Product › App Shortcuts Preview before the release.
