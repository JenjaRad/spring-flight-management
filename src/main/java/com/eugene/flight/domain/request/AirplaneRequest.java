package com.eugene.flight.domain.request;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Builder
@EqualsAndHashCode
@Relation(itemRelation = "airplane", collectionRelation = "airplanes")
public class AirplaneRequest extends RepresentationModel<AirplaneRequest> {
    private Long id;
    private String name;
    private String serialNumber;
    private String type;
}
