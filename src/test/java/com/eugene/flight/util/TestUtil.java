package com.eugene.flight.util;

import com.eugene.flight.domain.AirCompany;
import com.eugene.flight.domain.request.AirCompanyRequest;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public final class TestUtil {

    private TestUtil() {
    }

    public static AirCompany airCompanyBuilder(Long id, String name) {
        return AirCompany
                .builder()
                .id(id)
                .name(name)
                .build();
    }

    public static AirCompanyRequest airCompanyRequestBuilder(Long id, String name) {
        return AirCompanyRequest
                .builder()
                .id(id)
                .name(name)
                .build();
    }

    public static Long generateRandomLong() {
        return ThreadLocalRandom.current()
                .nextLong(1, 11);
    }

    public static String generateRandomString() {
        return UUID.randomUUID()
                .toString();
    }
}
