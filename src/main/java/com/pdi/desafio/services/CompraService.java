package com.pdi.desafio.services;

import com.pdi.desafio.exceptions.CompraNaoAutorizadaException;
import com.pdi.desafio.models.Compra;
import com.pdi.desafio.repository.CompraRepository;
import com.pdi.desafio.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Date;

@Service
public class CompraService {

    @Autowired
    ContaRepository contaRepository;

    @Autowired
    CompraRepository compraRepository;

        public Compra efetuarCompra(String numeroConta, Double valor) throws CompraNaoAutorizadaException {

            var compra = new Compra();
            compra.setNumeroConta(numeroConta);
            compra.setValor(valor);
            compra.setDataCriacao(Date.from(ZonedDateTime.now().toInstant()));

            var conta = contaRepository.findByNumeroConta(numeroConta)
                .orElseThrow(() -> new CompraNaoAutorizadaException(valor));

            if (conta.getSaldo() < valor) {
                throw new CompraNaoAutorizadaException(valor);
                } else {
                conta.setSaldo(conta.getSaldo() - valor);
                contaRepository.save(conta);
                compraRepository.save(compra);
                return compra;
            }
    }
}
