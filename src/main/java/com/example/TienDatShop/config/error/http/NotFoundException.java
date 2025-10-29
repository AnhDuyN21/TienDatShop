package com.example.TienDatShop.config.error.http;

import org.springframework.http.HttpStatus;

import static com.example.TienDatShop.constant.ErrorConstants.ERROR_RESOURCE_NOT_FOUND;

public class NotFoundException extends BaseHttpException {

    public NotFoundException() {
        super(HttpStatus.NOT_FOUND, ERROR_RESOURCE_NOT_FOUND);
    }

}
