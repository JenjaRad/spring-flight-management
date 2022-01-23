package com.eugene.flight.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "flight", schema = "flight_schema")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public non-sealed class Flight extends DataAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "flight_sequence")
    @SequenceGenerator(name = "flight_sequence", allocationSize = 1, schema = "flight_schema")
    private Long id;

    @Enumerated(EnumType.STRING)
    private FlightStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "air_company_id")
    private AirCompany airCompany;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "airplane_id")
    private Airplane airplane;

    private String departureCountry;

    private String destinationCountry;

    private int distance;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd 'at' HH:mm", locale = "uk_UA")
    private Instant estimatedDateType;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd 'at' HH:mm", locale = "uk_UA")
    @Column(name = "ended_at")
    private Instant endedAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd 'on' HH:mm", locale = "uk_UA")
    @Column(name = "delay_at")
    private Instant delayAt;
}
