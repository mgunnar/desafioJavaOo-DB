package com.pdi.desafio.services;

import com.pdi.desafio.Fixture.ClienteFixture;
import com.pdi.desafio.Fixture.ContaFixture;
import com.pdi.desafio.exceptions.CompraNaoAutorizadaException;
import com.pdi.desafio.exceptions.ContaNaoEncontradaException;
import com.pdi.desafio.models.Cliente;
import com.pdi.desafio.models.Conta;
import com.pdi.desafio.models.enums.TipoCliente;
import com.pdi.desafio.repository.CompraRepository;
import com.pdi.desafio.repository.ContaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.ZonedDateTime;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

class CompraServiceTest {

    @InjectMocks
    private CompraService compraService;

    @Mock
    private ContaService contaServiceMock;

    @Mock
    private ClienteService clienteServiceMock;

    @Mock
    private ContaRepository contaRepositoryMock;

    @Mock
    private CompraRepository compraRepositoryMock;

    private static final String CPF = "12345678900";
    private static final String NOME = "Teste";
    private static final TipoCliente TIPO_CLIENTE = TipoCliente.A;
    private static final String NUMERO_CONTA = "00001";
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveRealizarCompra() throws ContaNaoEncontradaException, CompraNaoAutorizadaException {
        double valorCompra = 1000;

        var cliente = ClienteFixture.build();
        var conta = ContaFixture.build(cliente);

        when(contaServiceMock.buscarContaPorNumeroConta(conta.getNumeroConta())).thenReturn(conta);

        var resultado = compraService.efetuarCompra(conta.getNumeroConta(), valorCompra);

        assertEquals(conta.getNumeroConta(), resultado.getNumeroConta());
        assertEquals(valorCompra, resultado.getValor(), 0.0);

        verify(contaServiceMock, atLeastOnce()).buscarContaPorNumeroConta(conta.getNumeroConta());
    }

    @Test
    void naoAplicaDescontoCompra() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        double valorCompra = 1000;

        Method verificaDescontoCompra = CompraService.class.getDeclaredMethod("verificaDescontoCompra", Double.class, TipoCliente.class, Conta.class);

        verificaDescontoCompra.setAccessible(true);

        var conta = ContaFixture.build(ClienteFixture.build());

        conta.getCliente().setTipoCliente(TipoCliente.A);

        double resultado = (Double) verificaDescontoCompra.invoke(compraService, valorCompra, conta.getCliente().getTipoCliente(), conta);

        assertEquals(valorCompra, resultado, 0);
    }


    @Test
    void aplicaDescontoCompra() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        double valorCompra = 10000;
        double valorCompraComDesconto = 9000;
        var conta = ContaFixture.build(ClienteFixture.build());

        conta.getCliente().setTipoCliente(TipoCliente.A);

        Method verificaDescontoCompra = CompraService.class.getDeclaredMethod("verificaDescontoCompra", Double.class, TipoCliente.class, Conta.class);
        verificaDescontoCompra.setAccessible(true);

        double resultado = (Double) verificaDescontoCompra.invoke(compraService, valorCompra, conta.getCliente().getTipoCliente(), conta);

        assertEquals(valorCompraComDesconto, resultado, 0);
    }

    @Test
    void verificaDescontoCompraParametrosNulos() throws NoSuchMethodException {
        Double valorCompra = null;
        TipoCliente tipoCliente = null;
        Conta conta = null;

        Method metodoPrivado = CompraService.class.getDeclaredMethod("verificaDescontoCompra", Double.class, TipoCliente.class, Conta.class);
        metodoPrivado.setAccessible(true);

        InvocationTargetException exception = assertThrows(InvocationTargetException.class, () ->
                metodoPrivado.invoke(compraService, valorCompra, tipoCliente, conta));

        Throwable cause = exception.getCause();

        assertEquals(IllegalArgumentException.class, cause.getClass());
        assertEquals("valorCompra, tipoCliente e conta não podem ser nulos.", cause.getMessage());
    }

    @Test
    void efetuarCompraCompraNaoAutorizada() throws ContaNaoEncontradaException {
        var conta = ContaFixture.build(ClienteFixture.build());
        conta.getCliente().setTipoCliente(TipoCliente.A);

        when(contaServiceMock.buscarContaPorNumeroConta(anyString())).thenReturn(conta);

        assertThrows(CompraNaoAutorizadaException.class, () -> compraService.efetuarCompra("12345", 10001D));

        verify(contaServiceMock, never()).salvarConta(any());
    }

    @Test
    void efetuarCompraContaNaoEncontrada() throws ContaNaoEncontradaException {
        when(contaServiceMock.buscarContaPorNumeroConta(anyString()))
                .thenThrow(new ContaNaoEncontradaException(NUMERO_CONTA));

        ContaNaoEncontradaException exception = assertThrows(ContaNaoEncontradaException.class,
                () -> compraService.efetuarCompra(NUMERO_CONTA, 1000.0));

        Assertions.assertEquals("400 BAD_REQUEST - Conta não encontrada com número: "+ NUMERO_CONTA, exception.getMessage());

        verify(contaServiceMock, atLeastOnce()).buscarContaPorNumeroConta(NUMERO_CONTA);
    }

    @Test
    void verificaAumentoDelimiteAumentoDeveOcorrer() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        TipoCliente tipoCliente = TipoCliente.A;

        var conta = ContaFixture.build(ClienteFixture.build(CPF, NOME, tipoCliente));


        Method verificaAumentoDelimite = CompraService.class.getDeclaredMethod("verificaAumentoDelimite", Double.class, TipoCliente.class, Conta.class);
        verificaAumentoDelimite.setAccessible(true);

        verificaAumentoDelimite.invoke(compraService, 5000.0, tipoCliente, conta);

        assertEquals(10500.0, conta.getLimite(), 0.01);
    }


    private Conta configurarConta(Cliente cliente) {
        var novaConta = new Conta();
        novaConta.setNumeroConta(NUMERO_CONTA);
        novaConta.setLimite(cliente.getTipoCliente().getLimiteCreditoInicial());
        novaConta.setCpfCliente(cliente.getCpf());
        novaConta.setSaldo(novaConta.getLimite());
        novaConta.setDataCriacao(Date.from(ZonedDateTime.now().toInstant()));
        novaConta.setCliente(cliente);
        return novaConta;
    }

}