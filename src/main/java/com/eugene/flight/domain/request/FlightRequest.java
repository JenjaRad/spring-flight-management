package com.eugene.flight.domain.request;

import com.eugene.flight.domain.FlightStatus;

import java.time.Instant;

public record FlightRequest(FlightStatus status, String companyName, Instant createdAt, Instant delayAt,
                            Instant endedAt) {
}
