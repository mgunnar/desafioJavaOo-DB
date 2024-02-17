package com.pdi.desafio.exceptions;

import org.springframework.http.HttpStatus;

public class ContaNaoEncontradaException extends CustomHttpException {

    public ContaNaoEncontradaException(String conta) {
        super(HttpStatus.BAD_REQUEST,"Conta não encontrada com número: " + conta);
    }
}
