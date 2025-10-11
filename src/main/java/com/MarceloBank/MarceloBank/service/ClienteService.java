package com.MarceloBank.MarceloBank.service;

import com.MarceloBank.MarceloBank.model.*;
import com.MarceloBank.MarceloBank.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
public class ClienteService
{
    private final ClienteRepository clienteRepository;
    private final ContaRepository contaRepository;
    private final EmprestimoRepository emprestimoRepository;
    private final CartaoRepository cartaoRepository;

    public ClienteService(ClienteRepository clienteRepository,
                          ContaRepository contaRepository, CartaoRepository
                                  cartaoRepository, EmprestimoRepository
                                  emprestimoRepository)
    {
        this.clienteRepository = clienteRepository;
        this.contaRepository = contaRepository;
        this.cartaoRepository = cartaoRepository;
        this.emprestimoRepository = emprestimoRepository;
    }

    public Cliente criarCliente(Cliente cliente)
    {
        if (clienteRepository.findByCpf(cliente.getCpf()).isPresent()) {
            throw new RuntimeException("CPF já cadastrado: " + cliente.getCpf());
        }
        return clienteRepository.save(cliente);
    }

    public List<Cliente> listarTodos()
    {
        return clienteRepository.findAll();
    }
    public Cliente getClientePorCpf(String cpf) {
        return clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

    public Cliente atualizarCliente(String cpf, Cliente clienteAtualizado)
    {
        Cliente cliente = clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        cliente.setNome(clienteAtualizado.getNome());
        cliente.setEmail(clienteAtualizado.getEmail());
        cliente.setTelefone(clienteAtualizado.getTelefone());
        cliente.setEndereco(clienteAtualizado.getEndereco());

        return clienteRepository.save(cliente);
    }

    public void deletarCliente(String cpf) {
        Cliente cliente = clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        List<Conta> contas = contaRepository.findByClienteCpf(cpf);
        if (!contas.isEmpty()) {
            throw new RuntimeException("Não é possível deletar o cliente," +
                    " pois existem contas associadas.");
        }
        clienteRepository.delete(cliente);
    }

    public Map<String, Object> getInfoCompletaCliente(String cpf)
    {
        Cliente cliente = clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        List<Conta> contas = contaRepository.findByClienteCpf(cpf);
        List<Cartao> cartoes = cartaoRepository.findCartoesByClienteCpf(cpf);
        List<Emprestimo> emprestimos = emprestimoRepository.findByClienteCpf(cpf);

        Map<String, Object> info = new HashMap<>();
        info.put("cliente", cliente);
        info.put("contas", contas);
        info.put("cartoes", cartoes);
        info.put("emprestimos", emprestimos);

        return info;
    }
}