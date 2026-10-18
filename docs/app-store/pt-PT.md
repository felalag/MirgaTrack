# App Store metadata: Portuguese (Portugal)

App Store Connect localization "Portuguese (Portugal)". The English source and the field limits are in [APP_STORE.md](../APP_STORE.md). The terms follow [LOCALIZATION_GLOSSARY.md](../LOCALIZATION_GLOSSARY.md): "crise" for attack and episode, "enxaqueca" for migraine. The counts were made with Python `len()` and `len(text.encode("utf-8"))` on October 18, 2026.

European Portuguese text: "registe", "partilhe", "ficheiro", "Definições", "desencadeante".

## App information

| Field | Value | Count | Limit |
|---|---|---|---|
| Name | MigraTrack | 10 characters | 30 characters |
| Subtitle | Enxaqueca: diário e relatórios | 30 characters | 30 characters |
| Privacy Policy URL | https://felalag.github.io/MirgaTrack/privacy/ (the English page, plan section 4.2) | | |

## Version information

### Promotional text

127 characters of 170.

```text
Registe as suas crises de enxaqueca no iPhone ou no Apple Watch, veja os seus padrões e partilhe um relatório com o seu médico.
```

### Description

1732 characters of 4000.

```text
O MigraTrack é um diário das suas crises de enxaqueca. Registe uma crise em segundos, veja os seus padrões e leve um relatório claro à próxima consulta.

REGISTE UMA CRISE
- Hora de início, intensidade da dor de 1 a 10, aura, sintomas, desencadeantes e medicamentos.
- A sua intensidade habitual e os seus medicamentos habituais para a crise já vêm preenchidos em cada nova crise, por isso muitas vezes basta um toque.
- Registe uma crise de um dia anterior no calendário ou edite-a depois.

VEJA OS SEUS PADRÕES
- O resumo mostra os seus dias sem dor e as suas últimas crises.
- As análises mostram as crises por semana, mês, seis meses e ano, com um calendário do mês.

APP SAÚDE
- Com a sua autorização, o MigraTrack lê o seu sono na app Saúde e mostra o sono da última noite no resumo.
- O MigraTrack não escreve nada na app Saúde.

APPLE WATCH
- Os seus dias sem dor no pulso.
- Rode a Digital Crown para ajustar a intensidade e registe a crise. Ela aparece no seu iPhone.
- Adicione a complicação "Nova crise" ao mostrador ou use o controlo do Centro de Controlo.

SIRI E ATALHOS
- Diga "Registar uma enxaqueca no MigraTrack" para registe uma crise com a sua intensidade habitual.

RELATÓRIOS PARA O SEU MÉDICO
- Exporte um relatório PDF ou um ficheiro do Excel do período que escolher.
- Escolha o que o relatório inclui: medicamentos, aura e sintomas de alerta, e gráficos da dor.

PRIVACIDADE DESDE O INÍCIO
- Sem conta, sem servidor, sem análises e sem publicidade.
- Os seus dados ficam no seu iPhone e no seu Apple Watch.
- Definições › Apagar todos os dados apaga tudo a qualquer momento.

O MigraTrack não é um dispositivo médico e não dá aconselhamento médico. Fale com o seu médico antes de alterar o seu tratamento.
```

### Keywords

78 bytes in UTF-8 (75 characters) of 100 bytes. The name and the subtitle are searchable already, so the list does not repeat "enxaqueca", "diário" or "relatórios".

```text
dor de cabeça,cefaleia,crise,aura,dor,desencadeante,sono,médico,pdf,relógio
```

### What's New

For the version that adds this localization:

```text
O MigraTrack está agora em português europeu: app, relatórios PDF e Excel, Apple Watch e Siri.
```

## Siri

The description quotes the Siri phrase "Registar uma enxaqueca no MigraTrack" from `iosApp/iosApp/AppShortcuts.xcstrings`. Siri speaks this language ([iOS feature availability](https://www.apple.com/ios/feature-availability/), Siri section, read on October 18, 2026). Check the phrase with Product › App Shortcuts Preview before the release.
