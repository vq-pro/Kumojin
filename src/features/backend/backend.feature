Feature: Backend

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
