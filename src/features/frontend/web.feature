Feature: Web

  Scenario: Display list
    Given these predefined items:
      | StarShip |
      | Dragon   |
    When we enter the application
    Then we see this list:
      | Dragon   |
      | StarShip |
