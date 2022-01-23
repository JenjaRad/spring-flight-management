package com.eugene.flight;

import com.eugene.flight.dao.AirCompanyRepository;
import com.eugene.flight.dao.AirplaneRepository;
import com.eugene.flight.domain.AirCompany;
import com.eugene.flight.service.AirCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringFlightManagementApplication implements CommandLineRunner {


    private AirCompanyRepository repository;

    private AirplaneRepository airplaneRepository;

    @Autowired
    public SpringFlightManagementApplication(AirCompanyRepository repository, AirplaneRepository airplaneRepository) {
        this.repository = repository;
        this.airplaneRepository = airplaneRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringFlightManagementApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        AirCompanyService service = new AirCompanyService(repository, airplaneRepository);
        final AirCompany company = service.reassignAirplaneToAnotherCompany(1L, 2L, 1L);
        System.out.println(company);
    }
}
