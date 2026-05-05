# Q3 — Test BDD Gherkin avec Cucumber
Feature: Calcul de l'âge d'un étudiant

  Scenario: Étudiant né il y a environ 23 ans
    Given un étudiant avec la date de naissance "2002-04-07"
    When on calcule son âge
    Then l'âge retourné doit être 23

  Scenario: Étudiant né il y a environ 24 ans
    Given un étudiant avec la date de naissance "2001-03-15"
    When on calcule son âge
    Then l'âge retourné doit être 25

  Scenario: Étudiant né il y a environ 20 ans
    Given un étudiant avec la date de naissance "2004-12-01"
    When on calcule son âge
    Then l'âge retourné doit être 21
