package com.eugene.flight.domain.request;

import com.eugene.flight.domain.AirCompany;
import com.eugene.flight.domain.Airplane;
import com.eugene.flight.domain.FlightStatus;
import lombok.Builder;

import java.time.Instant;

@Builder
public record FlightRequest(Long id, AirCompany company, Airplane airplane, FlightStatus status, String companyName,
                            Instant createdAt, Instant delayAt,
                            Instant endedAt, String departureCountry, int distance) {
}
