package com.eugene.flight.controller;

import com.eugene.flight.domain.Flight;
import com.eugene.flight.domain.request.FlightRequest;
import com.eugene.flight.service.FlightService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.eugene.flight.controller.FlightController.*;

@RestController
@RequestMapping(BASE_URL)
public class FlightController {

    protected static final String BASE_URL = "/api/v1/flight-management";

    private FlightService flightService;

    private ModelMapper mapper;

    @Autowired
    public FlightController(FlightService flightService, ModelMapper mapper) {
        this.flightService = flightService;
        this.mapper = mapper;
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

    @GetMapping(value = "/flights/completed")
    public ResponseEntity<List<Flight>> getAllCompletedFlightsWithDateDiff() {
        List<Flight> flights = flightService.findAllByStatusCompletedAndDate();
        return ResponseEntity.ok(flights);
    }

    @PutMapping(value = "/edit/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Flight> updateFlightInfoByStatus(@PathVariable(name = "id") Long flightId, @RequestBody FlightRequest updatingFlight) {
        Flight savedFlight = flightService.updateFlightStatus(flightId, updatingFlight);
        return new ResponseEntity<>(savedFlight, HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<Flight> createFlight(@RequestBody FlightRequest request) {
        Flight convertedFlight = convertToEntity(request);
        Flight createdFlight = flightService.createFlight(convertedFlight);
        return new ResponseEntity<>(createdFlight, HttpStatus.CREATED);
    }

    private Flight convertToEntity(FlightRequest request) {
        Flight dest = mapper.map(request, Flight.class);
        if (request.id() != null) {
            dest.setId(request.id());
            dest.setStatus(request.status());
            dest.setAirCompany(request.company());
            dest.setAirplane(request.airplane());
            dest.setCreatedAt(request.createdAt());
            dest.setEndedAt(request.endedAt());
            dest.setDelayAt(request.delayAt());
            dest.setDistance(request.distance());
            dest.setDepartureCountry(request.departureCountry());
        }
        return dest;
    }
}
