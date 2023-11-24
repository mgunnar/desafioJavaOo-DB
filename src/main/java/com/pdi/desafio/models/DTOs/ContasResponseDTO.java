package com.pdi.desafio.models.DTOs;


import java.util.Date;

public record ContasResponseDTO(
        String numeroConta,
        Double limite,
        Double saldo,
        String cpfCliente,
        Date dataCriacao) {
}
