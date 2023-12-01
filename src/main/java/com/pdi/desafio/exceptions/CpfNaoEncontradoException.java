package com.pdi.desafio.exceptions;

import org.springframework.http.HttpStatus;

public class CpfNaoEncontradoException extends CustomHttpException{
    public CpfNaoEncontradoException(String cpf) {
        super(HttpStatus.NOT_FOUND, "Cliente não encontrado com CPF: " + cpf);
    }
}
