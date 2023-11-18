package com.pdi.desafio.services;

import com.pdi.desafio.exceptions.CompraNaoAutorizadaException;
import com.pdi.desafio.exceptions.ContaNaoEncontradaException;
import com.pdi.desafio.exceptions.CpfNaoEncontradoException;
import com.pdi.desafio.models.Compra;
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

        public Compra efetuarCompra(String numeroConta, Double valor) throws CompraNaoAutorizadaException, ContaNaoEncontradaException, CpfNaoEncontradoException {
            var compra = new Compra();
            compra.setNumeroConta(numeroConta);
            compra.setValor(valor);
            compra.setDataCriacao(Date.from(ZonedDateTime.now().toInstant()));

            var cliente = contaService.bucarClientePorNumeroConta(numeroConta);
            var conta = contaService.buscarContaPorNumeroConta(numeroConta);


            if (conta.getSaldo() < valor) {
                throw new CompraNaoAutorizadaException(valor);
                } else {

                verificaDescontoCompra(valor, cliente.getTipoCliente());
                verificaAumentoDelimite(valor, cliente.getTipoCliente(), numeroConta);

                conta.setSaldo(conta.getSaldo() - valor);
                contaService.salvarConta(conta);
                compraRepository.save(compra);

                return compra;
            }
    }

    private Double verificaDescontoCompra (Double valorCompra, TipoCliente tipoCliente){
            var desconto = tipoCliente.getPercentualDesconto();
            var varloMinimoParaDesconto = tipoCliente.getValorMinimoCompraParaTerDesconto();

            if (valorCompra >= varloMinimoParaDesconto) {
                return valorCompra - (valorCompra * desconto);
            } else {
                return valorCompra;
            }
    }

    private void verificaAumentoDelimite (Double valorCompra, TipoCliente tipoCliente, String numeroConta) throws ContaNaoEncontradaException {
            if (tipoCliente.isAumentaLimiteLiberado() && valorCompra >= tipoCliente.getValorGastoParaAumentoLimite()){
                var conta = contaService.buscarContaPorNumeroConta(numeroConta);
                conta.setLimite(conta.getLimite() + tipoCliente.getValorDeAumentoLimite());
            }
        }

}
