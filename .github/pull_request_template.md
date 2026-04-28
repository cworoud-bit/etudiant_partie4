## 📝 Description du changement
<!-- Décrivez brièvement ce que fait cette PR -->

## 🔗 Ticket Jira
<!-- Lien vers le ticket Jira correspondant -->
Ticket : [PROJ-XX](https://votre-jira.atlassian.net/browse/PROJ-XX)

## 🏷️ Type de changement
- [ ] 🐛 Bug fix
- [ ] ✨ Nouvelle fonctionnalité
- [ ] ♻️ Refactoring
- [ ] 📚 Documentation
- [ ] 🔧 Configuration / DevOps

## ✅ Checklist de review

### Code
- [ ] Le code suit les conventions du projet (architecture en couches)
- [ ] Pas de code mort ou commenté inutilement
- [ ] Les noms de variables/méthodes sont clairs et en français/anglais cohérent

### Tests
- [ ] Les tests existants passent (`mvn test` ou `npm test`)
- [ ] Des tests ont été ajoutés pour les nouvelles fonctionnalités

### API
- [ ] La documentation Swagger est à jour
- [ ] Les codes HTTP retournés sont corrects (201 pour create, 204 pour delete, etc.)
- [ ] La gestion des erreurs est correcte

### Docker
- [ ] Le `docker-compose.yml` est mis à jour si nécessaire
- [ ] L'image Docker se build sans erreur

### Documentation
- [ ] Le `README.md` est mis à jour si nécessaire

## 📷 Captures d'écran (si applicable)
<!-- Ajoutez des captures pour les changements d'UI -->

## 💬 Notes pour le reviewer
<!-- Tout ce que le reviewer doit savoir avant de relire -->
