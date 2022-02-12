package com.eugene.flight.factory;

import com.eugene.flight.domain.Flight;
import com.eugene.flight.domain.request.FlightRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FlightMapper {

    public static Flight convertToEntity(FlightRequest request) {
        return Flight.builder()
                .id(request.id())
                .createdAt(request.createdAt())
                .endedAt(request.endedAt())
                .delayAt(request.delayAt())
                .status(request.status())
                .airCompany(request.company())
                .airplane(request.airplane())
                .distance(request.distance())
                .departureCountry(request.departureCountry())
                .build();
    }
}
