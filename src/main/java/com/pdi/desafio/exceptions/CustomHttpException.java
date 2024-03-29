package com.pdi.desafio.exceptions;

import org.springframework.http.HttpStatus;

public abstract class CustomHttpException extends Exception {

    private final HttpStatus httpStatus;
    private final String tipoTransacao;

    protected CustomHttpException(HttpStatus httpStatus, String message) {
        super(formatErrorMessageHttp(httpStatus, null, message));
        this.httpStatus = httpStatus;
        this.tipoTransacao = null;
    }

    protected CustomHttpException(HttpStatus httpStatus, String tipoTransacao, String message) {
        super(formatErrorMessageHttp(httpStatus, tipoTransacao, message));
        this.httpStatus = httpStatus;
        this.tipoTransacao = tipoTransacao;
    }

    private static String formatErrorMessageHttp(HttpStatus httpStatus, String tipoTransacao, String message) {
        if (tipoTransacao != null) {
            return httpStatus + " - " + tipoTransacao + ": " + message;
        } else {
            return httpStatus + " - " + message;
        }
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    public String getTipoTransacao() {
        return this.tipoTransacao;
    }
}
