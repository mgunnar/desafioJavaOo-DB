package com.pdi.desafio.exceptions;

import org.springframework.http.HttpStatus;

public class CompraNaoAutorizadaException extends CustomHttpException {

    public CompraNaoAutorizadaException(Double valor) {
        super(HttpStatus.BAD_REQUEST, "Compra não autorizada no valor de: " + valor);
    }
}
