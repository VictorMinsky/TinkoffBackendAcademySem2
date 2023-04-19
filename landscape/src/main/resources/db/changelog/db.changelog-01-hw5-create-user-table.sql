--liquibase formatted sql

--changeset VictorMinsky:1
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS users
(
    id              uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    user_type       TEXT                        NOT NULL,
    login           TEXT                        NOT NULL,
    email           TEXT                        NOT NULL,
    phone_number    TEXT                        NOT NULL,
    created_at      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    last_updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL
);