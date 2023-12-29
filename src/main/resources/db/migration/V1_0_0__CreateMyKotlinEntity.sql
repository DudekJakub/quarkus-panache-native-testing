CREATE SEQUENCE public.example_sq INCREMENT 1 START WITH 1 MINVALUE 1;

CREATE TABLE mykotlinentity
(
    id        BIGINT,
    firstname VARCHAR,
    surname   VARCHAR,
    PRIMARY KEY (id)
);
