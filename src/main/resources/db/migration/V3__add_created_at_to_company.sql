ALTER TABLE flight_schema.air_company
    ADD column created_at TIMESTAMP without time zone NOT NULL default now();