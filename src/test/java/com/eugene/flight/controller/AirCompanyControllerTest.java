package com.eugene.flight.controller;

import com.eugene.flight.domain.AirCompany;
import com.eugene.flight.domain.request.AirCompanyRequest;
import com.eugene.flight.domain.request.CompanyAirplaneIdRequest;
import com.eugene.flight.resource.AirCompanyResource;
import com.eugene.flight.service.AirCompanyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

import static com.eugene.flight.util.TestUtil.*;
import static com.eugene.flight.util.TestUtil.airCompanyBuilder;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AirCompanyController.class)
class AirCompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AirCompanyService airCompanyService;

    @MockBean
    private AirCompanyResource airCompanyResource;

    @Test
    @Order(1)
    void whenFindCompanyThenStatusOk() throws Exception {
        List<AirCompany> company = Collections.singletonList(airCompanyBuilder(generateRandomNumber(), generateRandomString()));
        when(airCompanyService.findAll())
                .thenReturn(company);

        mockMvc.perform(get("/api/v1/air-company-management/companies")
                .content(mapper.writeValueAsString(company))
                .contentType("application/hal+json"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    void whenListCompanyIsNull() throws Exception {
        when(airCompanyService.findAll())
                .thenReturn(null);
        mockMvc.perform(get("/api/v1/air-company-management/companies")
                .content(mapper.writeValueAsString(null))
                .contentType("application/hal+json"))
                .andDo(print())
                .andExpect(jsonPath("$[0]").doesNotExist())
                .andExpect(status().isOk());
    }

    @Test
    @Order(3)
    void whenGetByIdThenStatusOk() throws Exception {
        AirCompany company = airCompanyBuilder(generateRandomNumber(), generateRandomString());
        AirCompanyRequest request = airCompanyRequestBuilder(generateRandomNumber(), generateRandomString());

        when(airCompanyService.findAirCompanyById(anyLong())).thenReturn(company);
        when(airCompanyResource.toModel(company)).thenReturn(request);

        mockMvc.perform(get("/api/v1/air-company-management/{id}", generateRandomNumber())
                .contentType("application/hal+json")
                .characterEncoding(StandardCharsets.UTF_8.name()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(4)
    void whenReassignAirplaneThenStatusCreated() throws Exception {
        AirCompany airCompany = airCompanyBuilder(generateRandomNumber(), generateRandomString());
        AirCompanyRequest request = airCompanyRequestBuilder(generateRandomNumber(), generateRandomString());

        var airplaneIdRequest = new CompanyAirplaneIdRequest(generateRandomNumber(), generateRandomNumber(), generateRandomNumber());
        when(airCompanyService.reassignAirplaneToAnotherCompany(anyLong(), anyLong(), anyLong())).thenReturn(airCompany);
        when(airCompanyResource.toModel(airCompany)).thenReturn(request);

        mockMvc.perform(post("/api/v1/air-company-management/reassign")
                .contentType("application/hal+json")
                .content(mapper.writeValueAsString(airplaneIdRequest))
                .characterEncoding(StandardCharsets.UTF_8.name()))
                .andDo(print())
                .andExpect(status().isCreated());
    }
}
