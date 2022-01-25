package com.eugene.flight.controller;

import com.eugene.flight.domain.Flight;
import com.eugene.flight.domain.request.FlightRequest;
import com.eugene.flight.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.eugene.flight.controller.FlightController.*;

@RestController
@RequestMapping(BASE_URL)
public class FlightController {

    protected static final String BASE_URL = "/api/v1/flight-management";

    private FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping(value = "/flights/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Flight>> getAllFlightsByStatusAndCompanyName(FlightRequest request) {
        List<Flight> allFlights = flightService.getAllFlightsByStatusAndAirCompanyName(request.status(), request.companyName());
        return ResponseEntity.ok(allFlights);
    }

    @GetMapping(value = "/flights/active")
    public ResponseEntity<List<Flight>> getAllActiveFlights() {
        List<Flight> allActiveFlights = flightService.findAllActiveFlights();
        return ResponseEntity.ok(allActiveFlights);
    }
}
