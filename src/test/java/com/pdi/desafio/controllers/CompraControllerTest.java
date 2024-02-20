package com.pdi.desafio.controllers;

import com.pdi.desafio.Fixture.CompraRequestDTOFixture;
import com.pdi.desafio.exceptions.CompraNaoAutorizadaException;
import com.pdi.desafio.exceptions.ContaNaoEncontradaException;
import com.pdi.desafio.models.DTOs.CompraRequestDTO;
import com.pdi.desafio.services.CompraService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureMockMvc
class CompraControllerTest {

    private static final String NUMERO_CONTA = "00001";
    private static final Double VALOR_COMPRA_999 = 999D;
    private static final Double VALOR_COMPRA_20000 = 20000D;

    @Autowired
    private MockMvc mockMvc;
    @Mock
    private CompraService compraServiceMock;
    @InjectMocks
    private CompraController compraController;

    @Test
    public void fazerNovaCompraCompraAutorizada() throws CompraNaoAutorizadaException, ContaNaoEncontradaException {
        CompraRequestDTO compraRequest = CompraRequestDTOFixture.build(NUMERO_CONTA, VALOR_COMPRA_999);

        Mockito.doNothing().when(compraServiceMock).efetuarCompra(compraRequest.numeroConta(), compraRequest.valor());

        ResponseEntity<String> responseEntity = compraController.fazerNovaCompra(compraRequest);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("Compra efetuada com sucesso!", responseEntity.getBody());
    }

    @Test
    public void fazerNovaCompraNaoAutorizada() throws CompraNaoAutorizadaException, ContaNaoEncontradaException {
        CompraRequestDTO compraRequest = CompraRequestDTOFixture.build(NUMERO_CONTA, VALOR_COMPRA_20000);

        Mockito.doThrow(new CompraNaoAutorizadaException(VALOR_COMPRA_20000)).when(compraServiceMock).efetuarCompra(compraRequest.numeroConta(), compraRequest.valor());

        assertThrows(CompraNaoAutorizadaException.class, () -> compraController.fazerNovaCompra(compraRequest));
    }
}