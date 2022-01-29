package com.eugene.flight.service;

import com.eugene.flight.dao.FlightRepository;
import com.eugene.flight.domain.Flight;
import com.eugene.flight.domain.FlightStatus;
import com.eugene.flight.domain.request.FlightRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    public List<Flight> findAllActiveFlights() {
        return flightRepository.findAllByStatusActiveAndCreatedDateGreaterThan24Hours();
    }

    public Flight createFlight(Flight flight) {
        Flight savedFlight = Optional.of(flightRepository.save(flight))
                .orElseThrow(IllegalArgumentException::new);

        if (!savedFlight.getStatus()
                .equals(FlightStatus.PENDING)) {
            savedFlight.setStatus(FlightStatus.PENDING);
        }

        return savedFlight;
    }


    public Flight updateFlightStatus(Long flightId, FlightRequest updateRequest) {
        Flight foundedFlight = flightRepository.findById(flightId).orElseThrow(IllegalArgumentException::new);
        if (foundedFlight.getStatus()
                .equals(updateRequest.status())) {
            throw new IllegalArgumentException("You cannot set the same status to flight");
        }
        updateFlightState(updateRequest, foundedFlight);
        flightRepository.saveAndFlush(foundedFlight);
        return foundedFlight;
    }

    private void updateFlightState(FlightRequest updateRequest, Flight foundedFlight) {
        switch (updateRequest.status()) {
            case DELAYED -> {
                foundedFlight.setStatus(FlightStatus.DELAYED);
                foundedFlight.setDelayAt(updateRequest.delayAt());
            }
            case ACTIVE -> {
                foundedFlight.setStatus(FlightStatus.ACTIVE);
                foundedFlight.setCreatedAt(updateRequest.createdAt());
            }
            case COMPLETED -> {
                foundedFlight.setStatus(FlightStatus.COMPLETED);
                foundedFlight.setEndedAt(updateRequest.endedAt());
            }
            case PENDING -> foundedFlight.setStatus(FlightStatus.DEFAULT);
        }
    }
}