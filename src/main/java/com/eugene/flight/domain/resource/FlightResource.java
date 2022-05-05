package com.eugene.flight.domain.resource;

import com.eugene.flight.controller.FlightController;
import com.eugene.flight.domain.Flight;
import com.eugene.flight.domain.request.FlightRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class FlightResource implements RepresentationModelAssembler<Flight, FlightRequest> {

    @Override
    public FlightRequest toModel(Flight from) {
        return FlightRequest.builder()
                .id(from.getId())
                .status(from.getStatus())
                .createdAt(from.getCreatedAt())
                .delayAt(from.getDelayAt())
                .company(from.getAirCompany())
                .airplane(from.getAirplane())
                .build();
    }

    @Override
    public CollectionModel<FlightRequest> toCollectionModel(Iterable<? extends Flight> flights) {
        CollectionModel<FlightRequest> flightRequests = RepresentationModelAssembler.super.toCollectionModel(flights);
        return flightRequests.add(linkTo(methodOn(FlightController.class).getAllActiveFlights()).withSelfRel());
    }
}
