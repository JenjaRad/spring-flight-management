package com.eugene.flight.domain.resource;

import com.eugene.flight.controller.AirplaneController;
import com.eugene.flight.domain.Airplane;
import com.eugene.flight.domain.request.AirplaneRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AirplaneResource implements RepresentationModelAssembler<Airplane, AirplaneRequest> {

    @Override
    public AirplaneRequest toModel(Airplane from) {
        AirplaneRequest airplaneRequest = AirplaneRequest.builder()
                .id(from.getId())
                .name(from.getName())
                .serialNumber(from.getSerialNumber())
                .type(from.getType())
                .build();
        airplaneRequest.add(linkTo(methodOn(AirplaneController.class)).withSelfRel());
        return airplaneRequest;
    }

    @Override
    public CollectionModel<AirplaneRequest> toCollectionModel(Iterable<? extends Airplane> airplanes) {
        CollectionModel<AirplaneRequest> companyRequests = RepresentationModelAssembler.super.toCollectionModel(airplanes);
        return companyRequests.add(linkTo(methodOn(AirplaneController.class).getAllAirplanes()).withSelfRel());
    }
}
