package com.pdi.desafio.models.DTOs;


import com.pdi.desafio.models.Cliente;
import com.pdi.desafio.models.Conta;

public record ContasResponseDTO(Cliente cliente, Conta conta) {
}
