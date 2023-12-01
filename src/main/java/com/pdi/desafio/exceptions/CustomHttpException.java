package com.pdi.desafio.exceptions;

import org.springframework.http.HttpStatus;

public abstract class CustomHttpException extends Exception {

    private HttpStatus httpStatus;

    protected CustomHttpException(HttpStatus httpStatus, String message) {
        super(formatErrorMessage(httpStatus, message));
        this.httpStatus = httpStatus;
    }

    private static String formatErrorMessage(HttpStatus httpStatus, String message) {
        return httpStatus + " - " + message;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
}
