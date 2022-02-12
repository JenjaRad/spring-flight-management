package com.eugene.flight.util;

import com.eugene.flight.domain.Flight;
import com.eugene.flight.domain.request.FlightRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FlightMapper {

    private static ModelMapper mapper;

    static {
        mapper = new ModelMapper();
    }

    public static Flight convertToEntity(FlightRequest request) {
        Flight dest = mapper.map(request, Flight.class);
        if (request.id() != null) {
            dest.setId(request.id());
            dest.setStatus(request.status());
            dest.setAirCompany(request.company());
            dest.setAirplane(request.airplane());
            dest.setCreatedAt(request.createdAt());
            dest.setEndedAt(request.endedAt());
            dest.setDelayAt(request.delayAt());
            dest.setDistance(request.distance());
            dest.setDepartureCountry(request.departureCountry());
        }
        return dest;
    }
}
