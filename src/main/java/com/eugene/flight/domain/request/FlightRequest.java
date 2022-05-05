package com.eugene.flight.domain.request;

import com.eugene.flight.domain.AirCompany;
import com.eugene.flight.domain.Airplane;
import com.eugene.flight.domain.FlightStatus;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.Instant;

@Getter
@Builder
@EqualsAndHashCode(callSuper = false)
@Relation(itemRelation = "flight", collectionRelation = "flights")
public final class FlightRequest extends RepresentationModel<FlightRequest> {
    private final Long id;
    private final FlightStatus status;
    private final AirCompany company;
    private final Airplane airplane;
    private final Instant createdAt;
    private final Instant delayAt;
    private final Instant endedAt;
    private final String departureCountry;
    private final int distance;
}
