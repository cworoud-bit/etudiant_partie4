/// <reference types="cypress" />

describe('Application Étudiants - Tests E2E', () => {

  beforeEach(() => {
    // Attendre que l'application soit chargée
    cy.visit('http://localhost:3000');
    // Ignorer les erreurs de fetch (CORS)
    cy.on('uncaught:exception', (err, runnable) => {
      console.log('Uncaught exception:', err.message);
      return false;
    });
  });

  describe('Page d\'accueil', () => {
    it('affiche le titre principal', () => {
      cy.contains('Bienvenue sur EtudiantsApp', { timeout: 10000 }).should('be.visible');
    });

    it('affiche les boutons de navigation', () => {
      cy.contains('Voir les Étudiants').should('be.visible');
    });
  });

  describe('Navigation vers la liste', () => {
    it('navigue vers /etudiants', () => {
      cy.contains('Voir les Étudiants').click();
      cy.url().should('include', '/etudiants');
      // Attendre que la page charge
      cy.wait(1000);
    });
  });

  describe('Liste des étudiants', () => {
    beforeEach(() => {
      cy.visit('http://localhost:3000/etudiants');
      cy.wait(2000); // Attendre le chargement
    });

    it('affiche le tableau des étudiants', () => {
      cy.get('table', { timeout: 10000 }).should('exist');
      cy.contains('Nom').should('exist');
    });
  });

  describe('Ajout d\'étudiant', () => {
    beforeEach(() => {
      cy.visit('http://localhost:3000/etudiants/nouveau');
      cy.wait(2000);
    });

    it('affiche le formulaire d\'ajout', () => {
      cy.get('input[name="nom"]', { timeout: 10000 }).should('exist');
      cy.get('input[name="cin"]').should('exist');
    });

    it('ajoute un nouvel étudiant', () => {
      const nomUnique = `Test_${Date.now()}`;

      cy.get('input[name="nom"]').type(nomUnique);
      cy.get('input[name="cin"]').type('99999999');
      cy.get('input[name="email"]').type('test@cypress.com');
      cy.get('input[name="dateNaissance"]').type('2000-01-01');
      cy.get('input[name="anneePremiereInscription"]').type('2024');

      cy.get('button[type="submit"]').click();

      // Vérifier redirection
      cy.url({ timeout: 5000 }).should('include', '/etudiants');
    });
  });
});