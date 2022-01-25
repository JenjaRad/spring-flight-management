package com.eugene.flight.service;

import com.eugene.flight.dao.FlightRepository;
import com.eugene.flight.domain.Flight;
import com.eugene.flight.domain.FlightStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

@Service
public class FlightService {

    private FlightRepository flightRepository;

    @Autowired
    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public List<Flight> getAllFlightsByStatusAndAirCompanyName(FlightStatus status, String name) {
        if (status != null && StringUtils.hasText(name)) {
            return flightRepository.findAllByStatusAndAirCompanyName(status, name);
        }
        return Collections.emptyList();
    }
}
