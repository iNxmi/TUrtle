CREATE TABLE users
(
    id            BIGSERIAL PRIMARY KEY,
    username      VARCHAR(255) UNIQUE NOT NULL,
    first_name    VARCHAR(255),
    last_name     VARCHAR(255),
    email         VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255)        NOT NULL,
    emojis        VARCHAR(255) UNIQUE,
    status        VARCHAR(50)         NOT NULL,
    created_at    TIMESTAMP           NOT NULL DEFAULT now(),
    updated_at    TIMESTAMP           NOT NULL DEFAULT now()
);

CREATE TABLE tokens
(
    id         BIGSERIAL PRIMARY KEY,
    uuid       VARCHAR(255) UNIQUE NOT NULL,
    duration   NUMERIC(21, 0)      NOT NULL,
    type       VARCHAR(50)         NOT NULL,
    created_at TIMESTAMP           NOT NULL DEFAULT now(),
    updated_at TIMESTAMP           NOT NULL DEFAULT now()
);

CREATE TABLE user_tokens
(
    user_id  BIGINT NOT NULL REFERENCES users (id),
    token_id BIGINT NOT NULL REFERENCES tokens (id),
    PRIMARY KEY (user_id, token_id)
);

CREATE TABLE roles
(
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(255) UNIQUE NOT NULL,
    type       VARCHAR(50) UNIQUE,
    created_at TIMESTAMP           NOT NULL DEFAULT now(),
    updated_at TIMESTAMP           NOT NULL DEFAULT now()
);

CREATE TABLE role_permissions
(
    role_id    BIGINT       NOT NULL REFERENCES roles (id),
    permission VARCHAR(255) NOT NULL,
    PRIMARY KEY (role_id, permission)
);

CREATE TABLE user_roles
(
    user_id BIGINT NOT NULL REFERENCES users (id),
    role_id BIGINT NOT NULL REFERENCES roles (id),
    PRIMARY KEY (user_id, role_id)
);

CREATE TABLE room_bookings
(
    id            BIGSERIAL PRIMARY KEY,
    user_id       BIGINT       NOT NULL REFERENCES users (id),
    title         VARCHAR(255) NOT NULL,
    start_time    TIMESTAMP    NOT NULL,
    end_time      TIMESTAMP    NOT NULL,
    description   TEXT,
    accessibility VARCHAR(50)           DEFAULT 'LOCKED',
    status        VARCHAR(50)  NOT NULL,
    created_at    TIMESTAMP    NOT NULL DEFAULT now(),
    updated_at    TIMESTAMP    NOT NULL DEFAULT now()
);

CREATE TABLE room_bookings_whitelisted_users
(
    room_booking_id BIGINT NOT NULL REFERENCES room_bookings (id),
    user_id         BIGINT NOT NULL REFERENCES users (id),
    PRIMARY KEY (room_booking_id, user_id)
);

CREATE TABLE lockers
(
    id                     BIGSERIAL PRIMARY KEY,
    name                   VARCHAR(255) UNIQUE NOT NULL,
    index                  INT UNIQUE          NOT NULL,
    is_software_unlockable BOOLEAN             NOT NULL,
    locked                 BOOLEAN             NOT NULL,
    created_at             TIMESTAMP           NOT NULL DEFAULT now(),
    updated_at             TIMESTAMP           NOT NULL DEFAULT now()
);

CREATE TABLE item_categories
(
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(255) UNIQUE NOT NULL,
    created_at TIMESTAMP           NOT NULL DEFAULT now(),
    updated_at TIMESTAMP           NOT NULL DEFAULT now()
);

CREATE TABLE items
(
    id                 BIGSERIAL PRIMARY KEY,
    name               VARCHAR(255) UNIQUE NOT NULL,
    description        TEXT,
    category_id        BIGINT REFERENCES item_categories (id),
    locker_id          BIGINT REFERENCES lockers (id),
    needs_confirmation BOOLEAN             NOT NULL,
    acquired_at        TIMESTAMP           NOT NULL,
    created_at         TIMESTAMP           NOT NULL DEFAULT now(),
    updated_at         TIMESTAMP           NOT NULL DEFAULT now()
);

CREATE TABLE item_bookings
(
    id           BIGSERIAL PRIMARY KEY,
    user_id      BIGINT      NOT NULL REFERENCES users (id),
    item_id      BIGINT      NOT NULL REFERENCES items (id),
    start_time   TIMESTAMP   NOT NULL,
    end_time     TIMESTAMP   NOT NULL,
    collected_at TIMESTAMP,
    returned_at  TIMESTAMP,
    status       VARCHAR(50) NOT NULL,
    created_at   TIMESTAMP   NOT NULL DEFAULT now(),
    updated_at   TIMESTAMP   NOT NULL DEFAULT now()
);

CREATE TABLE support_tickets
(
    id          BIGSERIAL PRIMARY KEY,
    urgency     VARCHAR(50)  NOT NULL,
    category    VARCHAR(50)  NOT NULL,
    email       VARCHAR(255) NOT NULL,
    subject     VARCHAR(255) NOT NULL,
    description TEXT,
    status      VARCHAR(50)  NOT NULL,
    created_at  TIMESTAMP    NOT NULL DEFAULT now(),
    updated_at  TIMESTAMP    NOT NULL DEFAULT now()
);

CREATE TABLE faq
(
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(255) UNIQUE NOT NULL,
    title      VARCHAR(255),
    content    TEXT,
    enabled    BOOLEAN             NOT NULL,
    created_at TIMESTAMP           NOT NULL DEFAULT now(),
    updated_at TIMESTAMP           NOT NULL DEFAULT now()
);

CREATE TABLE exceptions
(
    id          BIGSERIAL PRIMARY KEY,
    endpoint    VARCHAR(255),
    exception   VARCHAR(255),
    message     TEXT,
    stack_trace TEXT,
    created_at  TIMESTAMP NOT NULL DEFAULT now(),
    updated_at  TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE email_templates
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255) UNIQUE NOT NULL,
    description TEXT,
    subject     VARCHAR(255),
    content     TEXT,
    type        VARCHAR(50) UNIQUE,
    created_at  TIMESTAMP           NOT NULL DEFAULT now(),
    updated_at  TIMESTAMP           NOT NULL DEFAULT now()
);

CREATE TABLE content_templates
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255) UNIQUE NOT NULL,
    description TEXT,
    content     TEXT,
    type        VARCHAR(50) UNIQUE,
    created_at  TIMESTAMP           NOT NULL DEFAULT now(),
    updated_at  TIMESTAMP           NOT NULL DEFAULT now()
);

CREATE TABLE configuration
(
    id         BIGSERIAL PRIMARY KEY,
    key        VARCHAR(255) UNIQUE NOT NULL,
    type       VARCHAR(50)         NOT NULL,
    value      TEXT,
    visibility VARCHAR(50)         NOT NULL,
    created_at TIMESTAMP           NOT NULL DEFAULT now(),
    updated_at TIMESTAMP           NOT NULL DEFAULT now()
);

CREATE SEQUENCE audit_logs_seq START 1;
CREATE TABLE audit_logs
(
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGINT REFERENCES users (id),
    ip_address  VARCHAR(255),
    endpoint    VARCHAR(255),
    status      INT,
    http_method VARCHAR(10),
    created_at  TIMESTAMP NOT NULL DEFAULT now(),
    updated_at  TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE statistic_queries
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255),
    description TEXT,
    query       TEXT,
    created_at  TIMESTAMP NOT NULL DEFAULT now(),
    updated_at  TIMESTAMP NOT NULL DEFAULT now()
);