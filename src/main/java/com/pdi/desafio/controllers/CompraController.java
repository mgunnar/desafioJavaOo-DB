package com.pdi.desafio.controllers;

import com.pdi.desafio.exceptions.CompraNaoAutorizadaException;
import com.pdi.desafio.models.Compra;
import com.pdi.desafio.models.DTOs.CompraRequestDTO;
import com.pdi.desafio.services.CompraService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(path = CompraController.BASE_URL)
public class CompraController {

    public static final String BASE_URL = "/v1/compras";

    @Autowired
    private CompraService compraService;

    @PostMapping("/")
    public ResponseEntity<Compra> fazerNovaCompra(@RequestBody CompraRequestDTO compraRequest) throws CompraNaoAutorizadaException {
        var compra = compraService.efetuarCompra(compraRequest.numeroConta(), compraRequest.valor());
        return ResponseEntity.ok(compra);
    }
}
