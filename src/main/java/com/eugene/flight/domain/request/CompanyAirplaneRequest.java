package com.eugene.flight.domain.request;

public record CompanyAirplaneRequest(Long fromCompanyId, Long toCompanyId, Long airplaneId) {
}
