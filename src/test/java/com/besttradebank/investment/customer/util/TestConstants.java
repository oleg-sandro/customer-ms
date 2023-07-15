package com.besttradebank.investment.customer.util;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class TestConstants {

    public static final UUID CUSTOMER_ID = UUID.fromString("de6b37b0-847a-448e-a9f7-8f8c437e8750");
    public static final String CUSTOMER_USERNAME = "OlegSandro";
    public static final String CUSTOMER_EMAIL = "test@gmail.com";
    public static final String CUSTOMER_PASSWORD = "qwerty123";

    public static final UUID ACTIVATION_ID = UUID.fromString("239ca3ae-d5fe-4b9f-946c-1a04a922acd3");
    public static final Instant EXPIRED_AT = Instant.now().plus(1, ChronoUnit.DAYS);

}
