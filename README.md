# TP - JDBC pour manipuler une base de données

Programmation Orientée Objet avec Java – Coda 1 ère année – 2026

## Exercice 1 - rpg_character

### 1.1 - Créez un nouveau projet maven

Il doit compiler en java version 25.

- groupId : celui que vous avez utilisé dans le TP du Maven
- artifactId : `tp-jdbc`

### 1.2 - Ajoutez la dépendance du driver JDBC de SQLite

- groupId : `org.xerial`
- artifactId : `sqlite-jdbc`
- version : `3.51.0.0`

### 1.3 - Créer un package

Créez un package correspondant au `groupId` de 
votre projet maven suivi de `.tp.jdbc.test`.

Ex: si votre groupId était `foo.bar.baz`, 
le nom de votre package serait `foo.bar.baz.tp.jdbc.test`.

### 1.4 - Créer un programme principal

Dans le package que vous venez de créer.

Ajouter une nouvelle classe java nommée `TestJdbc`.

Dans ce fichier ajouter le nécessaire pour que votre programme soit exécutable.

Faites en sorte que ce programme principal affiche le texte suivant dans la console : 

```text
Je vais utiliser une base de données.
```

### 1.5 - Se connecter à une base de données

Dans le programme principal, créer **une connexion** à la base de données en lui donnant l'URL suivante : 

```text
jdbc:sqlite:test.db
```

**Vous devez gérer les exceptions potentielles**

> 💡une connexion à une base de données est une resource `AutoCloseable`

En cas d'exception du type `SQLException`, affichez un message d'erreur similaire au suivant : 

`"Erreur lors de la connexion à la base de données :"` suivi du message correspondant à l'exception. 


**Exécutez le programme**

Un fichier `test.db` devrait être apparu à la racine de votre projet.


### 1.6 - Création du modèle de données

Dans votre programme, créez un `Statement`.

> 💡un Statement est une resource `AutoCloseable`

Exécutez la requête suivante à l'aide du statement.

```sqlite
CREATE TABLE IF NOT EXISTS rpg_character
(
    id   integer      not null 
            constraint rpg_character_pk
            primary key autoincrement,
    name varchar(255) not null,
    hp   integer      not null,
    def  integer      not null,
    money  integer      not null,
    atk  integer,
    heal integer
)
```

En cas d'erreur `SQLException`, le message suivant doit être affiché : 

`"Erreur lors de création de la table rpg_character : "` suivi du message correspondant à l'exception.


### 1.7 - Ouvrir la base de données dans IntelliJ

IntelliJ dispose d'outils intégrés pour afficher et modifier des bases de données.

Double-cliquez sur le fichier `test.db` à la racine de votre projet.

Si nécessaire, télécharger le Driver SQLite.

![](img/intellij-db-test-sqlite.png)

Vérifier la connexion en cliquant sur "Test Connection"

![](img/intellij-db-test-sqlite-verify-connection.png)

Valider en cliquant sur "OK"

---

Ouvrir le menu latéral et les outils de base de données

Déplier la base de données `table` jusqu'à voir les colonnes de la table `rpg_character`

![](img/intellij-db-unfold-table.png)

### 1.8 - voir les données de la table

Double-cliquer sur la table `rpg_character`.

![](img/intellij-db-open-table.png)

Une vue en tableau apparait.

### 1.9 - Ajouter des lignes

Dans la vue tableau, ajouter des lignes.

Puis "submit".

![](img/intellij-db-table-insert-row-submit.png)

La table devrait se mettre à jour;

![](img/intellij-db-table-rows-inserted.png)

### 1.10 - Voir le code SQL exécuté par IntelliJ

IntelliJ exécute du code SQL pour nous.

Nous pouvons le voir dans la partie "Services"
en dépliant "Database", puis `test`, puis `rpg_character`.

![](img/intellij-services-database-test-rpg_character.png)

Nous pourrons nous servir de ces requêtes par la suite dans notre programme.

### 1.11 - Utiliser la Query console

- Retourner dans le menu de base de données
- Jump to query console
- Choisir la query console par défaut

Une zone de texte apparait sur la gauche.

Dans cette zone de texte, saisir la requête suivante : 

```sqlite
SELECT *
FROM rpg_character;
```

Puis exécuter la requête.

Le résultat s'affiche dans le menu "Services" en bas de l'écran.

![](img/intellij-query-console-select-rpg_character.png)

### 1.12 - Écrire un record

Retourner dans le programme Java.

Dans un nouveau fichier, créer un **record** nommé `RpgCharacter`.

Celui-ci doit contenir les attributs suivants : 

- `name` : chaîne de caractères
- `hp` : nombre entier
- `def` : nombre entier
- `money` : nombre entier
- `atk` : nombre entier

### 1.13 Écrire une requête SQL dans le code Java


À partir du statement existant.

Exécuter la requête suivante afin qu'elle retourne un `ResultSet`.

```sqlite
SELECT name, hp, def, money, atk 
FROM rpg_character
```

Tant que le `ResultSet` a des lignes :

Récupérer les valeurs des colonnes :
- `name`
- `hp`
- `def`
- `money`
- `atk`

Instancier un `RpgCharacter` à partir de ces valeurs.

Afficher le contenu du `RpgCharacter` dans la console.

Le résultat devrait ressembler à ceci : 

```text
RpgCharacter[name=Rincevent, hp=30, def=1, money=34, atk=2]
RpgCharacter[name=Cohen le barbare, hp=120, def=2, money=78, atk=5]
RpgCharacter[name=Deuxfleurs, hp=20, def=0, money=350, atk=0]
```
