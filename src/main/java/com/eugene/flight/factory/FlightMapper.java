package com.eugene.flight.factory;

import com.eugene.flight.domain.Flight;
import com.eugene.flight.domain.request.FlightRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FlightMapper {

    public static Flight convertToEntity(FlightRequest request) {
        return Flight.builder()
                .id(request.getId())
                .createdAt(request.getCreatedAt())
                .endedAt(request.getEndedAt())
                .delayAt(request.getDelayAt())
                .status(request.getStatus())
                .distance(request.getDistance())
                .departureCountry(request.getDepartureCountry())
                .build();
    }
}
