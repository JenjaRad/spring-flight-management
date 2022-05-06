package com.eugene.flight.controller;

import com.eugene.flight.domain.Flight;
import com.eugene.flight.domain.request.FlightRequest;
import com.eugene.flight.resource.FlightResource;
import com.eugene.flight.service.FlightService;
import com.eugene.flight.factory.FlightMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.eugene.flight.controller.FlightController.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping(value = BASE_URL, produces = "application/hal+json")
public class FlightController {

    protected static final String BASE_URL = "/api/v1/flight-management";

    private final FlightService flightService;

    private final FlightResource flightResource;

    public FlightController(FlightService flightService, FlightResource flightResource) {
        this.flightService = flightService;
        this.flightResource = flightResource;
    }

    @GetMapping(value = "/flights/status/company-name")
    public ResponseEntity<CollectionModel<FlightRequest>> getAllFlightsByStatusAndCompanyName(FlightRequest request) {
        List<Flight> allFlights = flightService.getAllFlightsByStatusAndAirCompanyName(request.getStatus(), request.getCompany()
                .getName());
        return ResponseEntity.ok(flightResource.toCollectionModel(allFlights));
    }

    @GetMapping(value = "/flights/status/active")
    public ResponseEntity<CollectionModel<FlightRequest>> getAllActiveFlights() {
        List<Flight> allActiveFlights = flightService.findAllActiveFlights();
        CollectionModel<FlightRequest> flightRequests = flightResource.toCollectionModel(allActiveFlights);
        flightRequests.add(linkTo(methodOn(AirplaneController.class).getAllAirplanes()).withRel("airplanes"));
        flightRequests.add(linkTo(methodOn(AirCompanyController.class).getAllCompanies()).withRel("companies"));
        return ResponseEntity.ok(flightRequests);
    }

    @GetMapping("/flights/status/completed/date")
    public ResponseEntity<CollectionModel<FlightRequest>> findAllByDateAndStatus() {
        List<Flight> allFlightsByDate = flightService.findAllByStatusCompletedAndDate();
        return ResponseEntity.ok(flightResource.toCollectionModel(allFlightsByDate));
    }

    @GetMapping(value = "/flights/status/completed")
    public ResponseEntity<CollectionModel<FlightRequest>> getAllCompletedFlightsWithDateDiff() {
        List<Flight> flights = flightService.findAllByStatusCompletedAndDate();
        return ResponseEntity.ok(flightResource.toCollectionModel(flights));
    }

    @PutMapping(value = "/edit/{id}")
    public ResponseEntity<FlightRequest> updateFlightInfoByStatus(@PathVariable(name = "id") Long flightId, @RequestBody FlightRequest updatingFlight) {
        Flight savedFlight = flightService.updateFlightStatus(flightId, updatingFlight);
        FlightRequest flightRequest = flightResource.toModel(savedFlight);
        return new ResponseEntity<>(flightRequest, HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<FlightRequest> createFlight(@RequestBody FlightRequest request) {
        Flight convertedFlight = FlightMapper.convertToEntity(request);
        Flight createdFlight = flightService.createFlight(convertedFlight);
        FlightRequest flightRequest = flightResource.toModel(createdFlight);
        return new ResponseEntity<>(flightRequest, HttpStatus.CREATED);
    }
}
