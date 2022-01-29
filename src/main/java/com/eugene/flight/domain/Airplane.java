package com.eugene.flight.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "airplane", schema = "flight_schema")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public non-sealed class Airplane extends DataAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "airplane_sequence")
    @SequenceGenerator(name = "airplane_sequence", allocationSize = 1, schema = "flight_schema")
    private Long id;

    private String name;

    @NaturalId
    private String serialNumber;

    private int numberOfFlights;

    private Long fuelCapacity;

    private int flightDistance;

    private String type;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy = "airplane",
            orphanRemoval = true
    )
    @JsonManagedReference
    //@ToString.Exclude
    private Set<Flight> flights = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "air_company_id", nullable = false)
    @JsonBackReference
    @ToString.Exclude
    private AirCompany company;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || super.getClass() != o.getClass()) return false;
        Airplane airplane = (Airplane) o;
        return serialNumber.equals(airplane.serialNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serialNumber);
    }
}

