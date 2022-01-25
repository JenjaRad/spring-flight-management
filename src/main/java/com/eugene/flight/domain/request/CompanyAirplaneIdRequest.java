package com.eugene.flight.domain.request;

public record CompanyAirplaneIdRequest(Long fromCompanyId, Long toCompanyId, Long airplaneId) {
}
