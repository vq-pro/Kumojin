# Kumojin

## Main page

Start WebApplication, and then go to http://localhost:8080/index.html

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

* For persistence, integrated the H2 memory database, also for simplicity. This way, it will run on any local
  configuration, without the need for a more complex Docker container (or the like). Since the app is JPA, it would be a
  cinch to convert it to use a PostgreSL instance, just a matter of datasource definition.

* Start and end event times will be kept in the database as a "TIMESTAMP". The timezone, in format "-05:00" will be
  stored in the DB. When timestamps are received by the backend REST server, it will convert timestamps to the server's
  local timezone. When the REST server give these back to the frontend (through the GET list method), they will be
  converted back into the actual timezones.
