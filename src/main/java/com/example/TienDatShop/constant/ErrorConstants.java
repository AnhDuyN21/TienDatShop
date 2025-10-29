package com.example.TienDatShop.constant;

public final class ErrorConstants {

    private ErrorConstants() {}

    public static final String ERROR_RESOURCE_NOT_FOUND = "error.resource.not-found";
    public static final String ERROR_INTERNAL_SERVER_ERROR = "error.internal-server-error";
    public static final String ERROR_INVALID_REQUEST = "error.invalid-request";

    public static final String ERROR_COIN_ID_REQUIRED = "error.validation.coin.id.required";
    public static final String ERROR_COIN_NAME_REQUIRED = "error.validation.coin.name.required";
    public static final String ERROR_COIN_NAME_LENGTH = "error.validation.coin.name.length";
    public static final String ERROR_COIN_LOGO_URL_REQUIRED = "error.validation.coin.logoUrl.required";
    public static final String ERROR_COIN_LOGO_URL_LENGTH = "error.validation.coin.logoUrl.length";
    public static final String ERROR_COIN_NOT_FOUND = "error.validation.coin.not-found";

    public static final String ERROR_AMOUNT_REQUIRED = "error.validation.amount.required";

    public static final String ERROR_FROM_ADDRESS_REQUIRED = "error.validation.fromAddress.required";
    public static final String ERROR_FROM_ADDRESS_LENGTH = "error.validation.fromAddress.length";

    public static final String ERROR_TO_ADDRESS_REQUIRED = "error.validation.toAddress.required";
    public static final String ERROR_TO_ADDRESS_LENGTH = "error.validation.toAddress.length";

    public static final String ERROR_EMAIL_REQUIRED = "error.validation.email.required";
    public static final String ERROR_EMAIL_INVALID = "error.validation.email.invalid";

    public static final String ERROR_PASSWORD_REQUIRED = "error.validation.password.required";
}
