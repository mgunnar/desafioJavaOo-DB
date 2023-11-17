package com.pdi.desafio.exceptions;

public class CpfNaoEncontradoException extends Exception {

    private String cpf;

    public CpfNaoEncontradoException(String cpf) {
        super("Cliente não encontrado com CPF: " + cpf);
        this.cpf = cpf;
    }
}
