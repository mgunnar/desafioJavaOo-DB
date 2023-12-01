package com.pdi.desafio.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomHttpException.class)
    public ResponseEntity<String> handleExceptionAbstract(CustomHttpException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }
    //Testes unitários e exceções. fazer antes sem o transaction no teste unitário
    //Implementar transaction e fazer testes unitários
    //Teste para tipoCliente
    // Transaction na hora pagar conta.
    //
}
