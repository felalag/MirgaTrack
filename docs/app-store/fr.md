# App Store metadata: French

App Store Connect localization "French". The English source and the field limits are in [APP_STORE.md](../APP_STORE.md). The terms follow [LOCALIZATION_GLOSSARY.md](../LOCALIZATION_GLOSSARY.md): "crise" for attack and episode. The counts were made with Python `len()` and `len(text.encode("utf-8"))` on October 18, 2026.

## App information

| Field | Value | Count | Limit |
|---|---|---|---|
| Name | MigraTrack | 10 characters | 30 characters |
| Subtitle | Journal de migraine, rapports | 29 characters | 30 characters |
| Privacy Policy URL | https://felalag.github.io/MirgaTrack/privacy/ (the English page, plan section 4.2) | | |

## Version information

### Promotional text

120 characters of 170.

```text
Notez vos crises de migraine sur iPhone ou Apple Watch, repérez vos tendances et partagez un rapport avec votre médecin.
```

### Description

1837 characters of 4000.

```text
MigraTrack est un journal de vos crises de migraine. Notez une crise en quelques secondes, repérez vos tendances et apportez un rapport clair à votre prochain rendez-vous.

NOTER UNE CRISE
- Heure de début, intensité de la douleur de 1 à 10, aura, symptômes, déclencheurs et médicaments.
- Votre intensité habituelle et vos traitements de crise habituels sont déjà remplis pour chaque nouvelle crise : souvent, un seul toucher suffit.
- Notez une crise d’un jour passé depuis le calendrier ou modifiez-la plus tard.

REPÉRER VOS TENDANCES
- Le résumé affiche vos jours sans douleur et vos dernières crises.
- Les analyses affichent les crises par semaine, mois, six mois et année, avec un calendrier du mois.

APP SANTÉ
- Avec votre autorisation, MigraTrack lit votre sommeil dans l’app Santé et affiche le sommeil de la nuit dans le résumé.
- MigraTrack n’écrit rien dans l’app Santé.

APPLE WATCH
- Vos jours sans douleur au poignet.
- Tournez la Digital Crown pour régler l’intensité et notez la crise. Elle s’affiche sur votre iPhone.
- Ajoutez la complication « Nouvelle crise » à votre cadran ou utilisez la commande du Centre de contrôle.

SIRI ET RACCOURCIS
- Dites « Noter une migraine dans MigraTrack » pour noter une crise avec votre intensité habituelle.

RAPPORTS POUR VOTRE MÉDECIN
- Exportez un rapport PDF ou un fichier Excel pour la période de votre choix.
- Choisissez le contenu du rapport : médicaments, aura et symptômes annonciateurs, graphiques de la douleur.

CONFIDENTIALITÉ PAR DÉFAUT
- Ni compte, ni serveur, ni outil d’analyse, ni publicité.
- Vos données restent sur votre iPhone et votre Apple Watch.
- Réglages › Supprimer toutes les données efface tout à tout moment.

MigraTrack n’est pas un dispositif médical et ne donne pas d’avis médical. Parlez-en à votre médecin avant de modifier votre traitement.
```

### Keywords

90 bytes in UTF-8 (85 characters) of 100 bytes. The name and the subtitle are searchable already, so the list does not repeat "migraine", "journal" or "rapports".

```text
céphalée,mal de tête,crise,aura,douleur,déclencheur,sommeil,médecin,agenda,pdf,montre
```

### What's New

For the version that adds this localization:

```text
MigraTrack est maintenant en français : app, rapports PDF et Excel, Apple Watch et Siri.
```

## Siri

The description quotes the Siri phrase "Noter une migraine dans MigraTrack" from `iosApp/iosApp/AppShortcuts.xcstrings`. Siri speaks this language ([iOS feature availability](https://www.apple.com/ios/feature-availability/), Siri section, read on October 18, 2026). Check the phrase with Product › App Shortcuts Preview before the release.
