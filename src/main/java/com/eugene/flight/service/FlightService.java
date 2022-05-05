package com.eugene.flight.service;

import com.eugene.flight.dao.FlightRepository;
import com.eugene.flight.domain.Flight;
import com.eugene.flight.domain.FlightStatus;
import com.eugene.flight.domain.request.FlightRequest;
import com.eugene.flight.exception.FlightNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class FlightService {

    private final FlightRepository flightRepository;

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
        if (!flight.getStatus()
                .equals(FlightStatus.PENDING)) {
            flight.setStatus(FlightStatus.PENDING);
        }
        return flightRepository.save(flight);
    }

    public List<Flight> findAllByStatusCompletedAndDate() {
        return flightRepository.findAllByStatusCompletedAndEstimatedDateType();
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public Flight updateFlightStatus(Long flightId, FlightRequest updateRequest) {
        Flight foundedFlight = flightRepository.findById(flightId)
                .orElseThrow(FlightNotFoundException::new);
        if (foundedFlight.getStatus()
                .equals(updateRequest.getStatus())) {
            throw new IllegalArgumentException("You cannot set the same status to flight");
        }
        updateFlightState(updateRequest, foundedFlight);
        return flightRepository.saveAndFlush(foundedFlight);
    }

    private void updateFlightState(FlightRequest updateRequest, Flight foundedFlight) {
        foundedFlight.setStatus(updateRequest.getStatus());
        switch (updateRequest.getStatus()) {
            case DELAYED -> foundedFlight.setDelayAt(updateRequest.getDelayAt());
            case ACTIVE -> foundedFlight.setCreatedAt(updateRequest.getCreatedAt());
            case COMPLETED -> foundedFlight.setEndedAt(updateRequest.getEndedAt());
            default -> foundedFlight.setStatus(FlightStatus.DEFAULT);
        }
    }
}