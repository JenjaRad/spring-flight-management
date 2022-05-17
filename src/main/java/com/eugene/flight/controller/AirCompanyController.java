package com.eugene.flight.controller;

import com.eugene.flight.domain.request.AirCompanyRequest;
import com.eugene.flight.domain.request.CompanyAirplaneIdRequest;
import com.eugene.flight.resource.AirCompanyResource;
import com.eugene.flight.service.AirCompanyService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.eugene.flight.controller.AirCompanyController.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping(value = BASE_URL, produces = "application/hal+json")
public class AirCompanyController {

    protected static final String BASE_URL = "/api/v1/air-company-management";

    private final AirCompanyService companyService;

    private final AirCompanyResource airCompanyResource;

    public AirCompanyController(AirCompanyService companyService, AirCompanyResource airCompanyResource) {
        this.companyService = companyService;
        this.airCompanyResource = airCompanyResource;
    }

    @PostMapping(value = "/airplane/reassign")
    public ResponseEntity<AirCompanyRequest> assignAirplaneToAnotherCompany(@RequestBody CompanyAirplaneIdRequest companyAirplaneIdRequest) {
        return Optional.ofNullable(companyService.reassignAirplaneToAnotherCompany(
                companyAirplaneIdRequest.fromCompanyId(), companyAirplaneIdRequest.toCompanyId(), companyAirplaneIdRequest.airplaneId()))
                .map(airCompany -> {
                    AirCompanyRequest companyRequest = airCompanyResource.toModel(airCompany);
                    companyRequest.add(linkTo(methodOn(AirplaneController.class).getAllAirplanes()).withRel("airplanes"));
                    return new ResponseEntity<>(companyRequest, HttpStatus.CREATED);
                })
                .orElse(ResponseEntity.badRequest()
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirCompanyRequest> getCompanyById(@PathVariable Long id) {
        return Optional.ofNullable(companyService.findAirCompanyById(id))
                .map(company -> {
                    AirCompanyRequest airCompanyRequest = airCompanyResource.toModel(company);
                    airCompanyRequest.add(linkTo(methodOn(AirCompanyController.class).getAllCompanies()).withSelfRel());
                    return ResponseEntity.ok(airCompanyRequest);
                })
                .orElse(ResponseEntity.notFound()
                        .build());
    }

    @GetMapping("/companies")
    public ResponseEntity<CollectionModel<AirCompanyRequest>> getAllCompanies() {
        return ResponseEntity.ok(airCompanyResource.toCollectionModel(companyService.findAll()));
    }
}