package com.eugene.flight.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "flight", schema = "flight_schema")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuperBuilder
public non-sealed class Flight extends DataAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "flight_sequence")
    @SequenceGenerator(name = "flight_sequence", sequenceName = "FLIGHT_SEQUENCE", allocationSize = 1, schema = "flight_schema")
    private Long id;

    @Enumerated(EnumType.STRING)
    private FlightStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "air_company_id", nullable = false)
    @JsonBackReference(value = "air-company-ref")
    @ToString.Exclude
    private AirCompany airCompany;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "airplane_id", nullable = false)
    @JsonBackReference(value = "airplane-ref")
    @ToString.Exclude
    private Airplane airplane;

    private String departureCountry;

    private String destinationCountry;

    private int distance;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC", locale = "uk_UA")
    private Instant estimatedDateType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC", locale = "uk_UA")
    @Column(name = "ended_at")
    private Instant endedAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC", locale = "uk_UA")
    @Column(name = "delay_at")
    private Instant delayAt;
}
