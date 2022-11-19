-- noinspection SqlDialectInspectionForFile
-- noinspection SqlNoDataSourceInspectionForFile

CREATE TABLE event
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(32) UNIQUE NOT NULL,
    description VARCHAR(255)       NOT NULL,
    timezone    VARCHAR(6)         NOT NULL,
    event_start TIMESTAMP          NOT NULL,
    event_end   TIMESTAMP          NOT NULL
);

CREATE SEQUENCE event_id_seq;
