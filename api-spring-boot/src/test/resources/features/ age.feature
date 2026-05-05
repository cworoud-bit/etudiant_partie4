Feature: Gestion des étudiants

  # ===== SCÉNARIOS EXISTANTS POUR L'ÂGE =====
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

  # ===== NOUVEAUX SCÉNARIOS POUR LE FILTRAGE =====
Feature: Filtrage des étudiants par année d'inscription

  Scenario: Filtrer les étudiants de l'année 2019
    Given des étudiants existants dans la base
    When je filtre les étudiants par année d'inscription "2019"
    Then je vois seulement les étudiants inscrits en 2019
    And le nombre d'étudiants affichés est 2
    And les étudiants sont "Ali Ben Salah" et "Mohamed Khelil"

  Scenario: Filtrer les étudiants de l'année 2020
    Given des étudiants existants dans la base
    When je filtre les étudiants par année d'inscription "2020"
    Then je vois seulement les étudiants inscrits en 2020
    And le nombre d'étudiants affichés est 2
    And les étudiants sont "Fatma Trabelsi" et "Youssef Mansouri"

  Scenario: Filtrer les étudiants de l'année 2021
    Given des étudiants existants dans la base
    When je filtre les étudiants par année d'inscription "2021"
    Then je vois seulement les étudiants inscrits en 2021
    And le nombre d'étudiants affichés est 1
    And l'étudiant est "Rim Gharbi"

  Scenario: Filtrer une année sans étudiants
    Given des étudiants existants dans la base
    When je filtre les étudiants par année d'inscription "2025"
    Then aucun étudiant n'est affiché
    And le message "Aucun étudiant trouvé" s'affiche