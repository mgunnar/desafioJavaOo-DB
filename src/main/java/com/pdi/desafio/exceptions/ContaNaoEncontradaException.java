package com.pdi.desafio.exceptions;

public class ContaNaoEncontradaException extends Exception {

    private String conta;

    public ContaNaoEncontradaException(String conta) {
        super("CONTA NÃO ENCONTRADA.");
        this.conta = conta;
    }
}
