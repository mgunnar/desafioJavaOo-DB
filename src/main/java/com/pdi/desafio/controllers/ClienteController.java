package com.pdi.desafio.controllers;

import com.pdi.desafio.exceptions.CpfNaoEncontradoException;
import com.pdi.desafio.exceptions.RuntimeTransacaoNaoConcluida;
import com.pdi.desafio.models.Cliente;
import com.pdi.desafio.dtos.ClienteRequestDTO;
import com.pdi.desafio.dtos.ContasResponseDTO;
import com.pdi.desafio.services.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = ClienteController.BASE_URL)
public class ClienteController {
    public static final String BASE_URL = "/v1/clientes";

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }


    @PostMapping("/")
    public ResponseEntity<Cliente> cadastrarNovoCliente(@RequestBody ClienteRequestDTO cliente) {
        var clienteSalvo = clienteService.cadastrarNovoCliente(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
    }

    @GetMapping("/")
        public ResponseEntity<List<Cliente>> buscarTodosClientes() {
        var listaClientes = clienteService.buscarTodosOsClientes();
        return ResponseEntity.ok(listaClientes);
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Cliente> buscarClientePorCpf(@PathVariable String cpf) throws CpfNaoEncontradoException {
        var cliente = clienteService.buscarClientePorCpf(cpf);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping("/{cpf}/conta")
    public ResponseEntity<ContasResponseDTO> buscarClienteEContaPorCpf(@PathVariable String cpf) throws CpfNaoEncontradoException {
        var clienteEConta = clienteService.buscarContaPorCpf(cpf);
        return ResponseEntity.ok(clienteEConta);
    }

}

