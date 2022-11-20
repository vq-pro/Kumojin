Feature: Backend

  Scenario: Add an event
    Given these predefined events:
      | name    | description          | timezone | start            | end              |
      | Wedding | Wedding in Paris.    | +01:00   | 2022-12-25 12:00 | 2022-12-27 23:00 |
      | Funeral | Funeral in Istanbul. | +03:00   | 2022-12-18 17:00 | 2022-12-18 20:00 |
    When we POST this event successfully:
      | name                  | description            | timezone | start            | end              |
      | Dinner (or something) | Not sure about dinner. | +00:00   | 2022-12-26 19:30 | 2022-12-26 22:00 |
    When we GET the event list
    Then we receive this:
      | name                  | description            | start                   | end                     |
      | Dinner (or something) | Not sure about dinner. | 2022-12-26 19:30 +00:00 | 2022-12-26 22:00 +00:00 |
      | Funeral               | Funeral in Istanbul.   | 2022-12-18 17:00 +03:00 | 2022-12-18 20:00 +03:00 |
      | Wedding               | Wedding in Paris.      | 2022-12-25 12:00 +01:00 | 2022-12-27 23:00 +01:00 |

  Scenario Outline: Add an event - ERROR - [name=<name>]
    Given these predefined events:
      | name    | description          | timezone | start            | end              |
      | Funeral | Funeral in Istanbul. | +03:00   | 2022-12-18 17:00 | 2022-12-18 20:00 |
    When we POST this event:
      | name   | description   | timezone   | start   | end   |
      | <name> | <description> | <timezone> | <start> | <end> |
    Then we receive a <code> error
    Examples:
      | code | name              | description    | timezone | start            | end              |
      | 400  |                   | Empty name     | +01:00   | 2022-12-26 14:30 | 2022-12-27 09:00 |
      | 400  | Empty description |                | +01:00   | 2022-12-26 14:30 | 2022-12-27 09:00 |
      | 400  | Empty timezone    | -              |          | 2022-12-26 14:30 | 2022-12-27 09:00 |
      | 400  | Empty start       | -              | +01:00   |                  | 2022-12-27 09:00 |
      | 400  | Empty end         | -              | +01:00   | 2022-12-26 14:30 |                  |
      | 400  | Bad timezone      | -              | +0x:00   | 2022-12-26 14:30 | 2022-12-27 09:00 |
      | 400  | Bad timezone      | -              | 01:00    | 2022-12-26 14:30 | 2022-12-27 09:00 |
      | 400  | Bad timezone      | -              | +01x00   | 2022-12-26 14:30 | 2022-12-27 09:00 |
      | 400  | Bad start         | -              | +01:00   | 2022-12-x6 14:30 | 2022-12-27 09:00 |
      | 400  | Bad end           | -              | +01:00   | 2022-12-26 14:30 | 2022-12-27 09x00 |
      | 400  |                   |                |          |                  |                  |
      | 409  | Funeral           | Duplicate name | +01:00   | 2022-12-26 14:30 | 2022-12-27 09:00 |
      | 418  | error 418         | -              | -01:00   | 2022-12-26 14:30 | 2022-12-27 09:00 |
      | 500  | error 500         | -              | +01:00   | 2022-12-26 14:30 | 2022-12-27 09:00 |

  Scenario: Get list of events [when not empty]
    Given these predefined events:
      | name     | description                | timezone | start            | end              |
      | Wedding  | Wedding in Paris.          | +01:00   | 2022-12-25 12:00 | 2022-12-27 23:00 |
      | Aventure | Jazz Festival in Montreal. | -05:00   | 2023-06-30 18:00 | 2023-07-07 09:00 |
      | Funeral  | Funeral in Istanbul.       | +03:00   | 2022-12-18 17:00 | 2022-12-18 20:00 |
    When we GET the event list
    Then we receive this:
      | name     | description                | start                   | end                     |
      | Aventure | Jazz Festival in Montreal. | 2023-06-30 18:00 -05:00 | 2023-07-07 09:00 -05:00 |
      | Funeral  | Funeral in Istanbul.       | 2022-12-18 17:00 +03:00 | 2022-12-18 20:00 +03:00 |
      | Wedding  | Wedding in Paris.          | 2022-12-25 12:00 +01:00 | 2022-12-27 23:00 +01:00 |

  Scenario: Get list of events [when empty]
  Returns an empty list (not 204 or 404).
    When we GET the event list
    Then we receive an empty list
