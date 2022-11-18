Feature: Web

  Scenario: Display event list on entering application
    Given these predefined events:
      | name    | description        |
      | Wedding | Wedding in Paris.  |
      | Funeral | Funeral in London. |
    When we enter the application
    Then we see this event list:
      | name    | description        |
      | Funeral | Funeral in London. |
      | Wedding | Wedding in Paris.  |

  Scenario: Add an event
    Given these predefined events:
      | name    | description        |
      | Wedding | Wedding in Paris.  |
      | Funeral | Funeral in London. |
    And we enter the application
    When we add this event:
      | name                  | description                                |
      | Dinner (or something) | Not sure whether dinner is right for this. |
    Then we see this event list:
      | name                  | description                                |
      | Dinner (or something) | Not sure whether dinner is right for this. |
      | Funeral               | Funeral in London.                         |
      | Wedding               | Wedding in Paris.                          |

  Scenario Outline: Add an event - ERROR - <error message>
    Given these predefined events:
      | name    | description        |
      | Funeral | Funeral in London. |
    And we enter the application
    When we add this event:
      | name   | description   |
      | <name> | <description> |
    Then we see this error message: "<error message>"
    Examples:
      | name           | description             | error message               |
      | Funeral        | Some other description. | Duplicate event             |
      |                | No name.                | Invalid event               |
      | No description |                         | Invalid event               |
      | error 204      | -                       | Internal server error (204) |
      | error 500      | -                       | Internal server error (500) |

  Scenario: Add an event - ERROR - Error disappears after successful add
    Given we enter the application
    And we add this event:
      | name | description |
      |      |             |
    And we see this error message: "Invalid event"
    When we add this event:
      | name | description |
      |      |             |
    When we add this event:
      | name   | description        |
      | Dinner | Dinner, all right! |
    Then we don't see an error message
    Then we see this event list:
      | name   | description        |
      | Dinner | Dinner, all right! |
