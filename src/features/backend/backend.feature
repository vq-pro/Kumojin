Feature: Backend

  Scenario: Add an event
    Given these predefined events:
      | name    | description          | timezone | start            |
      | Wedding | Wedding in Paris.    | +01:00   | 2022-12-25 12:00 |
      | Funeral | Funeral in Istanbul. | +03:00   | 2022-12-18 17:00 |
    When we POST this event successfully:
      | name                  | description            | timezone | start            |
      | Dinner (or something) | Not sure about dinner. | +00:00   | 2022-12-26 14:30 |
    When we GET the event list
    Then we receive this:
      | name                  | description            | start                   |
      | Dinner (or something) | Not sure about dinner. | 2022-12-26 14:30 Z      |
      | Funeral               | Funeral in Istanbul.   | 2022-12-18 17:00 +03:00 |
      | Wedding               | Wedding in Paris.      | 2022-12-25 12:00 +01:00 |

  @Ignore
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
      | name     | description                | timezone | start            |
      | Wedding  | Wedding in Paris.          | +01:00   | 2022-12-25 12:00 |
      | Aventure | Jazz Festival in Montreal. | -05:00   | 2023-06-30 18:00 |
      | Funeral  | Funeral in Istanbul.       | +03:00   | 2022-12-18 17:00 |
    When we GET the event list
    Then we receive this:
      | name     | description                | start                   |
      | Aventure | Jazz Festival in Montreal. | 2023-06-30 18:00 -05:00 |
      | Funeral  | Funeral in Istanbul.       | 2022-12-18 17:00 +03:00 |
      | Wedding  | Wedding in Paris.          | 2022-12-25 12:00 +01:00 |

  Scenario: Get list of events [when empty]
  Returns an empty list (not 204 or 404).
    When we GET the event list
    Then we receive an empty list
