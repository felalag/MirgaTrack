# App Store metadata: Portuguese (Brazil)

App Store Connect localization "Portuguese (Brazil)". The English source and the field limits are in [APP_STORE.md](../APP_STORE.md). The terms follow [LOCALIZATION_GLOSSARY.md](../LOCALIZATION_GLOSSARY.md): "crise" for attack and episode, "enxaqueca" for migraine. The counts were made with Python `len()` and `len(text.encode("utf-8"))` on October 18, 2026.

## App information

| Field | Value | Count | Limit |
|---|---|---|---|
| Name | MigraTrack | 10 characters | 30 characters |
| Subtitle | Enxaqueca: diário e relatórios | 30 characters | 30 characters |
| Privacy Policy URL | https://felalag.github.io/MirgaTrack/privacy/ (the English page, plan section 4.2) | | |

## Version information

### Promotional text

123 characters of 170.

```text
Registre suas crises de enxaqueca no iPhone ou no Apple Watch, veja seus padrões e compartilhe um relatório com seu médico.
```

### Description

1719 characters of 4000.

```text
O MigraTrack é um diário das suas crises de enxaqueca. Registre uma crise em segundos, veja os seus padrões e leve um relatório claro à próxima consulta.

REGISTRE UMA CRISE
- Hora de início, intensidade da dor de 1 a 10, aura, sintomas, gatilhos e medicamentos.
- A sua intensidade habitual e os seus medicamentos habituais para a crise já vêm preenchidos em cada nova crise, por isso muitas vezes basta um toque.
- Registre uma crise de um dia anterior no calendário ou edite-a depois.

VEJA OS SEUS PADRÕES
- O resumo mostra os seus dias sem dor e as suas últimas crises.
- As análises mostram as crises por semana, mês, seis meses e ano, com um calendário do mês.

APP SAÚDE
- Com a sua permissão, o MigraTrack lê o seu sono no app Saúde e mostra o sono da última noite no resumo.
- O MigraTrack não grava nada no app Saúde.

APPLE WATCH
- Os seus dias sem dor no pulso.
- Gire a Digital Crown para ajustar a intensidade e registre a crise. Ela aparece no seu iPhone.
- Adicione a complicação "Nova crise" ao mostrador ou use o controle da Central de Controle.

SIRI E ATALHOS
- Diga "Registrar uma enxaqueca no MigraTrack" para registre uma crise com a sua intensidade habitual.

RELATÓRIOS PARA O SEU MÉDICO
- Exporte um relatório PDF ou um arquivo do Excel do período que escolher.
- Escolha o que o relatório inclui: medicamentos, aura e sintomas de alerta, e gráficos da dor.

PRIVACIDADE DESDE O INÍCIO
- Sem conta, sem servidor, sem análises e sem publicidade.
- Os seus dados ficam no seu iPhone e no seu Apple Watch.
- Ajustes › Apagar todos os dados apaga tudo a qualquer momento.

O MigraTrack não é um dispositivo médico e não dá orientação médica. Fale com o seu médico antes de mudar o seu tratamento.
```

### Keywords

82 bytes in UTF-8 (78 characters) of 100 bytes. The name and the subtitle are searchable already, so the list does not repeat "enxaqueca", "diário" or "relatórios". "Migrânea" is the clinical word of the Brazilian ICHD-3.

```text
dor de cabeça,cefaleia,migrânea,crise,aura,dor,gatilho,sono,médico,pdf,relógio
```

### What's New

For the version that adds this localization:

```text
O MigraTrack agora está em português: app, relatórios PDF e Excel, Apple Watch e Siri.
```

## Siri

The description quotes the Siri phrase "Registrar uma enxaqueca no MigraTrack" from `iosApp/iosApp/AppShortcuts.xcstrings`. Siri speaks this language ([iOS feature availability](https://www.apple.com/ios/feature-availability/), Siri section, read on October 18, 2026). Check the phrase with Product › App Shortcuts Preview before the release.
