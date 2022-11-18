Feature: Backend

  Scenario: Add an event
    Given these predefined events:
      | name    | description        |
      | Wedding | Wedding in Paris.  |
      | Funeral | Funeral in London. |
    When we POST this event successfully:
      | name                  | description                                |
      | Dinner (or something) | Not sure whether dinner is right for this. |
    When we GET the event list
    Then we receive this:
      | name                  | description                                |
      | Dinner (or something) | Not sure whether dinner is right for this. |
      | Funeral               | Funeral in London.                         |
      | Wedding               | Wedding in Paris.                          |

  Scenario Outline: Add an event - ERROR - <error case>
    Given these predefined events:
      | name    | description        |
      | Funeral | Funeral in London. |
    When we POST this event:
      | name   | description   |
      | <name> | <description> |
    Then we receive a <code> error
    Examples:
      | error case          | name      | description       | code |
      | Duplicate name      | Funeral   | Some description. | 409  |
      | Empty name          |           | Some description. | 400  |
      | Empty description   | Funeral   |                   | 400  |
      | Empty everything    |           |                   | 400  |
      | Error 418 generator | error 418 | -                 | 418  |
      | Error 500 generator | error 500 | -                 | 500  |

  Scenario: Get list of events [when not empty]
    Given these predefined events:
      | name    | description        |
      | Wedding | Wedding in Paris.  |
      | Funeral | Funeral in London. |
    When we GET the event list
    Then we receive this:
      | name    | description        |
      | Funeral | Funeral in London. |
      | Wedding | Wedding in Paris.  |

  Scenario: Get list of events [when empty]
  Returns an empty list (not 204 or 404).
    When we GET the event list
    Then we receive an empty list
