package com.pdi.desafio.exceptions;

import org.springframework.http.HttpStatus;

public class RuntimeTransacaoNaoConcluida extends CustomHttpException{
    public RuntimeTransacaoNaoConcluida(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR,"Transação não concluída: " + message);
    }
}
