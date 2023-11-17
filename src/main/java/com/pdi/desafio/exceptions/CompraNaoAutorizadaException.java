package com.pdi.desafio.exceptions;

public class CompraNaoAutorizadaException extends Exception {

    private Double valor;

    public CompraNaoAutorizadaException(Double valor) {
        super("COMPRA NÃO AUTORIZADA!");
        this.valor = valor;
    }
}
