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

    public void efetuarCompra(String numeroConta, Double valor) throws CompraNaoAutorizadaException, ContaNaoEncontradaException {

        var requisicaoCompra = new Compra();

        requisicaoCompra.setNumeroConta(numeroConta);
        requisicaoCompra.setValor(valor);
        requisicaoCompra.setDataCriacao(Date.from(ZonedDateTime.now().toInstant()));

        var conta = contaService.buscarContaPorNumeroConta(numeroConta);

        if (conta.getSaldo() < valor) {
            throw new CompraNaoAutorizadaException(valor);
        } else {
            var compraValidadaComOuSemDesconto = verificaDescontoCompra(valor, conta);
            contaService.descontaValorCompraDoLimiteDisponivel(conta, compraValidadaComOuSemDesconto);
            contaService.aumentaLimiteDeCreditoSeDisponivel(requisicaoCompra.getValor(),conta);
            contaService.salvarConta(conta);
            compraRepository.save(requisicaoCompra);
        }
    }

//TODO: esse método está fazendo mais uma coisa, deve quebrar o aumento limite e desconto compra
    //feito
    private Double verificaDescontoCompra(Double valorCompra, Conta conta) {

        var percentualDesconto = conta.getCliente().getTipoCliente().getPercentualDesconto();
        var valorMinimoParaDesconto = conta.getCliente().getTipoCliente().getValorMinimoCompraParaTerDesconto();

        if (valorCompra >= valorMinimoParaDesconto) {
            valorCompra -= valorCompra * percentualDesconto;
        }
        return valorCompra;
    }
}