package com.eugene.flight.controller;

import com.eugene.flight.domain.AirCompany;
import com.eugene.flight.domain.request.CompanyAirplaneIdRequest;
import com.eugene.flight.service.AirCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.eugene.flight.controller.AirCompanyController.*;

@RestController
@RequestMapping(value = BASE_URL)
public class AirCompanyController {

    protected static final String BASE_URL = "/api/v1/air-company-management";

    private AirCompanyService companyService;

    @Autowired
    public AirCompanyController(AirCompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping(value = "/reassign", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AirCompany> assignAirplaneToAnotherCompany(@RequestBody CompanyAirplaneIdRequest companyAirplaneIdRequest) {
        AirCompany company = companyService.reassignAirplaneToAnotherCompany(
                companyAirplaneIdRequest.fromCompanyId(), companyAirplaneIdRequest.toCompanyId(), companyAirplaneIdRequest.airplaneId()
        );
        return new ResponseEntity<>(company, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirCompany> getCompanyById(@PathVariable Long id) {
        AirCompany currentCompany = companyService.findAirCompanyById(id);
        return ResponseEntity.ok(currentCompany);
    }
}