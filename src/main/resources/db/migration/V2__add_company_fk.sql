ALTER TABLE flight_schema.airplane
    ADD COLUMN air_company_id BIGINT;

ALTER TABLE flight_schema.airplane
    ADD CONSTRAINT FK_AIR_COMPANY FOREIGN KEY (air_company_id) REFERENCES flight_schema.air_company (id);