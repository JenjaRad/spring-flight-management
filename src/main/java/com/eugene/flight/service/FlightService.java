package com.eugene.flight.service;

import com.eugene.flight.dao.FlightRepository;
import com.eugene.flight.domain.Flight;
import com.eugene.flight.domain.FlightStatus;
import com.eugene.flight.domain.request.FlightRequest;
import com.eugene.flight.exception.FlightNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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

    public List<Flight> findAllByStatusCompletedAndDate() {
        return flightRepository.findAllByStatusCompletedAndEstimatedDateType();
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public Flight updateFlightStatus(Long flightId, FlightRequest updateRequest) {
        Flight foundedFlight = flightRepository.findById(flightId)
                .orElseThrow(FlightNotFoundException::new);
        if (foundedFlight.getStatus()
                .equals(updateRequest.status())) {
            throw new IllegalArgumentException("You cannot set the same status to flight");
        }
        updateFlightState(updateRequest, foundedFlight);
        return flightRepository.saveAndFlush(foundedFlight);
    }

    private void updateFlightState(FlightRequest updateRequest, Flight foundedFlight) {
        foundedFlight.setStatus(updateRequest.status());
        switch (updateRequest.status()) {
            case DELAYED -> foundedFlight.setDelayAt(updateRequest.delayAt());
            case ACTIVE -> foundedFlight.setCreatedAt(updateRequest.createdAt());
            case COMPLETED -> foundedFlight.setEndedAt(updateRequest.endedAt());
            default -> foundedFlight.setStatus(FlightStatus.DEFAULT);
        }
    }
}