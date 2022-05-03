package com.eugene.flight.controller;

import com.eugene.flight.domain.request.AirplaneRequest;
import com.eugene.flight.domain.resource.AirplaneResource;
import com.eugene.flight.service.AirplaneService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.eugene.flight.controller.AirplaneController.*;

@RestController
@RequestMapping(value = BASE_URL, produces = "application/hal+json")
public class AirplaneController {
    protected static final String BASE_URL = "/api/v1/airplane-management";

    private AirplaneService airplaneService;

    private AirplaneResource airplaneResource;

    public AirplaneController(AirplaneService airplaneService, AirplaneResource airplaneResource) {
        this.airplaneService = airplaneService;
        this.airplaneResource = airplaneResource;
    }

    @GetMapping("/airplanes")
    public ResponseEntity<CollectionModel<AirplaneRequest>> getAllAirplanes() {
        return ResponseEntity.ok(airplaneResource.toCollectionModel(airplaneService.findAll()));
    }
}
