package com.eugene.flight.domain.request;

import com.eugene.flight.domain.FlightStatus;
import lombok.Builder;

import java.time.Instant;

@Builder
public record FlightRequest(FlightStatus status, String companyName, Instant createdAt, Instant delayAt,
                            Instant endedAt, String departureCountry, int distance, Long id) {
}
