package com.pdi.desafio.controllers;

import com.pdi.desafio.exceptions.ContaNaoEncontradaException;
import com.pdi.desafio.models.Conta;
import com.pdi.desafio.services.ContaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(path = ContaController.BASE_URL)
public class ContaController {
    public static final String BASE_URL = "/v1/contas";

    @Autowired
    private ContaService contaService;

    @PostMapping("/pagar")
    public ResponseEntity<String> pagarCompra(@RequestParam String numeroConta) throws ContaNaoEncontradaException {
        var compra = contaService.pagarFaturaIntegralmente(numeroConta);
        return ResponseEntity.ok(compra);
    }

    @GetMapping("/saldo")
    public ResponseEntity<String> consultarSaldo(@RequestParam String numeroConta) throws ContaNaoEncontradaException {
        var saldo = contaService.consultarSaldo(numeroConta);
        return ResponseEntity.ok(saldo);
    }

    @GetMapping("/conta")
    public ResponseEntity<Conta> consultarConta(@RequestParam String numeroConta) throws ContaNaoEncontradaException {
        var conta = contaService.buscarContaPorNumeroConta(numeroConta);
        return ResponseEntity.ok(conta);
    }
}
