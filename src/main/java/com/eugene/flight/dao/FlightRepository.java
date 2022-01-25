package com.eugene.flight.dao;

import com.eugene.flight.domain.Flight;
import com.eugene.flight.domain.FlightStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    List<Flight> findAllByStatusAndAirCompanyName(FlightStatus status, String companyName);

}
