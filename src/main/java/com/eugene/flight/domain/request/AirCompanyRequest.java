package com.eugene.flight.domain.request;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Builder
@EqualsAndHashCode(callSuper = false)
@Relation(itemRelation = "airCompany", collectionRelation = "airCompanies")
public class AirCompanyRequest extends RepresentationModel<AirCompanyRequest> {
    private Long id;
    private String name;
    private String companyType;
}
