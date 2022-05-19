package com.eugene.flight.resource;

import com.eugene.flight.controller.AirCompanyController;
import com.eugene.flight.domain.AirCompany;
import com.eugene.flight.domain.request.AirCompanyRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class AirCompanyResource implements RepresentationModelAssembler<AirCompany, AirCompanyRequest> {

    @Override
    public AirCompanyRequest toModel(AirCompany from) {
        AirCompanyRequest companyRequest = AirCompanyRequest.builder()
                .id(from.getId())
                .name(from.getName())
                .companyType(from.getCompanyType())
                .build();
        companyRequest.add(linkTo(methodOn(AirCompanyController.class).getCompanyById(companyRequest.getId())).withSelfRel());
        return companyRequest;
    }

    @Override
    public CollectionModel<AirCompanyRequest> toCollectionModel(Iterable<? extends AirCompany> companies) {
        CollectionModel<AirCompanyRequest> companyRequests = RepresentationModelAssembler.super.toCollectionModel(companies);
        return companyRequests.add(linkTo(methodOn(AirCompanyController.class).getAllCompanies()).withSelfRel());
    }
}