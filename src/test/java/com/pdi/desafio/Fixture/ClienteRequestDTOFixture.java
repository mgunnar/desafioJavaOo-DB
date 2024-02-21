package com.pdi.desafio.Fixture;

import com.pdi.desafio.dtos.ClienteRequestDTO;
import com.pdi.desafio.models.enums.TipoCliente;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

public class ClienteRequestDTOFixture {
    public static ClienteRequestDTO build() {
        return new ClienteRequestDTO(
                RandomStringUtils.randomAlphanumeric(10),
                TipoCliente.values()[RandomUtils.nextInt(0, TipoCliente.values().length)],
                RandomStringUtils.randomNumeric(11)
        );
    }
}