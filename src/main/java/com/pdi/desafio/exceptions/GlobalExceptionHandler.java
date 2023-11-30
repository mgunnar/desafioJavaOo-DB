package com.pdi.desafio.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler{
    @ExceptionHandler(CpfNaoEncontradoException.class)
    public ResponseEntity<String> handleClienteNaoEncontradoException(CpfNaoEncontradoException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(CustomHttpException.class)
    public ResponseEntity<String> handleExceptionAbstract(HttpStatus httpStatus, CustomHttpException e) {
        return ResponseEntity.status(httpStatus).body(e.getMessage());
    }

    @ExceptionHandler(ContaNaoEncontradaException.class)
    public ResponseEntity<String> handleContaNaoEncontradaException(ContaNaoEncontradaException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(CompraNaoAutorizadaException.class)
    public ResponseEntity<String> handleCompraNaoAutorizadaException(CompraNaoAutorizadaException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }


    // O corpo da resposta apenas a msg erro + status code
    // Criar abstratcion da exceção status + body que extend excpetion
    // todas as minhas exceções extend a abstração
    // no construtor passo o httpstatus e a mensagem
    // no handler eu pego a abstração e retorno o status e a mensagem

    //Testes unitários e exceções. fazer antes sem o transaction no teste unitário
    //Teste para tipoCliente
    // Transaction na hora pagar conta.
}
