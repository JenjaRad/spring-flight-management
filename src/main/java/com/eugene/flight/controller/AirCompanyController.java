package com.eugene.flight.controller;

import com.eugene.flight.domain.AirCompany;
import com.eugene.flight.domain.Airplane;
import com.eugene.flight.service.AirCompanyService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = AirCompanyController.BASE_URL)
public class AirCompanyController {

    private AirCompanyService companyService;

    protected static final String BASE_URL = "/api/v1/air-company";

    @Autowired
    public AirCompanyController(AirCompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping(value = "/company-management/reassign/{companyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AirCompany> assignAirplanesToAnotherCompany(@PathVariable Long companyId, @RequestBody ObjectNode node) {
        return ResponseEntity.noContent()
                .build();
    }
}
// /?param1=litak&param2=vertu&company=