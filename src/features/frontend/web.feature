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

  Scenario: Add an item - ERROR - Duplicate item
    Given these predefined items:
      | Dragon |
    And we enter the application
    When we add "Dragon"
    Then we see this error message: "Duplicate item"

#  FIXME-1 Add an item - ERROR - Error disappears after successful add
#  FIXME-1 Add an item - ERROR - Other error

  Scenario: Add an item - ERROR - Invalid item
    Given we enter the application
    When we add ""
    Then we see this error message: "Invalid item"
