# 🎵 Vinyle Shop Maven

Application web Java EE de gestion et vente de vinyles avec API REST sécurisée.

## 📋 Table des matières

- [Aperçu](#aperçu)
- [Fonctionnalités](#fonctionnalités)
- [Architecture](#architecture)
- [Prérequis](#prérequis)
- [Installation](#installation)
- [Configuration](#configuration)
- [Utilisation](#utilisation)
- [API REST](#api-rest)
- [Technologies](#technologies)
- [Structure du projet](#structure-du-projet)

## 🎯 Aperçu

Vinyle Shop Maven est une plateforme e-commerce complète dédiée à la vente de vinyles. L'application propose une interface web intuitive pour les clients et les administrateurs, ainsi qu'une API REST sécurisée pour l'intégration avec des systèmes tiers.

### Captures d'écran

- Interface de catalogue avec recherche avancée
- Gestion du panier en temps réel
- Système de commandes complet
- Panel d'administration

## ✨ Fonctionnalités

### Pour les Clients

- ✅ Inscription et authentification
- 🔍 Recherche de vinyles par titre ou artiste
- 🛒 Gestion du panier (ajout, modification, suppression)
- 📦 Passage de commandes
- 📋 Historique des commandes

### Pour les Administrateurs

- 👥 Gestion des utilisateurs (CRUD)
- 🏷️ Gestion des catégories
- 📊 Vue d'ensemble des commandes

### API REST

- 🎼 CRUD complet pour les vinyles
- 🎤 CRUD complet pour les artistes
- 🏷️ CRUD complet pour les catégories
- 🔐 Authentification JWT
- 📚 Documentation Swagger/OpenAPI

## 🏗️ Architecture

L'application suit une architecture en couches :

```
┌─────────────────────────┐
│   Couche Présentation   │  (JSP, Servlets)
├─────────────────────────┤
│   Couche Contrôleur     │  (Servlets, Filters)
├─────────────────────────┤
│   Couche Service        │  (Services métier)
├─────────────────────────┤
│   Couche DAO            │  (Accès aux données)
├─────────────────────────┤
│   Base de Données       │  (MySQL)
└─────────────────────────┘
```

### Design Patterns utilisés

- **DAO (Data Access Object)** : Abstraction de l'accès aux données
- **MVC (Model-View-Controller)** : Séparation des préoccupations
- **DTO (Data Transfer Object)** : Transfert de données entre couches
- **Singleton** : Gestion de la connexion à la base de données
- **Filter** : Authentification et contrôle d'accès

## 🔧 Prérequis

- **Java** : JDK 17 ou supérieur
- **Maven** : 3.8+ pour la gestion des dépendances
- **MySQL** : 8.0+ pour la base de données
- **Serveur d'application** : Tomcat 10+ ou équivalent
- **IDE recommandé** : Eclipse, IntelliJ IDEA, ou VS Code

## 📥 Installation

### 1. Cloner le repository

```bash
git clone https://github.com/adlecorre/vinyl-shop-jee.git
```

### 2. Créer la base de données

```sql
CREATE DATABASE bd_vinyle CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE bd_vinyle;

-- Créer l'utilisateur de l'application
CREATE USER 'app'@'localhost' IDENTIFIED BY 'app';
GRANT ALL PRIVILEGES ON bd_vinyle.* TO 'app'@'localhost';
FLUSH PRIVILEGES;
```

### 3. Exécuter le script de création des tables

```sql
-- Table Utilisateur
CREATE TABLE utilisateur (
    id_utilisateur INT PRIMARY KEY AUTO_INCREMENT,
    nom_utilisateur VARCHAR(100) NOT NULL,
    prenom_utilisateur VARCHAR(100) NOT NULL,
    mot_de_passe VARCHAR(255) NOT NULL,
    date_naissance DATE,
    email_utilisateur VARCHAR(150) UNIQUE NOT NULL,
    adresse_utilisateur VARCHAR(255),
    tel_utilisateur VARCHAR(20) UNIQUE,
    role_utilisateur ENUM('CLIENT', 'ADMIN') DEFAULT 'CLIENT'
);

-- Table Artiste
CREATE TABLE artiste (
    id_artiste INT PRIMARY KEY AUTO_INCREMENT,
    nom_artiste VARCHAR(150) NOT NULL
);

-- Table Catégorie
CREATE TABLE categorie (
    id_categorie INT PRIMARY KEY AUTO_INCREMENT,
    nom_categorie VARCHAR(100) NOT NULL
);

-- Table Vinyle
CREATE TABLE vinyle (
    id_vinyle INT PRIMARY KEY AUTO_INCREMENT,
    titre VARCHAR(200) NOT NULL,
    url_pochette VARCHAR(500),
    stock INT NOT NULL DEFAULT 0,
    prix_vinyle DECIMAL(10, 2) NOT NULL,
    description_vinyle TEXT,
    id_artiste INT,
    FOREIGN KEY (id_artiste) REFERENCES artiste(id_artiste) ON DELETE SET NULL
);

-- Table Commande
CREATE TABLE commande (
    id_commande INT PRIMARY KEY AUTO_INCREMENT,
    date_commande DATE,
    statut_commande ENUM('EN_ATTENTE', 'CONFIRMEE', 'ANNULEE') DEFAULT 'EN_ATTENTE',
    id_utilisateur INT,
    FOREIGN KEY (id_utilisateur) REFERENCES utilisateur(id_utilisateur) ON DELETE CASCADE
);

-- Table Ligne de Commande
CREATE TABLE lignecommande (
    id_ligne INT PRIMARY KEY AUTO_INCREMENT,
    id_commande INT,
    id_vinyle INT,
    quantite INT NOT NULL,
    FOREIGN KEY (id_commande) REFERENCES commande(id_commande) ON DELETE CASCADE,
    FOREIGN KEY (id_vinyle) REFERENCES vinyle(id_vinyle) ON DELETE CASCADE
);
```

### 4. Insérer des données de test (optionnel)

```sql
-- Données exemple
INSERT INTO artiste (nom_artiste) VALUES 
    ('Daft Punk'),
    ('Pink Floyd'),
    ('The Beatles');

INSERT INTO vinyle (titre, stock, prix_vinyle, description_vinyle, id_artiste) VALUES
    ('Random Access Memories', 10, 29.99, 'Album emblématique de Daft Punk', 1),
    ('The Dark Side of the Moon', 15, 24.99, 'Chef-d\'œuvre de Pink Floyd', 2),
    ('Abbey Road', 8, 27.99, 'Album mythique des Beatles', 3);
```

### 5. Compiler le projet

```bash
mvn clean install
```

### 6. Déployer sur Tomcat

- Copier le fichier WAR généré (`target/M2iVinyleMaven.war`) dans le dossier `webapps` de Tomcat
- Démarrer Tomcat
- L'application sera accessible à : `http://localhost:8080/M2iVinyleMaven`

## ⚙️ Configuration

### Connexion à la base de données

Les paramètres de connexion se trouvent dans :

**`src/main/java/org/eclipse/config/MySqlConnection.java`**

```java
private static final String URL = "jdbc:mysql://localhost:3306/bd_vinyle?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
private static final String USER = "app";
private static final String PASSWORD = "app";
```

### Configuration JWT (API REST)

**`src/main/java/org/eclipse/rest/JwtUtil.java`**

```java
private static final long EXP_MS = 2 * 30 * 60 * 1000; // 30 minutes
```

## 🚀 Utilisation

### Interface Web

#### Connexion

1. Accéder à `http://localhost:8080/M2iVinyleMaven/connexion`
2. Utiliser les identifiants (ou créer un compte via `/inscription`)

#### Navigation Client

- **Catalogue** : `/catalogue` - Parcourir et rechercher des vinyles
- **Panier** : `/panier` - Gérer votre panier
- **Commandes** : `/commandes` - Consulter vos commandes

#### Navigation Admin

Les administrateurs ont accès à des fonctionnalités supplémentaires pour gérer les utilisateurs et les catégories.

### API REST

#### Authentification

```bash
# Obtenir un token JWT
curl -X POST http://localhost:8080/M2iVinyleMaven/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "api-admin"
  }'
```

#### Endpoints principaux

**Vinyles**

```bash
# Lister tous les vinyles
GET /api/vinyle

# Obtenir un vinyle par ID
GET /api/vinyle/{id}

# Créer un vinyle (Admin uniquement)
POST /api/vinyle
Content-Type: application/json
Authorization: Bearer {token}

{
  "titre": "Abbey Road",
  "prixVinyle": 27.99,
  "stock": 10,
  "description": "Album des Beatles",
  "artisteDTO": {
    "idArtiste": 3,
    "nom": "The Beatles"
  }
}

# Mettre à jour un vinyle
PUT /api/vinyle/{id}

# Supprimer un vinyle
DELETE /api/vinyle/{id}
```

**Artistes**

```bash
# Lister tous les artistes
GET /api/artiste

# Créer un artiste
POST /api/artiste
Content-Type: application/json

{
  "nom": "Nom de l'artiste"
}
```

**Catégories**

```bash
# Lister toutes les catégories
GET /api/categorie

# Créer une catégorie
POST /api/categorie
```

### Documentation Swagger

Accéder à la documentation interactive : `http://localhost:8080/M2iVinyleMaven/api/openapi`

## 🛠️ Technologies

### Backend

- **Java EE 10** (Jakarta EE)
- **JAX-RS (Jersey)** - API REST
- **JDBC** - Accès aux données
- **JWT (jjwt)** - Authentification
- **Swagger/OpenAPI** - Documentation API

### Frontend

- **JSP (JavaServer Pages)**
- **JSTL** - Bibliothèque de tags
- **Bootstrap 5** - Framework CSS
- **Font Awesome** - Icônes

### Base de données

- **MySQL 8.0+**

### Build & Dépendances

- **Maven 3.8+**
- **MySQL Connector/J**
- **Jakarta Servlet API**
- **Jersey (JAX-RS)**
- **io.jsonwebtoken (JWT)**

## 📁 Structure du projet

```
M2iVinyleMaven/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── GestionCommande/
│   │   │   │   ├── Commande.java
│   │   │   │   ├── CommandeDao.java
│   │   │   │   ├── LigneDeCommande.java
│   │   │   │   ├── LigneDeCommandeDAO.java
│   │   │   │   ├── Panier.java
│   │   │   │   └── StatutCommande.java
│   │   │   ├── GestionPanier/
│   │   │   │   └── LignePanier.java
│   │   │   ├── GestionUtilisateurs/
│   │   │   │   ├── Role.java
│   │   │   │   ├── Utilisateur.java
│   │   │   │   └── UtilisateurDAO.java
│   │   │   ├── GestionVinyle/
│   │   │   │   ├── Artiste.java
│   │   │   │   ├── ArtisteDAO.java
│   │   │   │   ├── Categorie.java
│   │   │   │   ├── CategorieDAO.java
│   │   │   │   ├── Vinyle.java
│   │   │   │   └── VinyleDAO.java
│   │   │   ├── org/eclipse/
│   │   │   │   ├── config/
│   │   │   │   │   └── MySqlConnection.java
│   │   │   │   ├── controllers/
│   │   │   │   │   ├── CatalogueServlet.java
│   │   │   │   │   ├── CommandeServlet.java
│   │   │   │   │   ├── ConnexionServlet.java
│   │   │   │   │   ├── InscriptionServlet.java
│   │   │   │   │   ├── PanierServlet.java
│   │   │   │   │   └── VinyleServlet.java
│   │   │   │   ├── filters/
│   │   │   │   │   └── AuthFilter.java
│   │   │   │   ├── rest/
│   │   │   │   │   ├── ArtisteResource.java
│   │   │   │   │   ├── ArtisteServices.java
│   │   │   │   │   ├── AuthResource.java
│   │   │   │   │   ├── CategorieResource.java
│   │   │   │   │   ├── VinyleResource.java
│   │   │   │   │   ├── VinyleServices.java
│   │   │   │   │   ├── JwtUtil.java
│   │   │   │   │   └── OpenApiResource.java
│   │   │   │   └── services/
│   │   │   │       └── InscriptionService.java
│   │   │   ├── utilitaires/
│   │   │   │   ├── Clavier.java
│   │   │   │   ├── ConnexionBD.java
│   │   │   │   └── DAO.java
│   │   │   └── Main/
│   │   │       └── Main.java
│   │   └── webapp/
│   │       ├── WEB-INF/
│   │       │   ├── web.xml
│   │       │   ├── catalogue.jsp
│   │       │   ├── commandes.jsp
│   │       │   ├── connexion.jsp
│   │       │   ├── inscription.jsp
│   │       │   ├── panier.jsp
│   │       │   └── partial/
│   │       │       ├── _links.jsp
│   │       │       └── _menu.jsp
│   │       └── css/
│   │           └── style.css
│   └── test/
│       └── java/
├── pom.xml
└── README.md
```

## 🔐 Sécurité

### Authentification Web

- Filtre d'authentification (`AuthFilter`) protégeant les ressources sensibles
- Sessions utilisateur sécurisées
- Hachage des mots de passe (à implémenter avec BCrypt pour la production)

### API REST

- Authentification JWT avec tokens signés
- Rôles utilisateurs (api-user, api-admin)
- Expiration des tokens (30 minutes par défaut)
- `@RolesAllowed` pour le contrôle d'accès granulaire

## 🐛 Dépannage

### Problèmes courants

**Erreur de connexion à la base de données**
```
Solution : Vérifier que MySQL est démarré et que les identifiants sont corrects
```

**ClassNotFoundException : com.mysql.cj.jdbc.Driver**
```
Solution : Vérifier que la dépendance MySQL Connector est dans pom.xml
```

**404 sur les ressources statiques**
```
Solution : Vérifier le context path dans web.xml et l'URL d'accès
```

## 📝 TODO / Améliorations futures

- [ ] Implémenter BCrypt pour les mots de passe
- [ ] Ajouter la pagination sur le catalogue
- [ ] Système de notation et avis clients
- [ ] Upload d'images pour les pochettes
- [ ] Gestion des stocks en temps réel
- [ ] Système de notifications email
- [ ] Export des commandes (PDF, CSV)
- [ ] Dashboard analytics pour les admins
- [ ] Tests unitaires et d'intégration
