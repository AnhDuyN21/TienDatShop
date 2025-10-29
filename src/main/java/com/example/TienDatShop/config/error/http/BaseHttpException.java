package com.example.TienDatShop.config.error.http;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class BaseHttpException extends RuntimeException {

    protected final HttpStatus status;
    protected final String message;

}
