package com.pdi.desafio.services;

import com.pdi.desafio.Fixture.ClienteFixture;
import com.pdi.desafio.Fixture.ClienteRequestDTOFixture;
import com.pdi.desafio.Fixture.ContaFixture;
import com.pdi.desafio.exceptions.CpfNaoEncontradoException;
import com.pdi.desafio.exceptions.RuntimeTransacaoNaoConcluida;
import com.pdi.desafio.models.Cliente;
import com.pdi.desafio.models.Conta;
import com.pdi.desafio.models.DTOs.ContasResponseDTO;
import com.pdi.desafio.repository.ClienteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ContaService contaServiceMock;

    @Mock
    private ClienteRepository clienteRepositoryMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCadastrarNovoCliente() throws RuntimeTransacaoNaoConcluida {
        var clienteRequest = ClienteRequestDTOFixture.build();
        var cliente = ClienteFixture.build(clienteRequest.cpf(), clienteRequest.nome(), clienteRequest.tipoCliente());

        when(clienteRepositoryMock.save(any(Cliente.class))).thenReturn(cliente);
        when(contaServiceMock.criarConta(any(Cliente.class))).thenReturn("Conta criada com sucesso!");

        Cliente clienteResult = clienteService.cadastrarNovoCliente(clienteRequest);

        Assertions.assertEquals(clienteResult.getNome(),clienteRequest.nome().toUpperCase());
        Assertions.assertEquals (clienteResult.getCpf(),clienteRequest.cpf());
        Assertions.assertEquals (clienteResult.getTipoCliente(),(clienteRequest.tipoCliente()));

        verify(clienteRepositoryMock, atLeastOnce()).save(any(Cliente.class));
        verify(contaServiceMock,atLeastOnce()).criarConta(any(Cliente.class));
    }

    @Test
    void deveBuscarTodosOsClientes() {
        when(clienteRepositoryMock.findAll()).thenReturn(Arrays.asList(ClienteFixture.build(), ClienteFixture.build()));

        var clientes = clienteService.buscarTodosOsClientes();

        assertFalse(clientes.isEmpty());
        assertEquals(2, clientes.size());
        verify(clienteRepositoryMock,atLeastOnce()).findAll();
    }

    @Test
    void deveBuscarClientePorCpf() throws CpfNaoEncontradoException {
        var cliente = ClienteFixture.build();

        when(clienteRepositoryMock.findByCpf(cliente.getCpf())).thenReturn(Optional.of(cliente));

        Cliente clienteEncontrado = clienteService.buscarClientePorCpf(cliente.getCpf());

        assertEquals(cliente, clienteEncontrado);
    }

    @Test
    void deveBuscarContaPorCpf() throws CpfNaoEncontradoException {
        Cliente cliente = ClienteFixture.build();
        Conta conta = ContaFixture.build(cliente);

        cliente.setContas(List.of(conta));

        when(clienteRepositoryMock.findByCpf(cliente.getCpf())).thenReturn(Optional.of(cliente));

        ContasResponseDTO contasResponseDTO = clienteService.buscarContaPorCpf(cliente.getCpf());

        assertEquals(conta.getNumeroConta(), contasResponseDTO.numeroConta());
        assertEquals(conta.getLimite(), contasResponseDTO.limite());
        assertEquals(conta.getSaldo(), contasResponseDTO.saldo());
        assertEquals(cliente.getCpf(), contasResponseDTO.cpfCliente());
        assertEquals(conta.getDataCriacao(), contasResponseDTO.dataCriacao());
    }
}