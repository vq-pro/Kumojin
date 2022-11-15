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
