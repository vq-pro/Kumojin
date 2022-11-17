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

  Scenario: Add an item - ERROR - Duplicate name
    Given these predefined items:
      | Dragon |
    When we add the item "Dragon"
    Then we get a 409 error

  Scenario: Add an item - ERROR - Empty name
    When we add the item with an empty name
    Then we get a 400 error

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
