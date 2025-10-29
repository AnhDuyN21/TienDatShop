package com.example.TienDatShop.config.error.http;

import jakarta.annotation.Nonnull;
import org.springframework.http.HttpStatus;

public class BadRequestException extends BaseHttpException {

    public BadRequestException(@Nonnull String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

}
