package com.besttradebank.investment.customer.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class ErrorMessageConstants {

    public static final String CUSTOMER_USERNAME_EXISTS = "Customer with username %s has already existed";
    public static final String CUSTOMER_EMAIL_EXISTS = "Customer with email %s has already existed";
    public static final String DIFFERENT_CUSTOMERS = "Customer with username %s and customer with email %s are" +
            " two different customers";

}
