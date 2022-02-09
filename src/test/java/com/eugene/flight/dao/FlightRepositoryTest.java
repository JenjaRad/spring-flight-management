package com.eugene.flight.dao;

import com.eugene.flight.domain.Flight;
import com.eugene.flight.domain.FlightStatus;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
@Sql(value = {"/db/data-h2.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class FlightRepositoryTest {

    @Autowired
    private FlightRepository flightRepository;

    @Test
    @Order(1)
    void testFindAllByStatusAndAirCompanyName() {
        List<Flight> flights = flightRepository.findAllByStatusCompletedAndEstimatedDateType();
        assertThat(flights.size()).isPositive();
    }

    @Test
    @Order(2)
    void testFindAllByStatusActive() {
        List<Flight> flights = flightRepository.findAllByStatusActiveAndCreatedDateGreaterThan24Hours();
        assertThat(flights).isEmpty();
    }

    @Test
    @Order(3)
    void testFindByStatusAndCompanyName() {
        List<Flight> flights = flightRepository.findAllByStatusAndAirCompanyName(FlightStatus.COMPLETED, "London Airways");
        assertThat(flights).isNotEmpty();
    }
}
