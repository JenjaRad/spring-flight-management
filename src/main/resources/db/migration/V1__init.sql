CREATE SCHEMA IF NOT EXISTS flight_schema;

CREATE TABLE flight_schema.air_company
(
    id           BIGINT NOT NULL,
    name         VARCHAR(255),
    company_type VARCHAR(255),
    founded_at   INTEGER,
    CONSTRAINT pk_air_company PRIMARY KEY (id)
);

CREATE TABLE flight_schema.airplane
(
    id                BIGINT                      NOT NULL,
    created_at        TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    name              VARCHAR(255),
    serial_number     BIGINT,
    number_of_flights INTEGER,
    fuel_capacity     BIGINT,
    flight_distance   INTEGER,
    type              VARCHAR(255),
    CONSTRAINT pk_airplane PRIMARY KEY (id)
);

CREATE TABLE flight_schema.flight
(
    id                  BIGINT                      NOT NULL,
    created_at          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    status              VARCHAR(255),
    air_company_id      BIGINT,
    airplane_id         BIGINT,
    departure_country   VARCHAR(255),
    destination_country VARCHAR(255),
    distance            INTEGER,
    estimated_date_type TIMESTAMP WITHOUT TIME ZONE,
    ended_at            TIMESTAMP WITHOUT TIME ZONE,
    delay_at            TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_flight PRIMARY KEY (id)
);

ALTER TABLE flight_schema.flight
    ADD CONSTRAINT FK_AIRPLANE FOREIGN KEY (airplane_id) REFERENCES flight_schema.airplane (id);

ALTER TABLE flight_schema.flight
    ADD CONSTRAINT FK_AIR_COMPANY FOREIGN KEY (air_company_id) REFERENCES flight_schema.air_company (id);

