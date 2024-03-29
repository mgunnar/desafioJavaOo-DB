package com.pdi.desafio.services;

import com.pdi.desafio.exceptions.ContaNaoEncontradaException;
import com.pdi.desafio.models.Cliente;
import com.pdi.desafio.models.Conta;
import com.pdi.desafio.models.enums.TipoCliente;
import com.pdi.desafio.repository.ContaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Date;

@Service
@AllArgsConstructor
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    @Transactional
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

    @Transactional
    public Conta salvarConta(Conta conta) {
        return contaRepository.save(conta);
    }

    @Transactional
    public Conta buscarContaPorNumeroConta(String numeroConta) throws ContaNaoEncontradaException {
        return contaRepository.findByNumeroConta(numeroConta).orElseThrow(() -> new ContaNaoEncontradaException(numeroConta));
    }

    @Transactional
    public String consultarSaldo(String numeroConta) throws ContaNaoEncontradaException {
        var conta = contaRepository.findByNumeroConta(numeroConta).orElseThrow(() -> new ContaNaoEncontradaException(numeroConta));
        return "O limite disponível é:" + conta.getSaldo();
    }

    @Transactional
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

    public void aumentaLimiteDeCreditoSeDisponivel(Double valorCompra, Conta conta){
        var valorMinimoGastoParaAumentarLimite = conta.getCliente().getTipoCliente().getValorGastoParaAumentoLimite();
        var clientePodeTerAumentoDeLimite = conta.getCliente().getTipoCliente().isAumentaLimiteLiberado();

        if (clientePodeTerAumentoDeLimite && valorCompra >= valorMinimoGastoParaAumentarLimite) {
            conta.setLimite(conta.getLimite() + conta.getCliente().getTipoCliente().getValorDeAumentoLimite());
        }
    }

    public void descontaValorCompraDoLimiteDisponivel(Conta conta, Double valor){
        conta.setSaldo(conta.getSaldo() - valor);
    }
}
