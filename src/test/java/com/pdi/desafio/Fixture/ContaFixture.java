package com.pdi.desafio.Fixture;

import com.pdi.desafio.models.Cliente;
import com.pdi.desafio.models.Conta;
import com.pdi.desafio.models.enums.TipoCliente;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.ZonedDateTime;
import java.util.Date;


public class ContaFixture {

    public static Conta build(Cliente cliente) {
        TipoCliente tipoCliente = cliente.getTipoCliente();

        Conta novaConta = new Conta();
        novaConta.setNumeroConta(RandomStringUtils.randomNumeric(5));
        novaConta.setLimite(tipoCliente.getLimiteCreditoInicial());
        novaConta.setSaldo(tipoCliente.getLimiteCreditoInicial());
        novaConta.setCpfCliente(cliente.getCpf());
        novaConta.setCliente(cliente);
        novaConta.setDataCriacao(Date.from(ZonedDateTime.now().toInstant()));

        return novaConta;
    }
}

