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

  Scenario Outline: Add an item - ERROR - <error message>
    Given these predefined items:
      | Dragon |
    And we enter the application
    When we add "<item>"
    Then we see this error message: "<error message>"
    Examples:
      | item      | error message               |
      | Dragon    | Duplicate item              |
      | error 204 | Internal server error (204) |
      | error 500 | Internal server error (500) |
      |           | Invalid item                |

  Scenario: Add an item - ERROR - Error disappears after successful add
    Given we enter the application
    And we add ""
    And we see this error message: "Invalid item"
    When we add "Dragon"
    Then we don't see an error message
    And we see this list:
      | Dragon |
