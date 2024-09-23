# Projet : IMDb Open

## Présentation

Bonjour Christophe,

Dans cette version du projet, j'ai intégré Docker pour créer des conteneurs pour l'application et la base de données (MariaDB). Le projet comprend maintenant un fichier Dockerfile pour construire l'image de l'application et un fichier `docker-compose.yml` pour gérer l'application et la base de données ensemble.

## Dockerfile

Le Dockerfile est divisé en deux étapes :

1. **Construction de l'application** :  
   Nous utilisons l'image officielle de Maven et Java (Temurin 17). Le code source est copié dans le conteneur, puis l'application est construite avec Maven (`mvn clean package -DskipTests`).

2. **Exécution de l'application** :  
   Une fois l'application construite, le fichier JAR est copié dans une nouvelle image basée sur l'image JRE de Temurin 17. L'application écoute sur le port 8080.

## docker-compose.yml

Le fichier `docker-compose.yml` configure deux services :

1. **app** :  
   Ce service utilise l'image Docker de l'application, expose le port 8080, et configure les variables d'environnement pour se connecter à la base de données.

2. **db** :  
   Ce service utilise l'image officielle de MariaDB, crée une base de données nommée `testimdb` et persiste les données à l'aide d'un volume Docker. Le port 3306 (MariaDB) est mappé sur le port 3308 de la machine hôte.

## Exécution de l'application avec Docker

Pour exécuter l'application avec Docker, suivez ces étapes :

1. Clonez le projet ou téléchargez les fichiers nécessaires.
2. Assurez-vous que Docker et Docker Compose sont installés sur votre machine.
3. Vous pouvez récupérer l'image de l'application directement depuis Docker Hub avec la commande suivante :
   
4. 
```bash
docker pull filipdab/imdb_open:latest
```

Ensuite, lancez les services en utilisant Docker Compose :

```bash
docker-compose up
```

Cela créera et démarrera à la fois l'application et la base de données. L'application sera disponible sur le port 8080 et la base de données MariaDB sur le port 3308.

Pour arrêter les services, utilisez :

```bash
docker-compose down
```

Variables d'environnement

Les variables suivantes sont utilisées pour la connexion à la base de données :

    SPRING_DATASOURCE_URL
    SPRING_DATASOURCE_USERNAME (par défaut : diginamic)
    SPRING_DATASOURCE_PASSWORD (par défaut : digipass)

N’hésite pas à me contacter si tu as des questions ou des retours :) 
