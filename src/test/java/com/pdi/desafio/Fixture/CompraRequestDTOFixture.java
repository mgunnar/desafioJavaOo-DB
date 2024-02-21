package com.pdi.desafio.Fixture;

import com.pdi.desafio.dtos.CompraRequestDTO;

public class CompraRequestDTOFixture {
    public static CompraRequestDTO build(String numeroConta, Double valor){
        return new CompraRequestDTO(numeroConta,valor);
    }
}
