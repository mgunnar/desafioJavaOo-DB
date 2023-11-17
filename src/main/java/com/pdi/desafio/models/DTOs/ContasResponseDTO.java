package com.pdi.desafio.models.DTOs;


import com.pdi.desafio.models.Cliente;
import com.pdi.desafio.models.Conta;

import java.util.List;

public record ContasResponseDTO(Cliente cliente, List<Conta> contas) {
}
