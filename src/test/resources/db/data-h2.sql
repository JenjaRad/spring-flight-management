INSERT INTO flight_schema.air_company (id, name, company_type, founded_at)
values (1, 'London Airways', 'Logistic', 1927);

INSERT INTO flight_schema.air_company (id, name, company_type, founded_at)
values (2, 'Qatar Airways', 'Logistic', 1932);

INSERT INTO flight_schema.air_company (id, name, company_type, founded_at)
values (3, 'UA Flight', 'Logistic', 1943);

INSERT INTO flight_schema.air_company (id, name, company_type, founded_at)
values (4, 'RU Flight', 'Logistic', 1973);

INSERT INTO flight_schema.airplane (id, created_at, name, serial_number, number_of_flights, fuel_capacity,
                                    flight_distance, type, air_company_id)
values (1, now(), 'T20', 72191204, 2, 32, 460, 'Iron', 1);

INSERT INTO flight_schema.airplane (id, created_at, name, serial_number, number_of_flights, fuel_capacity,
                                    flight_distance, type, air_company_id)
values (2, now(), 'T24', 48342104, 1, 48, 12200, 'Iron', 2);

INSERT INTO flight_schema.airplane (id, created_at, name, serial_number, number_of_flights, fuel_capacity,
                                    flight_distance, type, air_company_id)
values (3, now(), 'T100', 34851635, 4, 121, 102100, 'Iron', 1);

INSERT INTO flight_schema.flight (id, created_at, status, air_company_id, airplane_id, departure_country,
                                  destination_country, distance, estimated_date_type, ended_at, delay_at)
values (1, now(), 'PENDING', 1, 2, 'Ukraine', 'Austria', 1021, '2022-09-02 04:11:05', '2022-09-02 04:05:06',
        '2022-09-02 04:10:06');

INSERT INTO flight_schema.flight (id, created_at, status, air_company_id, airplane_id, departure_country,
                                  destination_country, distance, estimated_date_type, ended_at, delay_at)
values (2, now(), 'COMPLETED', 2, 1, 'Algeria', 'Australia', 3782, '2022-09-02 07:11:05',
        '2022-09-02 08:05:06',
        '2022-09-02 08:10:14');

INSERT INTO flight_schema.flight (id, created_at, status, air_company_id, airplane_id, departure_country,
                                  destination_country, distance, estimated_date_type, ended_at, delay_at)
values (3, now(), 'COMPLETED', 1, 2, 'Romania', 'Russia', 1089, '2022-09-02 11:12:05', '2022-09-02 11:13:03',
        null);
