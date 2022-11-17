Feature: Web

  Scenario: Display list on entering application
    Given these predefined items:
      | StarShip |
      | Dragon   |
    When we enter the application
    Then we see this list:
      | Dragon   |
      | StarShip |

  Scenario: Add an item
    Given these predefined items:
      | StarShip |
      | Dragon   |
    And we enter the application
    When we add "Firefly (fiction)"
    Then we see this list:
      | Dragon            |
      | Firefly (fiction) |
      | StarShip          |
