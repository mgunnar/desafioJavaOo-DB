package com.pdi.desafio.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdi.desafio.Fixture.ClienteFixture;
import com.pdi.desafio.Fixture.ClienteRequestDTOFixture;
import com.pdi.desafio.models.Cliente;
import com.pdi.desafio.models.DTOs.ClienteRequestDTO;
import com.pdi.desafio.services.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ClienteControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCadastrarNovoCliente() throws Exception {
        var clienteRequestDTO = ClienteRequestDTOFixture.build();
        var cliente = ClienteFixture.fromClienteRequestDTO(clienteRequestDTO);

        when(clienteService.cadastrarNovoCliente(any(ClienteRequestDTO.class))).thenReturn(cliente);

        mockMvc.perform(MockMvcRequestBuilders.post(ClienteController.BASE_URL + "/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteRequestDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void deveBuscarTodosClientes() throws Exception {
        List<Cliente> listaClientes = Collections.singletonList(ClienteFixture.build());

        when(clienteService.buscarTodosOsClientes()).thenReturn(listaClientes);

        mockMvc.perform(get(ClienteController.BASE_URL + "/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void deveBuscarClientePorCpf() throws Exception {
        var clienteRequestDTO = ClienteRequestDTOFixture.build();
        var cliente = ClienteFixture.fromClienteRequestDTO(clienteRequestDTO);

        when(clienteService.cadastrarNovoCliente(any(ClienteRequestDTO.class))).thenReturn(cliente);
        when(clienteService.buscarClientePorCpf(cliente.getCpf())).thenReturn(cliente);

        mockMvc.perform(MockMvcRequestBuilders.post(ClienteController.BASE_URL + "/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteRequestDTO)));

        mockMvc.perform(get(ClienteController.BASE_URL + "/{cpf}", cliente.getCpf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void deveBuscarClienteEContaPorCpf() throws Exception {
        var clienteRequestDTO = ClienteRequestDTOFixture.build();
        var cliente = ClienteFixture.fromClienteRequestDTO(clienteRequestDTO);

        when(clienteService.cadastrarNovoCliente(any(ClienteRequestDTO.class))).thenReturn(cliente);
        when(clienteService.buscarClientePorCpf(cliente.getCpf())).thenReturn(cliente);

        mockMvc.perform(MockMvcRequestBuilders.post(ClienteController.BASE_URL + "/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteRequestDTO)));

        mockMvc.perform(get(ClienteController.BASE_URL + "/{cpf}/conta", cliente.getCpf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }
}