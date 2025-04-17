### Pollen-monitoring

## Introduction

Ce projet est une application web de surveillance des niveaux de pollen qui permet de visualiser et d'analyser les données de concentration pollinique en temps réel. L'application récupère les données depuis l'API Open-Meteo et les présente sous forme de tableaux et de graphiques interactifs.

## Fonctionnalités principales

- 📊 **Visualisation des données** : Affichage des niveaux de pollen sous forme de tableaux et de graphiques
- 📍 **Support multi-localisation** : Possibilité de consulter les données pour différentes localisations
- 🌿 **Types de pollen variés** : Suivi de plusieurs types de pollen (aulne, bouleau, graminées, armoise, olivier, ambroisie)
- 📈 **Graphiques interactifs** : Visualisation de l'évolution des niveaux de pollen sur différentes périodes
- 🔄 **Données en temps réel** : Récupération des données les plus récentes depuis l'API Open-Meteo


## Technologies utilisées

- **Backend** : Java Spring Boot
- **Frontend** : HTML, CSS, JavaScript
- **Visualisation** : Chart.js
- **API** : Open-Meteo pour les données de pollen
- **Monitoring** : Micrometer pour les métriques


## Structure du projet

```plaintext
pollen-monitoring/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── backend/
│   │   │           └── pollen_monitoring/
│   │   │               ├── config/
│   │   │               │   ├── AppConfig.java
│   │   │               │   └── MvcConfig.java
│   │   │               ├── controller/
│   │   │               │   ├── AirDataController.java
│   │   │               │   └── PollenController.java
│   │   │               ├── metrics/
│   │   │               │   └── PollenMetrics.java
│   │   │               ├── models/
│   │   │               │   └── PollutionData.java
│   │   │               ├── service/
│   │   │               │   └── PollenService.java
│   │   │               └── PollenMonitoringApplication.java
│   │   └── resources/
│   │       └── static/
│   │           ├── css/
│   │           │   └── pollen-styles.css
│   │           ├── pollen-table.html
│   │           └── pollen-graph.html
└── pom.xml
```

## Installation et configuration

### Prérequis

- Java 11 ou supérieur
- Maven
- Open-Meteo (gratuit, pas besoin de clé API)


### Configuration

1. Clonez le dépôt :

```shellscript
git clone https://github.com/HamCam203/pollen-monitoring.git
cd pollen-monitoring
```


2. Configurez les propriétés dans `application.properties` :

```plaintext
# Configuration de l'API Open-Meteo
openmeteo.api.url=https://air-quality-api.open-meteo.com/v1/air-quality

# Coordonnées par défaut (à personnaliser)
pollen.latitude=48.8566
pollen.longitude=2.3522

# Configuration du serveur
server.port=8080
```


3. Compilez et lancez l'application :

```shellscript
mvn clean install
mvn spring-boot:run
```




## Utilisation

Une fois l'application démarrée, vous pouvez y accéder via votre navigateur :

- **Tableau des données** : [http://localhost:8080/api/pollen](http://localhost:8080/api/pollen)
- **Graphiques** : [http://localhost:8080/api/pollen/graph](http://localhost:8080/api/pollen/graph)


### Fonctionnalités de l'interface

#### Tableau des données

- Sélection de la localisation
- Affichage des types de pollen et de leurs niveaux
- Indication visuelle du niveau de risque (faible, moyen, élevé)


#### Graphiques

- Sélection de la localisation
- Filtrage par type de pollen
- Sélection de la période d'affichage (24h, 48h, 72h, 4 jours)
- Visualisation de l'évolution des niveaux de pollen dans le temps


## API Endpoints

L'application expose les endpoints REST suivants :

| Endpoint | Méthode | Description
|-----|-----|-----
| `/api/pollen` | GET | Redirige vers la page de tableau des données
| `/api/pollen/data` | GET | Renvoie les données brutes de pollen au format JSON
| `/api/pollen/graph` | GET | Redirige vers la page de graphiques
| `/api/pollution` | GET | Renvoie les données de pollution et de pollen
| `/api/pollens` | GET | Renvoie uniquement les indices de pollen actuels


## Métriques et surveillance

Le projet intègre des métriques Micrometer pour surveiller les niveaux de pollen. Ces métriques sont mises à jour toutes les heures et peuvent être consultées via l'endpoint Actuator :

```plaintext
http://localhost:8080/actuator/metrics/pollen_birch_pollen_index
```

## Développement futur

Voici quelques améliorations prévues pour les versions futures :

- Ajout d'une carte géographique pour visualiser les niveaux de pollen par région
- Système d'alertes personnalisables par email ou notification
- Prévisions de pollen sur plusieurs jours
- Support pour les appareils mobiles via une application dédiée
- Intégration avec d'autres sources de données environnementales
