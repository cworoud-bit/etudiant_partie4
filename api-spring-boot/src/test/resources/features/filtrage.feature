# src/test/resources/features/filtrage.feature

Feature: Filtrage des étudiants par année d'inscription

  Background:
    Given les étudiants suivants existent dans la base:
      | Nom              | CIN     | Date naissance | Année inscription | Département   |
      | Ali Ben Salah    | 12345678| 2001-03-15     | 2019              | Informatique  |
      | Fatma Trabelsi   | 23456789| 2002-07-22     | 2020              | Gestion       |
      | Mohamed Khelil   | 34567890| 2000-11-08     | 2019              | Mathématiques |
      | Rim Gharbi       | 45678901| 2003-01-30     | 2021              | Informatique  |
      | Youssef Mansouri | 56789012| 2001-09-05     | 2020              | Gestion       |

  Scenario: Filtrer les étudiants de l'année 2019
    When je filtre les étudiants par année d'inscription "2019"
    Then je reçois 2 étudiants
    And les noms des étudiants sont "Ali Ben Salah" et "Mohamed Khelil"
    And tous les étudiants ont l'année d'inscription 2019

  Scenario: Filtrer les étudiants de l'année 2020
    When je filtre les étudiants par année d'inscription "2020"
    Then je reçois 2 étudiants
    And les noms des étudiants sont "Fatma Trabelsi" et "Youssef Mansouri"
    And tous les étudiants ont l'année d'inscription 2020

  Scenario: Filtrer les étudiants de l'année 2021
    When je filtre les étudiants par année d'inscription "2021"
    Then je reçois 1 étudiant
    And le nom de l'étudiant est "Rim Gharbi"
    And l'étudiant a l'année d'inscription 2021

  Scenario: Filtrer une année sans étudiants
    When je filtre les étudiants par année d'inscription "2025"
    Then je reçois 0 étudiant
    And un message "Aucun étudiant trouvé pour l'année 2025" est retourné