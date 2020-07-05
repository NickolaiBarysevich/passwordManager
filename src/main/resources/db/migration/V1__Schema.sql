CREATE TABLE IF NOT EXISTS service_user (
    id SERIAL PRIMARY key,
    username VARCHAR(64) NOT NULL UNIQUE,
    password VARCHAR(256) NOT NULL,
    password_last_update_date DATE NOT NULL DEFAULT NOW(),
    email VARCHAR(128) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS credentials (
    id CHAR(36) PRIMARY KEY NOT NULL,
    title VARCHAR(32) NOT NULL,
    username VARCHAR(64),
    password VARCHAR(256),
    description VARCHAR(256),
    password_last_update_date DATE NOT NULL DEFAULT NOW(),
    client_id INT REFERENCES service_user
)
