# 🏥 Plateforme d'Expertise Médicale

Application web Java EE pour la gestion des consultations médicales entre infirmiers, médecins généralistes et spécialistes.

## 📝 Description

Cette plateforme permet la collaboration entre différents professionnels de santé :

- **Infirmiers** : Enregistrent les patients et leurs signes vitaux
- **Médecins Généralistes** : Créent des consultations et demandent des avis spécialisés
- **Médecins Spécialistes** : Gèrent leurs créneaux et fournissent des expertises

## 🛠️ Technologies

- **Backend** : Java 17, Jakarta EE 10, Hibernate 6.4.4
- **Frontend** : JSP, JSTL, TailwindCSS
- **Base de données** : MySQL 8.0
- **Serveur** : Apache Tomcat 10.x
- **Build** : Maven 3.x

## 🚀 Installation rapide

### 1. Prérequis
- Java JDK 17+
- Maven 3.6+
- MySQL 8.0+
- Tomcat 10.x

### 2. Configuration

Créer la base de données :
```sql
CREATE DATABASE plateforme_expertise_medicale;
```

Modifier `src/main/resources/META-INF/persistence.xml` avec vos identifiants MySQL :
```xml
<property name="jakarta.persistence.jdbc.user" value="root"/>
<property name="jakarta.persistence.jdbc.password" value="votre_mot_de_passe"/>
```

### 3. Lancement

```bash
# Compiler
mvn clean install

# Lancer avec Tomcat
mvn tomcat7:run
```

Accéder à l'application : `http://localhost:8080`

## 📁 Structure du projet

```
src/main/java/org/platform_expertise_medicle/
├── model/          # Entités JPA (Patient, User, Consultation, etc.)
├── DAO/            # Accès aux données
├── servlet/        # Contrôleurs (Servlets)
├── enums/          # Role, StatutConsultation
└── util/           # Utilitaires (JpaUtil)

src/main/webapp/
├── auth/           # Page de connexion
├── infirmier/      # Pages pour infirmiers
├── generaliste/    # Pages pour généralistes
└── specialiste/    # Pages pour spécialistes
```

## 🔑 Fonctionnalités principales

### Infirmier
- Ajouter et modifier des patients
- Saisir les signes vitaux
- Consulter l'historique des patients

### Médecin Généraliste
- Créer des consultations
- Saisir symptômes, diagnostics et prescriptions
- Demander l'avis d'un spécialiste
- Consulter l'historique

### Médecin Spécialiste
- Gérer les créneaux de disponibilité
- Répondre aux demandes d'expertise
- Suggérer des actes techniques

## 🏗️ Architecture

Le projet utilise l'architecture **MVC** :
- **Model** : Entités JPA (Patient, Consultation, User, etc.)
- **View** : Pages JSP
- **Controller** : Servlets Java

**Pattern DAO** pour l'accès aux données avec JPA/Hibernate.

## 📚 Documentation

Pour plus de détails techniques, consultez [DOCUMENTATION_TECHNIQUE_COMPLETE.md](DOCUMENTATION_TECHNIQUE_COMPLETE.md)

## 👨‍💻 Auteur
