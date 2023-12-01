package com.pdi.desafio.services;

import com.pdi.desafio.exceptions.CpfNaoEncontradoException;
import com.pdi.desafio.models.Cliente;
import com.pdi.desafio.models.DTOs.ClienteRequestDTO;
import com.pdi.desafio.models.DTOs.ContasResponseDTO;
import com.pdi.desafio.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    @Autowired
    @Lazy
    private ContaService contaService;

    public ClienteService(ClienteRepository clienteRepository, ContaService contaService) {
        this.contaService = contaService;
        this.clienteRepository = clienteRepository;
    }

    public Cliente cadastrarNovoCliente(ClienteRequestDTO cliente) {
        var novoCliente = new Cliente();

        novoCliente.setCpf(cliente.cpf());
        novoCliente.setNome(cliente.nome().toUpperCase());
        novoCliente.setTipoCliente(cliente.tipoCliente());

        contaService.criarConta(novoCliente);

        return clienteRepository.save(novoCliente);
    }

    public List<Cliente> buscarTodosOsClientes() {
        return clienteRepository.findAll();
    }

    public Cliente buscarClientePorCpf(String cpf) throws CpfNaoEncontradoException {
        return clienteRepository.findByCpf(cpf).orElseThrow(() -> new CpfNaoEncontradoException(cpf));
    }

    public ContasResponseDTO buscarContaPorCpf(String cpf) throws CpfNaoEncontradoException {
        var cliente = clienteRepository.findByCpf(cpf).orElseThrow(() -> new CpfNaoEncontradoException(cpf));
        return new ContasResponseDTO(
                cliente.getContas().get(0).getNumeroConta(),
                cliente.getContas().get(0).getLimite(),
                cliente.getContas().get(0).getSaldo(),
                cliente.getCpf(),
                cliente.getContas().get(0).getDataCriacao()
        );
    }




}
