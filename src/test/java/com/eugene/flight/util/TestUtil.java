package com.eugene.flight.util;

import com.eugene.flight.domain.AirCompany;
import com.eugene.flight.domain.request.AirCompanyRequest;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public final class TestUtil {

    /*
    You cannot set negative number to the MIN_ORIGIN
     */
    private static final int MIN_ORIGIN = Math.abs(1);
    /*
    You cannot set negative number to the MAX_BOUND
     */
    private static final int MAX_BOUND = Math.abs(11);

    private TestUtil() {
    }

    public static AirCompany airCompanyBuilder(Long id, String name) {
          /*
           Constant value might be dynamically changed and not suitable to use it in the 'if' statement
         */
        if (id < 1) {
            throw new IllegalArgumentException("You cannot set negative id to the company");
        }
        return AirCompany.builder().id(id).name(name).build();
    }

    public static AirCompanyRequest airCompanyRequestBuilder(Long id, String name) {
        /*
           Constant value might be dynamically changed and not suitable to use it in the 'if' statement
         */
        if (id < 1) {
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
