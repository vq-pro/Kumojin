Feature: Backend

  Scenario: Add an item
    Given these predefined items:
      | StarShip |
      | Dragon   |
    When we add the item "Firefly (fiction)" successfully
    And we ask for the list
    Then we get this:
      | Dragon            |
      | Firefly (fiction) |
      | StarShip          |

  Scenario Outline: Add an item - ERROR - <error case>
    Given these predefined items:
      | Dragon |
    When we add the item "<item>"
    Then we get a <code> error
    Examples:
      | error case          | item      | code |
      | Duplicate name      | Dragon    | 409  |
      | Empty name          |           | 400  |
      | Error 418 generator | error 418 | 418  |
      | Error 500 generator | error 500 | 500  |

  Scenario: Get list of items [when not empty]
    Given these predefined items:
      | StarShip |
      | Dragon   |
    When we ask for the list
    Then we get this:
      | Dragon   |
      | StarShip |

  Scenario: Get list of items [when empty]
  Returns an empty list (not 204 or 404).
    When we ask for the list
    Then we get an empty list
