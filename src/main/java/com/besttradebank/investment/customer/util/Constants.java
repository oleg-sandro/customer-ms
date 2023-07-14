package com.besttradebank.investment.customer.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class Constants {

    public static final String CUSTOMER_USERNAME_EXISTS = "Customer with username %s has already existed";
    public static final String CUSTOMER_EMAIL_EXISTS = "Customer with email %s has already existed";
    public static final String DIFFERENT_CUSTOMERS = "Customer with username %s and customer with email %s are" +
            " two different customers";
    public static final String ACTIVATION_LINK_NOT_FOUND = "The activation link is incorrect";
    public static final String ACTIVATION_LINK_ALREADY_USED = "The activation link has already been used";
    public static final String ACTIVATION_LINK_IS_INVALID = "The activation link is already deprecated," +
            " because a new activation link has been requested";
    public static final String ACTIVATION_LINK_EXPIRED = "The activation link has already been expired";

    public static final String AUTOMATIC_EMAIL = "no-reply@best-trade-bank.com";
    public static final String ACTIVATION_EMAIL_SUBJECT = "Activation of customer account";
    public static final String ACTIVATION_EMAIL_BODY = "Hello! Please activate your account via clicking on link %s%s";

}
