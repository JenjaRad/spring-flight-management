package com.eugene.flight.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "air_company", schema = "flight_schema")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@ToString
public class AirCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "air_company_sequence")
    @SequenceGenerator(name = "air_company_sequence", allocationSize = 1, schema = "flight_schema")
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
    @JsonManagedReference
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
}
