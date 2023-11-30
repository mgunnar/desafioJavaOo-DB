package com.pdi.desafio.exceptions;

import org.springframework.http.HttpStatus;

public abstract class CustomHttpException extends Exception {

    protected CustomHttpException(HttpStatus httpStatus, String message) {
        super(formatErrorMessage(httpStatus, message));
    }

    private static String formatErrorMessage(HttpStatus httpStatus, String message) {
        return httpStatus + " - " + message;
    }
}
