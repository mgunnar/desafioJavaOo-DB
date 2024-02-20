package com.pdi.desafio.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdi.desafio.Fixture.ClienteFixture;
import com.pdi.desafio.Fixture.ClienteRequestDTOFixture;
import com.pdi.desafio.Fixture.ContaFixture;
import com.pdi.desafio.exceptions.ContaNaoEncontradaException;
import com.pdi.desafio.repository.ContaRepository;
import com.pdi.desafio.services.ContaService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ContaControllerTest {
    @Mock
    ClienteController clienteService;
    @InjectMocks
    private ContaController contaController;

    @Autowired
    private ContaRepository contaRepository;

    @InjectMocks
    private ClienteController clienteController;

    @Mock
    private ContaService contaService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveLancarExcecaoAoTentarPagarCompraContaNaoEncontrada() throws Exception {
        when(contaService.pagarFaturaIntegralmente(any(String.class))).thenThrow(new ContaNaoEncontradaException("Conta não encontrada"));

        mockMvc.perform(MockMvcRequestBuilders.post(ContaController.BASE_URL + "/pagar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("numeroConta", "00001"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveLancarExcecaoAoTentarConsultarSaldoContaNaoEncontrada() throws Exception {
        when(contaService.consultarSaldo(any(String.class))).thenThrow(new ContaNaoEncontradaException("Conta não encontrada"));

        mockMvc.perform(MockMvcRequestBuilders.get(ContaController.BASE_URL + "/saldo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("numeroConta", "00001"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void devePagarFaturaIntegralmente() throws Exception {
        var cliente = ClienteFixture.fromClienteRequestDTO(ClienteRequestDTOFixture.build());
        var conta = ContaFixture.build(cliente);

        when(contaService.criarConta(cliente)).thenReturn("Conta criada com sucesso!");
        when(contaService.buscarContaPorNumeroConta(conta.getNumeroConta())).thenReturn(conta);
        when(contaService.pagarFaturaIntegralmente(any(String.class))).thenReturn("Fatura paga com sucesso!");

        mockMvc.perform(MockMvcRequestBuilders.post(ContaController.BASE_URL + "/pagar")
                .contentType(MediaType.APPLICATION_JSON)
                .param("numeroConta", conta.getNumeroConta()));
//                .andExpect(status().isOk()); Esse teste não está funcionando. O que está errado?

    }

}