-- ── Tabla de lugares ────────────────────────────────────────────
CREATE TABLE venues (
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(255) NOT NULL,
    address  VARCHAR(255) NOT NULL,
    city     VARCHAR(255) NOT NULL,
    capacity INT          NOT NULL
);

-- ── Tabla de categorías ─────────────────────────────────────────
CREATE TABLE categories (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL UNIQUE,
    description TEXT
);

-- ── Tabla de eventos ────────────────────────────────────────────
CREATE TABLE events (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description TEXT         NOT NULL,
    event_date  TIMESTAMP    NOT NULL,
    active      BOOLEAN      NOT NULL DEFAULT TRUE,
    -- Llave foránea obligatoria hacia venues
    venue_id    BIGINT       NOT NULL,
    CONSTRAINT fk_event_venue FOREIGN KEY (venue_id) REFERENCES venues(id)
);

-- ── Tabla intermedia eventos-categorías ─────────────────────────
CREATE TABLE events_categories (
    event_id    BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    -- Clave primaria compuesta — evita duplicados en la relación
    PRIMARY KEY (event_id, category_id),
    CONSTRAINT fk_ec_event    FOREIGN KEY (event_id)    REFERENCES events(id),
    CONSTRAINT fk_ec_category FOREIGN KEY (category_id) REFERENCES categories(id)
);