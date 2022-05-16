package com.eugene.flight.util;

import com.eugene.flight.domain.AirCompany;
import com.eugene.flight.domain.request.AirCompanyRequest;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public final class TestUtil {

    private static final short MIN_ORIGIN = 1;
    private static final short MAX_BOUND = 11;

    private TestUtil() {
    }

    public static AirCompany airCompanyBuilder(Long id, String name) {
        if (id < MIN_ORIGIN) {
            throw new IllegalArgumentException("You cannot set negative id to the company");
        }
        return AirCompany.builder().id(id).name(name).build();
    }

    public static AirCompanyRequest airCompanyRequestBuilder(Long id, String name) {
        if (id < MIN_ORIGIN) {
            throw new IllegalArgumentException("You cannot set negative id to the companyRequest");
        }
        return AirCompanyRequest.builder().id(id).name(name).build();
    }

    public static Long generateRandomNumber() {
        return ThreadLocalRandom.current().nextLong(MIN_ORIGIN, MAX_BOUND);
    }

    public static String generateRandomString() {
        return UUID.randomUUID().toString();
    }
}
