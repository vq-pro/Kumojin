# Kumojin

## Main page

Start [Application](src/main/java/quebec/virtualite/kumojin/Application.java), and then go
to http://localhost:8080/index.html.

Here's the source for [index.html](src/main/resources/static/index.html).

## Features

[Web scenarios](src/features/frontend/web.feature)

[Backend scenarios](src/features/backend/backend.feature)

## Run Configurations

Located in .run directory.

## Notes

* I initially setup a multi-module project, to keep the frontend and backend separate. But this proved a hassle trying
  to start/stop the backend process from the frontend automatically. For simplicity's sake, I set it up as a single
  project and a single Spring Boot application. This way the embedded Tomcat instance will serve both sides.

* In true BDD/TDD fashion, I have selected to not use any JS framework, to deliver the simplest implementation that
  respects the present specifications. So straight
  JavaScript was used.

* Keeping things simple on the frontend allowed me to focus on the tests and the backend code. I chose not to implement
  some cosmetic niceties, like the Add button to disable/enable itself based on form validation (the backend validates
  instead), or a label that says something like "No events" when the list is empty.

* Also took a few shortcuts with the date display and input. As of now, it's the backend that supplies the dates in
  string representation. This is not ideal: normally the frontend would receive it in plain ISO format, and then display
  it according to a given format (locale-specific). And there could be a simpler interface to select the date on the add
  form (i.e. a popup calendar). But again, I decided to take a few shortcuts to avoid working on this for too long.

* For persistence, integrated the H2 memory database, also for simplicity. This way, it will run on any local
  configuration, without the need for a more complex Docker container (or the like). Since the app is JPA, it would be a
  cinch to convert it to use a PostgreSQL instance, just a matter of the datasource definition.

* Start and end event times will be kept in the database as a "TIMESTAMP". The timezone, in format "-05:00" will also be
  stored in the DB. When timestamps are received by the backend REST server, it will convert them to the server's
  local timezone for persistence. When the REST server gives these back to the frontend (through the GET list method),
  they will be converted back into the actual timezones.

* Easy to add new cities/timezones. They are specified right in the HTML page.