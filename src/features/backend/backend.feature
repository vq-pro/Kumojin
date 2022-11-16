Feature: Backend

  Scenario: Get list of items
    Given these predefined items:
      | StarShip |
      | Dragon   |
    When we ask for the list
    Then we get this:
      | Dragon   |
      | StarShip |
