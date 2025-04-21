### Pollen-monitoring

## Introduction

Ce projet est une application web de surveillance des niveaux de pollen qui permet de visualiser et d'analyser les données de concentration pollinique en temps réel. L'application récupère les données depuis l'API Open-Meteo et les présente sous forme de tableaux et de graphiques interactifs.

## Fonctionnalités principales

- 📊 **Visualisation des données** : Affichage des niveaux de pollen sous forme de tableaux et de graphiques
- 📍 **Support multi-localisation** : Possibilité de consulter les données pour différentes localisations
- 🌿 **Types de pollen variés** : Suivi de plusieurs types de pollen (aulne, bouleau, graminées, armoise, olivier, ambroisie)
- 📈 **Graphiques interactifs** : Visualisation de l'évolution des niveaux de pollen sur différentes périodes
- 🔄 **Données en temps réel** : Récupération des données les plus récentes depuis l'API Open-Meteo
- 📉 **Dashboard Grafana** : Visualisation avancée des métriques avec Grafana


## Technologies utilisées

- **Backend** : Java Spring Boot
- **Frontend** : HTML, CSS, JavaScript
- **Visualisation** : Chart.js
- **API** : Open-Meteo pour les données de pollen
- **Monitoring** : Micrometer pour les métriques
- **Conteneurisation** : Docker et Docker Compose
- **Métriques** : Prometheus pour la collecte des métriques
- **Dashboard** : Grafana pour la visualisation des métriques


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
├── Dockerfile
├── docker-compose.yml
├── prometheus/
│   └── prometheus.yml
├── grafana/
│   ├── dashboards/
│   │   └── pollen-dashboard.json
│   └── provisioning/
│       ├── dashboards/
│       │   └── dashboard.yml
│       └── datasources/
│           └── datasource.yml
└── pom.xml
```

## Installation et configuration

### Prérequis

- Java 21 ou supérieur
- Maven
- Docker et Docker Compose
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

# Configuration des métriques
management.endpoints.web.exposure.include=*
management.endpoint.prometheus.enabled=true
management.metrics.export.prometheus.enabled=true
management.metrics.tags.application=pollen-monitoring
```

### Déploiement avec Docker

1. Construisez et démarrez les conteneurs avec Docker Compose :


```shellscript
docker-compose up -d
```

Cette commande va :

- Construire l'image Docker de l'application Spring Boot
- Démarrer l'application sur le port 8080
- Démarrer Prometheus sur le port 9090
- Démarrer Grafana sur le port 3000


2. Vérifiez que les conteneurs sont en cours d'exécution :


```shellscript
docker-compose ps
```

### Déploiement manuel (sans Docker)

Si vous préférez ne pas utiliser Docker :

```shellscript
mvn clean install
mvn spring-boot:run
```

## Utilisation

Une fois l'application démarrée, vous pouvez y accéder via votre navigateur :

- **Tableau des données** : [http://localhost:8080/api/pollen](http://localhost:8080/api/pollen)
- **Graphiques** : [http://localhost:8080/api/pollen/graph](http://localhost:8080/api/pollen/graph)
- **Prometheus** : [http://localhost:9090](http://localhost:9090)
- **Grafana** : [http://localhost:3000](http://localhost:3000) (identifiants par défaut : admin/admin)


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


#### Dashboard Grafana

Après avoir accédé à Grafana ([http://localhost:3000](http://localhost:3000)) :

1. Connectez-vous avec les identifiants par défaut (admin/admin)
2. Accédez au dashboard "Surveillance du Pollen" préconfiguré
3. Explorez les différentes visualisations :

1. Niveaux de pollen en temps réel
2. Évolution des niveaux de pollen sur différentes périodes
3. Alertes sur les niveaux élevés de pollen





## API Endpoints

L'application expose les endpoints REST suivants :

| Endpoint | Méthode | Description
|-----|-----|-----
| `/api/pollen` | GET | Redirige vers la page de tableau des données
| `/api/pollen/data` | GET | Renvoie les données brutes de pollen au format JSON
| `/api/pollen/graph` | GET | Redirige vers la page de graphiques
| `/api/pollution` | GET | Renvoie les données de pollution et de pollen
| `/api/pollens` | GET | Renvoie uniquement les indices de pollen actuels
| `/actuator/prometheus` | GET | Expose les métriques au format Prometheus


## Métriques et surveillance

Le projet intègre des métriques Micrometer pour surveiller les niveaux de pollen. Ces métriques sont collectées par Prometheus et visualisées dans Grafana.

### Métriques disponibles

- `pollen.index` : Niveau de pollen par type
- Métriques système : Utilisation CPU, mémoire, etc.
- Métriques JVM : Garbage collection, threads, etc.


### Requêtes Prometheus utiles

Voici quelques requêtes que vous pouvez utiliser dans Prometheus ou Grafana :

```plaintext
# Niveau moyen de pollen par type sur les dernières 24h
avg_over_time(pollen_index{}[24h])

# Taux de changement du niveau de pollen
rate(pollen_index{}[6h])

# Niveau maximum de pollen par type
max by (type) (pollen_index{})
```

## Arrêt des services

Pour arrêter tous les services Docker :

```shellscript
docker-compose down
```

Pour arrêter les services et supprimer les volumes (attention, cela effacera toutes les données) :

```shellscript
docker-compose down -v
```

## Contribution

Les contributions sont les bienvenues ! N'hésitez pas à ouvrir une issue ou à soumettre une pull request.

## Licence

Ce projet est sous licence MIT.
