package com.pdi.desafio.Fixture;

import com.pdi.desafio.models.DTOs.CompraRequestDTO;

public class CompraRequestDTOFixture {
    public static CompraRequestDTO build(String numeroConta, Double valor){
        return new CompraRequestDTO(numeroConta,valor);
    }
}
