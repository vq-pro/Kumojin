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

  Scenario Outline: Add an event - ERROR - [name=<name>]
    Given these predefined events:
      | name    | description          | timezone | start            |
      | Funeral | Funeral in Istanbul. | +03:00   | 2022-12-18 17:00 |
    When we POST this event:
      | name   | description   | timezone   | start   |
      | <name> | <description> | <timezone> | <start> |
    Then we receive a <code> error
    Examples:
      | name              | description       | timezone | start            | code |
      | Funeral           | Duplicate name    | +01:00   | 2022-12-26 14:30 | 409  |
      |                   | Empty name        | +01:00   | 2022-12-26 14:30 | 400  |
      | Empty description |                   | +01:00   | 2022-12-26 14:30 | 400  |
      | Empty timezone    | Some description. |          | 2022-12-26 14:30 | 400  |
      | Empty start       | Some description. | +01:00   |                  | 400  |
      |                   |                   |          |                  | 400  |
      | error 418         | -                 | -        | -                | 418  |
      | error 500         | -                 | -        | -                | 500  |

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
