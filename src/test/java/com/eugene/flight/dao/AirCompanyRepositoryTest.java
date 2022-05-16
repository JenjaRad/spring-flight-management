package com.eugene.flight.dao;

import com.eugene.flight.domain.AirCompany;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
@Sql(value = {"/db/data-h2.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class AirCompanyRepositoryTest {
    @Autowired
    private AirCompanyRepository companyRepository;
    @Test
    @Order(1)
    void testFindByCompanyName() {
        AirCompany company = companyRepository.findByName("London Airways");
        assertThat(company).isNotNull();
    }

    @Test
    @Order(2)
    void testUpdateCompanyByName() {
        companyRepository.updateCompanyByName(1L, "Boeing");
        AirCompany updatedCompany = companyRepository.getById(1L);
        companyRepository.saveAndFlush(updatedCompany);
        assertThat(updatedCompany.getName()).isEqualTo("Boeing");
    }

    @Test
    @Order(3)
    void testFindAll() {
        List<AirCompany> companies = companyRepository.findAll();
        assertThat(companies).isNotNull();
        assertThat(companies.size()).isPositive();
    }
}
