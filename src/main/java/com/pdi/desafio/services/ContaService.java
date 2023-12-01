package com.pdi.desafio.services;

import com.pdi.desafio.exceptions.ContaNaoEncontradaException;
import com.pdi.desafio.models.Cliente;
import com.pdi.desafio.models.Conta;
import com.pdi.desafio.repository.ContaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Date;

@Service
@AllArgsConstructor
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    public String criarConta(Cliente cliente) {
        var novaConta = new Conta();

        String numeroConta = generateSequentialNumeroConta();
        novaConta.setNumeroConta(numeroConta);
        novaConta.setLimite(setLimiteConformeTipoCliente(cliente));
        novaConta.setCpfCliente(cliente.getCpf());
        novaConta.setSaldo(novaConta.getLimite());
        novaConta.setDataCriacao(Date.from(ZonedDateTime.now().toInstant()));

        contaRepository.save(novaConta);

        return "Conta criada com sucesso!";
    }

    public Conta salvarConta(Conta conta) {
        return contaRepository.save(conta);
    }

     public Conta buscarContaPorNumeroConta(String numeroConta) throws ContaNaoEncontradaException {
        return contaRepository.findByNumeroConta(numeroConta)
                .orElseThrow(() -> new ContaNaoEncontradaException(numeroConta));
     }

     public String consultarSaldo(String numeroConta) throws ContaNaoEncontradaException {
        var conta = contaRepository.findByNumeroConta(numeroConta).orElseThrow(() -> new ContaNaoEncontradaException(numeroConta));
        return "O limite disponível é:" +conta.getSaldo();
     }

    public String pagarFaturaIntegralmente(String numeroConta) throws ContaNaoEncontradaException {
        var conta = buscarContaPorNumeroConta(numeroConta);
        conta.setSaldo(conta.getLimite());
        salvarConta(conta);
        return ("Fatura paga com sucesso!");
    }

    private double setLimiteConformeTipoCliente(Cliente cliente) {
        return cliente.getTipoCliente().getLimiteCreditoInicial();
    }

    private String generateSequentialNumeroConta() {
        Long maxNumeroConta = contaRepository.findMaxNumeroConta();
        Long nextNumeroConta = (maxNumeroConta != null) ? maxNumeroConta + 1 : 1;

        return String.format("%05d", nextNumeroConta);
    }
}
