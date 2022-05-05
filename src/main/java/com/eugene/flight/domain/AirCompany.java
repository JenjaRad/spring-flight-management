package com.eugene.flight.domain;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "air_company", schema = "flight_schema")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public non-sealed class AirCompany extends DataAudit implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "air_company_sequence")
    @SequenceGenerator(name = "air_company_sequence", sequenceName = "AIR_COMPANY_SEQUENCE", allocationSize = 1, schema = "flight_schema")
    private Long id;

    private String name;

    private String companyType;

    @Column(name = "founded_at")
    private int foundedAt;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy = "airCompany",
            orphanRemoval = true
    )
    @JsonIgnore
    @ToString.Exclude
    private Set<Flight> flights = new HashSet<>();

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy = "company",
            orphanRemoval = true
    )
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @ToString.Exclude
    private List<Airplane> airplanes = new ArrayList<>();

    public void addFlight(Flight flight) {
        this.flights.add(flight);
        flight.setAirCompany(this);
    }

    public void removeFlight(Flight flight) {
        this.flights.remove(flight);
        flight.setAirCompany(null);
    }

    public void addAirplane(Airplane airplane) {
        this.airplanes.add(airplane);
        airplane.setCompany(this);
    }

    public void removeAirplane(Airplane airplane) {
        this.airplanes.remove(airplane);
        airplane.setCompany(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AirCompany company = (AirCompany) o;
        return id.equals(company.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
