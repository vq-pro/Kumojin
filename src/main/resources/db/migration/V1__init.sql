-- noinspection SqlDialectInspectionForFile
-- noinspection SqlNoDataSourceInspectionForFile

CREATE TABLE event
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(32) UNIQUE NOT NULL,

    UNIQUE (name)
);

CREATE SEQUENCE event_id_seq;
