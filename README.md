# 🎓 Projet Étudiants — Version 3 (Micro Services complets)

> Formateur : Wahid Hamdi — Activités d'Intégration de Compétences, Partie 3

## 🏗️ Architecture

```
┌─────────────────────────────────────────────────────────┐
│  Mobile App (React Native)   Frontend (Next.js :3000)   │
└────────────────────┬────────────────────┬───────────────┘
                     │                    │
                     ▼                    ▼
              ┌─────────────────────────────┐
              │     API Gateway (:8080)      │
              │   Spring Cloud Gateway       │
              └────────┬──────────┬─────────┘
                       │          │
              ┌────────▼──┐  ┌────▼───────────┐
              │ etudiant  │  │ grading-service │
              │ service   │  │    (:8082)      │
              │  (:8081)  │  └────────┬────────┘
              └─────┬─────┘          │ Feign
                    │                │
              ┌─────▼────────────────▼──────┐
              │      Eureka Server (:8761)   │
              └─────────────────────────────┘
                    │
              ┌─────▼────────────────────────┐
              │  PostgreSQL (:5432)  Redis    │
              └──────────────────────────────┘
```

## 🚀 Démarrage rapide

```bash
# Cloner et basculer sur la bonne branche
git checkout version-3

# Lancer toute l'architecture
docker-compose up --build

# Accès aux services
# API Gateway    → http://localhost:8080
# Eureka UI      → http://localhost:8761
# Frontend       → http://localhost:3000
# Swagger étud.  → http://localhost:8081/swagger-ui.html
# Swagger notes  → http://localhost:8082/swagger-ui.html
```

## 📦 Structure du projet

```
projet-etudiants/
├── api-spring-boot/      # Micro service étudiant (:8081)
├── grading-service/      # Micro service notes (:8082)  ← NOUVEAU
├── eureka-server/        # Serveur de registre (:8761)  ← NOUVEAU
├── api-gateway/          # API Gateway (:8080)          ← NOUVEAU
├── frontend/             # Application Next.js (:3000)  ← NOUVEAU
├── mobile-app/           # App React Native (mise à jour)
├── k8s/                  # Manifests Kubernetes (Partie 2)
├── .github/
│   ├── ISSUE_TEMPLATE/
│   │   ├── bug_report.md
│   │   └── feature_request.md
│   └── pull_request_template.md
└── docker-compose.yml    # Orchestration complète
```

## 🔗 Endpoints principaux (via API Gateway)

| Méthode | URL | Description |
|---------|-----|-------------|
| GET | `/api/etudiants` | Liste tous les étudiants |
| GET | `/api/etudiants/{id}` | Détail d'un étudiant |
| POST | `/api/etudiants` | Créer un étudiant |
| PUT | `/api/etudiants/{id}` | Modifier un étudiant |
| DELETE | `/api/etudiants/{id}` | Supprimer un étudiant |
| GET | `/api/departements` | Liste les départements |
| GET | `/api/notes` | Liste toutes les notes |
| GET | `/api/notes?studentId=X` | Notes d'un étudiant |
| POST | `/api/notes` | Créer une note (vérifie l'étudiant via Feign) |
| PUT | `/api/notes/{id}` | Modifier une note |
| DELETE | `/api/notes/{id}` | Supprimer une note |

## 📋 Convention de review (Q2)

### Règles de l'équipe

1. **Délai de review** : toute PR ouverte doit être relue dans les **48 heures** suivant son ouverture.
2. **Approbation requise** : au minimum **1 review approuvée** avant tout merge sur `main` ou `version-3`.
3. **Push directs interdits** : on ne push jamais directement sur `main` ou `version-3` — toujours via une PR.
4. **Commentaires bloquants** : tout commentaire marqué comme bloquant doit être **résolu avant le merge** (pas de "resolve" sans correction réelle).
5. **Lien Jira obligatoire** : chaque PR doit être reliée à un ticket Jira du Sprint en cours.
6. **Tests verts** : la CI doit passer (tests verts, build Docker réussi) avant le merge.
7. **Self-review** : avant de demander une review, relisez votre propre PR et cochez la checklist du template.

### Workflow Git

```
main
 └── version-3
       └── feature/PROJ-XX-description   ← vos branches de travail
```

Chaque fonctionnalité = 1 branche + 1 PR vers `version-3`.

## 🧪 Tests

```bash
# Tests Spring Boot (étudiant-service)
cd api-spring-boot && mvn test

# Tests grading-service
cd grading-service && mvn test

# Tests frontend
cd frontend && npm test
```

## 🐳 Services Docker

| Service | Port | Dépend de |
|---------|------|-----------|
| postgres | 5432 | — |
| redis | 6379 | — |
| eureka-server | 8761 | — |
| etudiant-service | 8081 | postgres, redis, eureka-server |
| grading-service | 8082 | postgres, eureka-server |
| api-gateway | 8080 | eureka-server |
| frontend | 3000 | api-gateway |
