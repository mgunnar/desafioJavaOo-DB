package com.pdi.desafio.Fixture;

import com.pdi.desafio.models.Compra;

import java.time.ZonedDateTime;
import java.util.Date;

public class CompraFixture {
    public static Compra build(Double valorCompra, String numeroConta ) {
        Compra compra = new Compra();
        compra.setNumeroConta(numeroConta);
        compra.setValor(valorCompra);
        compra.setDataCriacao(Date.from(ZonedDateTime.now().toInstant()));
        return compra;
    }
}