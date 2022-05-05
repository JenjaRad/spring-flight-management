package com.eugene.flight.dao;

import com.eugene.flight.domain.Flight;
import com.eugene.flight.domain.FlightStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    List<Flight> findAllByStatusAndAirCompanyName(FlightStatus status, String companyName);

    @Query(value = "select * from flight_schema.flight where status like 'ACTIVE' and created_at < now() - INTERVAL '1 DAY'", nativeQuery = true)
    List<Flight> findAllByStatusActiveAndCreatedDateGreaterThan24Hours();

    @Query(value = "select * from flight_schema.flight as difference where status like 'COMPLETED' " +
            "AND estimated_date_type < difference.ended_at", nativeQuery = true)
    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    List<Flight> findAllByStatusCompletedAndEstimatedDateType();
}
