Feature: Web

  Scenario: Display event list on entering application
    Given these predefined events:
      | name     | description                | timezone | start            | end              |
      | Wedding  | Wedding in Paris.          | +01:00   | 2022-12-25 12:00 | 2022-12-27 23:00 |
      | Festival | Jazz Festival in Montreal. | -05:00   | 2023-06-30 18:00 | 2023-07-07 09:00 |
      | Funeral  | Funeral in Istanbul.       | +03:00   | 2022-12-18 17:00 | 2022-12-18 20:00 |
    When we enter the application
    Then we see this event list:
      | name     | description                | start                   | end                     |
      | Festival | Jazz Festival in Montreal. | 2023-06-30 18:00 -05:00 | 2023-07-07 09:00 -05:00 |
      | Funeral  | Funeral in Istanbul.       | 2022-12-18 17:00 +03:00 | 2022-12-18 20:00 +03:00 |
      | Wedding  | Wedding in Paris.          | 2022-12-25 12:00 +01:00 | 2022-12-27 23:00 +01:00 |

  Scenario: Add an event
    Given these predefined events:
      | name    | description          | timezone | start            | end              |
      | Wedding | Wedding in Paris.    | +01:00   | 2022-12-25 12:00 | 2022-12-27 23:00 |
      | Funeral | Funeral in Istanbul. | +03:00   | 2022-12-18 17:00 | 2022-12-18 20:00 |
    And we enter the application
    When we add this event:
      | name                  | description            | timezone | start            | end              |
      | Dinner (or something) | Not sure about dinner. | London   | 2022-12-26 19:30 | 2022-12-26 22:00 |
    Then the add form is cleared
    And we see this event list:
      | name                  | description            | start                   | end                     |
      | Dinner (or something) | Not sure about dinner. | 2022-12-26 19:30 +00:00 | 2022-12-26 22:00 +00:00 |
      | Funeral               | Funeral in Istanbul.   | 2022-12-18 17:00 +03:00 | 2022-12-18 20:00 +03:00 |
      | Wedding               | Wedding in Paris.      | 2022-12-25 12:00 +01:00 | 2022-12-27 23:00 +01:00 |

  Scenario Outline: Add an event - ERROR - <error message> [name=<name>]
    Given these predefined events:
      | name    | description          | timezone | start            | end              |
      | Wedding | Wedding in Paris.    | +01:00   | 2022-12-25 12:00 | 2022-12-27 23:00 |
      | Funeral | Funeral in Istanbul. | +03:00   | 2022-12-18 17:00 | 2022-12-18 20:00 |
    And we enter the application
    When we add this event:
      | name   | description   | timezone   | start   | end   |
      | <name> | <description> | <timezone> | <start> | <end> |
    Then the add form is not cleared
    And we see this error message: "<error message>"
    Examples:
      | name           | description          | timezone | start            | end              | error message               |
      | Funeral        | Funeral in Istanbul. | Istanbul | 2022-12-18 17:00 | 2022-12-18 20:00 | Duplicate event             |
      |                | No name              | Paris    | 2022-12-25 12:00 | 2022-12-27 23:00 | Invalid event               |
      | No description |                      | Paris    | 2022-12-25 12:00 | 2022-12-27 23:00 | Invalid event               |
      | No timezone    | Funeral in Istanbul. |          | 2022-12-25 12:00 | 2022-12-27 23:00 | Invalid event               |
      | No start       | Funeral in Istanbul. | Paris    |                  | 2022-12-27 23:00 | Invalid event               |
      | No end         | Funeral in Istanbul. | Paris    | 2022-12-25 12:00 |                  | Invalid event               |
      | error 204      | -                    | Paris    | -                | -                | Internal server error (204) |
      | error 500      | -                    | Paris    | -                | -                | Internal server error (500) |

  @Ignore
  Scenario: Add an event - ERROR - Error disappears after successful add
    Given we enter the application
    And we add this event:
      | name   | description | timezone | start            |
      | Dinner |             | Paris    | 2022-12-26 14:30 |
    And we see this error message: "Invalid event"
    When we add this event:
      | name   | description        | timezone | start            |
      | Dinner | Dinner, all right! | Paris    | 2022-12-26 14:30 |
    Then we don't see an error message
    Then we see this event list:
      | name   | description        | start                   |
      | Dinner | Dinner, all right! | 2022-12-26 14:30 +01:00 |
