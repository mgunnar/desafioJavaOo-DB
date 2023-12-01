package com.pdi.desafio.services;

import com.pdi.desafio.Fixture.ClienteFixture;
import com.pdi.desafio.Fixture.ContaFixture;
import com.pdi.desafio.exceptions.ContaNaoEncontradaException;
import com.pdi.desafio.models.Conta;
import com.pdi.desafio.repository.ContaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContaServiceTest {

    private static final String NUMERO_CONTA = "00001";
    ContaRepository contaRepositoryMock = Mockito.mock(ContaRepository.class);
    @InjectMocks
    private ContaService contaService;
    private ClienteService clienteService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        contaService = new ContaService(contaRepositoryMock, clienteService);
    }

    @Test
    void deveCriarConta() {
        var cliente = ClienteFixture.build();
        var novaConta = ContaFixture.build(cliente);

        when(contaRepositoryMock.save(novaConta)).thenReturn(novaConta);

        String resultado = contaService.criarConta(cliente);

        verify(contaRepositoryMock, atLeastOnce()).save(any(Conta.class));

        assertEquals("Conta criada com sucesso!", resultado);
        assertEquals(cliente.getTipoCliente().getLimiteCreditoInicial(), novaConta.getLimite());
        assertEquals(cliente.getCpf(), novaConta.getCpfCliente());
        assertEquals(novaConta.getLimite(), novaConta.getSaldo());
    }

    @Test
    void deveSalvarConta() {
        var contaParaSalvar = ContaFixture.build(ClienteFixture.build());

        when(contaRepositoryMock.save(contaParaSalvar)).thenReturn(contaParaSalvar);

        var contaSalva = contaService.salvarConta(contaParaSalvar);

        verify(contaRepositoryMock, times(1)).save(contaParaSalvar);

        assertEquals(contaParaSalvar, contaSalva);
    }

    @Test
    void deveBuscarContaPorNumeroConta() throws ContaNaoEncontradaException {

        when(contaRepositoryMock.findByNumeroConta(NUMERO_CONTA)).thenReturn(Optional.of(new Conta()));

        Conta contaEncontrada = contaService.buscarContaPorNumeroConta(NUMERO_CONTA);

        verify(contaRepositoryMock, times(1)).findByNumeroConta(NUMERO_CONTA);

        assertNotNull(contaEncontrada);
    }

    @Test
    void deveConsultarSaldo() throws ContaNaoEncontradaException {

        when(contaRepositoryMock.findByNumeroConta(NUMERO_CONTA)).thenReturn(Optional.of(ContaFixture.build(ClienteFixture.build())));

        String resultado = contaService.consultarSaldo(NUMERO_CONTA);

        verify(contaRepositoryMock, times(1)).findByNumeroConta(NUMERO_CONTA);

        assertTrue(resultado.contains("O limite disponível é:"));
    }

    @Test
    public void devePagarFaturaIntegralmente() throws ContaNaoEncontradaException {
        var conta = ContaFixture.build(ClienteFixture.build());

        when(contaRepositoryMock.findByNumeroConta(conta.getNumeroConta())).thenReturn(Optional.of(conta));

        String resultado = contaService.pagarFaturaIntegralmente(conta.getNumeroConta());

        verify(contaRepositoryMock, times(1)).findByNumeroConta(conta.getNumeroConta());

        assertEquals("Fatura paga com sucesso!", resultado);
        assertEquals(conta.getLimite(), conta.getSaldo());
    }
}