# AMT-Rekognition-Sprint1

> Nicolas Crausaz & Maxime Scharwath

Ce laboratoire consiste à mettre en œuvre une application permettant le stockage `S3` et l'analyse d'image `Rekognition`.

....

# Prérequis

Pour utiliser cette application, il nécessaire d'avoir installé sur votre machine les dépendances suivantes :

- Java
- Maven

Vous devez également avoir à disposition : un Bucket AWS S3, ainsi que les clés d'accès publique et privée y permettant l'accès.

## Configuration

Copiez le contenu du fichier `.env.template` dans un nouveau fichier `.env` et ajoutez-y les informations nécessaires :

```
AWS_BUCKET_NAME= <nom du bucket> (ex: test-bucket.example.com)
AWS_ACCESS_KEY_ID= <access_key> (fournie par AWS)
AWS_SECRET_ACCESS_KEY= <private_access_key> (fournie par AWS)
AWS_REGION= <region du bucket> (ex: eu-west-2)
```

## Utilisation

### Installation des dépendances

Les dépendances détaillées du projet se trouvent [ici](https://github.com/AMT-TEAM10/AMT-Rekognition/wiki/D%C3%A9pendances).

Pour installer les dépendances, entrer la commande suivante :
> $ mvn install -DskipTests

### Exécution

Pour exécuter le programme, entrer la commande suivante :

# Tests

Après avoir installé les dépendances, il est possible d'exécuter les test en entrant la commande suivante :
> $ mvn tests


# Directives

Toute documentation relatives aux practices, dépendances, architecture se trouvent dans notre [Wiki](https://github.com/AMT-TEAM10/AMT-Rekognition/wiki).

