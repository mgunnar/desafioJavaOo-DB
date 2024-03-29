package com.pdi.desafio.Fixture;

import com.pdi.desafio.models.Cliente;
import com.pdi.desafio.models.DTOs.ClienteRequestDTO;
import com.pdi.desafio.models.enums.TipoCliente;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

public class ClienteFixture {
    public static Cliente build() {
        return new Cliente(RandomStringUtils.randomNumeric(10),
                RandomStringUtils.randomAlphabetic(10),
                TipoCliente.values()[RandomUtils.nextInt(0, TipoCliente.values().length)]);
    }

    public static Cliente build(String cpf, String nome, TipoCliente tipoCliente) {
        return new Cliente(cpf, nome, tipoCliente);
    }

    public static Cliente build(TipoCliente tipoCliente) {
        return new Cliente(RandomStringUtils.randomNumeric(10),
                RandomStringUtils.randomAlphabetic(10),
                tipoCliente);
    }

    public static Cliente fromClienteRequestDTO(ClienteRequestDTO clienteRequestDTO) {
        return new Cliente(
                clienteRequestDTO.cpf(),
                clienteRequestDTO.nome(),
                clienteRequestDTO.tipoCliente()
        );
    }
}