package com.pdi.desafio.services;

import com.pdi.desafio.exceptions.CompraNaoAutorizadaException;
import com.pdi.desafio.exceptions.ContaNaoEncontradaException;
import com.pdi.desafio.models.Compra;
import com.pdi.desafio.models.Conta;
import com.pdi.desafio.models.enums.TipoCliente;
import com.pdi.desafio.repository.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Date;

@Service
public class CompraService {

    @Autowired
    ContaService contaService;

    @Autowired
    CompraRepository compraRepository;

    public Compra efetuarCompra(String numeroConta, Double valor) throws CompraNaoAutorizadaException, ContaNaoEncontradaException {
        var compra = new Compra();

        compra.setNumeroConta(numeroConta);
        compra.setValor(valor);
        compra.setDataCriacao(Date.from(ZonedDateTime.now().toInstant()));

        var conta = contaService.buscarContaPorNumeroConta(numeroConta);
        var cliente = conta.getCliente();

        if (conta.getSaldo() < valor) {
            throw new CompraNaoAutorizadaException(valor);
        } else {
            var compraValidadaComDesconto = verificaDescontoCompra(valor, cliente.getTipoCliente(), conta);
            verificaAumentoDeLimite(valor, cliente.getTipoCliente(), conta);

            conta.setSaldo(conta.getSaldo() - compraValidadaComDesconto);

            contaService.salvarConta(conta);
            compraRepository.save(compra);

            return compra;
        }
    }


    private Double verificaDescontoCompra(Double valorCompra, TipoCliente tipoCliente, Conta conta) {
        if (valorCompra == null || tipoCliente == null || conta == null) {
            throw new IllegalArgumentException("valorCompra, tipoCliente e conta não podem ser nulos.");
        }

        var desconto = tipoCliente.getPercentualDesconto();
        var valorMinimoParaDesconto = tipoCliente.getValorMinimoCompraParaTerDesconto();

        if (valorCompra >= valorMinimoParaDesconto) {
            valorCompra -= valorCompra * (desconto);

            if (tipoCliente.isAumentaLimiteLiberado() && valorCompra >= tipoCliente.getValorGastoParaAumentoLimite()) {
                conta.setLimite(conta.getLimite() + tipoCliente.getValorDeAumentoLimite());
            }
        }
        return valorCompra;
    }



    private void verificaAumentoDeLimite(Double valorCompra, TipoCliente tipoCliente, Conta conta){
        if (tipoCliente.isAumentaLimiteLiberado() && valorCompra >= tipoCliente.getValorGastoParaAumentoLimite()) {
            conta.setLimite(conta.getLimite() + tipoCliente.getValorDeAumentoLimite());
        }
    }
}