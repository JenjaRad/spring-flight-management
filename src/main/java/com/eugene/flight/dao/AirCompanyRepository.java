package com.eugene.flight.dao;

import com.eugene.flight.domain.AirCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;

@Repository
public interface AirCompanyRepository extends JpaRepository<AirCompany, Long> {

    AirCompany findByName(String name);

    @Query("UPDATE AirCompany c set c.name = :name where c.id = :id")
    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    @Modifying
    void updateCompanyByName(Long id, String name);
}
