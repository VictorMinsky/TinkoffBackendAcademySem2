--liquibase formatted sql

--changeset VictorMinsky:2
CREATE TABLE IF NOT EXISTS user_types
(
    id   SERIAL PRIMARY KEY,
    type TEXT NOT NULL
);

INSERT INTO user_types(type)
VALUES ('rancher'),
       ('handyman');

ALTER TABLE users
    ADD COLUMN user_type_id INT NOT NULL REFERENCES user_types (id) DEFAULT 0;

UPDATE users
SET user_type_id = (SELECT id
                    FROM user_types
                    WHERE user_types.type = users.user_type);

ALTER TABLE users
    DROP COLUMN user_type;