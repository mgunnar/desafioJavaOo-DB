package com.pdi.desafio.services;

import com.pdi.desafio.exceptions.ContaNaoEncontradaException;
import com.pdi.desafio.exceptions.CpfNaoEncontradoException;
import com.pdi.desafio.models.Cliente;
import com.pdi.desafio.models.Conta;
import com.pdi.desafio.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private ClienteService clienteService;

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

    private String generateSequentialNumeroConta() {
        Long maxNumeroConta = contaRepository.findMaxNumeroConta();
        Long nextNumeroConta = (maxNumeroConta != null) ? maxNumeroConta + 1 : 1;

        return String.format("%05d", nextNumeroConta);
    }

     public Conta buscarContaPorCpf(String cpf) throws CpfNaoEncontradoException {
        return contaRepository.findByCpfCliente(cpf)
                .orElseThrow(() -> new CpfNaoEncontradoException(cpf));
     }

     public Conta buscarContaPorNumeroConta(String numeroConta) throws ContaNaoEncontradaException {
        return contaRepository.findByNumeroConta(numeroConta)
                .orElseThrow(() -> new ContaNaoEncontradaException(numeroConta));
     }

     public Double consultarSaldo(String numeroConta) throws ContaNaoEncontradaException {
        var conta = contaRepository.findByNumeroConta(numeroConta).orElseThrow(() -> new ContaNaoEncontradaException(numeroConta));
        return conta.getSaldo();
     }

     public Cliente bucarClientePorNumeroConta(String numeroConta) throws ContaNaoEncontradaException, CpfNaoEncontradoException {
        Optional<Conta> conta = contaRepository.findByNumeroConta(numeroConta);

        return clienteService.buscarClientePorCpf(conta.get().getCpfCliente());
     }


    private double setLimiteConformeTipoCliente(Cliente cliente) {
        return cliente.getTipoCliente().getLimiteCreditoInicial();
    }
}
