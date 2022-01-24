package com.eugene.flight.controller;

import com.eugene.flight.domain.AirCompany;
import com.eugene.flight.domain.request.CompanyAirplaneId;
import com.eugene.flight.service.AirCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = AirCompanyController.BASE_URL)
public class AirCompanyController {

    private AirCompanyService companyService;

    protected static final String BASE_URL = "/api/v1/airplane-management";

    @Autowired
    public AirCompanyController(AirCompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping(value = "/reassign", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AirCompany> assignAirplaneToAnotherCompany(@RequestBody CompanyAirplaneId companyAirplaneId) {
        AirCompany company = companyService.reassignAirplaneToAnotherCompany(
                companyAirplaneId.fromCompanyId(), companyAirplaneId.toCompanyId(), companyAirplaneId.airplaneId()
        );
        return new ResponseEntity<>(company, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirCompany> getCompanyById(@PathVariable Long id) {
        AirCompany currentCompany = companyService.findAirCompanyById(id);
        return new ResponseEntity<>(currentCompany, HttpStatus.OK);
    }

}