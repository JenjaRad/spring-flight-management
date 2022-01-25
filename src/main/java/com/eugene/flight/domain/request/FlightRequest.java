package com.eugene.flight.domain.request;

import com.eugene.flight.domain.FlightStatus;

public record FlightRequest(FlightStatus status, String companyName) {
}
