package com.pdi.desafio.services;

import com.pdi.desafio.exceptions.ContaNaoEncontradaException;
import com.pdi.desafio.models.Cliente;
import com.pdi.desafio.models.Conta;
import com.pdi.desafio.models.enums.TipoCliente;
import com.pdi.desafio.repository.ContaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContaServiceTest {

    @InjectMocks
    private ContaService contaService;

    private ClienteService clienteService;

    ContaRepository contaRepositoryMock = Mockito.mock(ContaRepository.class);

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        contaService = new ContaService(contaRepositoryMock, clienteService);
    }

    private static final String CPF = "12345678900";
    private static final String NOME = "Teste";
    private static final TipoCliente TIPO_CLIENTE = TipoCliente.A;
    private static final String NUMERO_CONTA = "00001";

    @Test
    void deveCriarConta() {
        var cliente = new Cliente();
        cliente.setCpf(CPF);
        cliente.setNome(NOME);
        cliente.setTipoCliente(TIPO_CLIENTE);

        var novaConta = configurarConta(cliente);

        when(contaRepositoryMock.save(novaConta)).thenReturn(novaConta);

        String resultado = contaService.criarConta(cliente);

        verify(contaRepositoryMock, atLeastOnce()).save(any(Conta.class));

        assertEquals("Conta criada com sucesso!", resultado);

        assertEquals(NUMERO_CONTA, novaConta.getNumeroConta());
        assertEquals(TIPO_CLIENTE.getLimiteCreditoInicial(), novaConta.getLimite());
        assertEquals(CPF, novaConta.getCpfCliente());
        assertEquals(novaConta.getLimite(), novaConta.getSaldo());
    }

    @Test
    void deveSalvarConta() {
        Conta contaParaSalvar = new Conta();

        when(contaRepositoryMock.save(contaParaSalvar)).thenReturn(contaParaSalvar);

        Conta contaSalva = contaService.salvarConta(contaParaSalvar);

        verify(contaRepositoryMock, times(1)).save(contaParaSalvar); // Verifica se o método save foi chamado exatamente uma vez com a contaParaSalvar

        assertEquals(contaParaSalvar, contaSalva);
    }

    @Test
    void deveBuscarContaPorNumeroConta() throws ContaNaoEncontradaException {

        String numeroConta = "00001";

        when(contaRepositoryMock.findByNumeroConta(numeroConta)).thenReturn(Optional.of(new Conta()));

        Conta contaEncontrada = contaService.buscarContaPorNumeroConta(numeroConta);

        verify(contaRepositoryMock, times(1)).findByNumeroConta(numeroConta);

        assertNotNull(contaEncontrada);
    }

    @Test
    void deveConsultarSaldo() throws ContaNaoEncontradaException {
        String numeroConta = "00001";

        when(contaRepositoryMock.findByNumeroConta(numeroConta)).thenReturn(Optional.of(new Conta()));

        String resultado = contaService.consultarSaldo(numeroConta);

        verify(contaRepositoryMock, times(1)).findByNumeroConta(numeroConta);

        assertTrue(resultado.contains("O limite disponível é:"));
    }


    @Test
    public void devePagarFaturaIntegralmente() throws ContaNaoEncontradaException {
        var conta = configurarConta(new Cliente(CPF, NOME, TIPO_CLIENTE));

        when(contaRepositoryMock.findByNumeroConta(NUMERO_CONTA)).thenReturn(Optional.of(conta));

        String resultado = contaService.pagarFaturaIntegralmente(NUMERO_CONTA);

        verify(contaRepositoryMock, times(1)).findByNumeroConta(NUMERO_CONTA);

        assertEquals("Fatura paga com sucesso!", resultado);
        assertEquals(conta.getLimite(), conta.getSaldo());
    }

    private Conta configurarConta(Cliente cliente) {
        var novaConta = new Conta();
        novaConta.setNumeroConta(NUMERO_CONTA);
        novaConta.setLimite(cliente.getTipoCliente().getLimiteCreditoInicial());
        novaConta.setCpfCliente(cliente.getCpf());
        novaConta.setSaldo(novaConta.getLimite());
        novaConta.setDataCriacao(Date.from(ZonedDateTime.now().toInstant()));
        return novaConta;
    }
}