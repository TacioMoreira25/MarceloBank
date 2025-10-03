package com.MarceloBank.MarceloBank.service;

import com.MarceloBank.MarceloBank.model.Cartao;
import com.MarceloBank.MarceloBank.model.Cliente;
import com.MarceloBank.MarceloBank.model.Conta;
import com.MarceloBank.MarceloBank.model.Emprestimo;
import com.MarceloBank.MarceloBank.repository.CartaoRepository;
import com.MarceloBank.MarceloBank.repository.ClienteRepository;
import com.MarceloBank.MarceloBank.repository.ContaRepository;
import com.MarceloBank.MarceloBank.repository.EmprestimoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ContaRepository contaRepository;
    private final EmprestimoRepository emprestimoRepository;
    private final CartaoRepository cartaoRepository;

    public ClienteService(ClienteRepository clienteRepository, ContaRepository contaRepository,
                          CartaoRepository cartaoRepository, EmprestimoRepository emprestimoRepository) {
        this.clienteRepository = clienteRepository;
        this.contaRepository = contaRepository;
        this.cartaoRepository = cartaoRepository;
        this.emprestimoRepository = emprestimoRepository;
    }

    public Cliente criarCliente(Cliente cliente) {
        // Valida se CPF já existe
        if (clienteRepository.findByCpf(cliente.getCpf()).isPresent()) {
            throw new RuntimeException("CPF já cadastrado: " + cliente.getCpf());
        }
        return clienteRepository.save(cliente);
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> buscarPorId(Integer id) {
        return clienteRepository.findById(id);
    }

    public Cliente atualizarCliente(Integer id, Cliente clienteAtualizado) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        cliente.setNome(clienteAtualizado.getNome());
        cliente.setEmail(clienteAtualizado.getEmail());
        cliente.setTelefone(clienteAtualizado.getTelefone());
        cliente.setEndereco(clienteAtualizado.getEndereco());

        return clienteRepository.save(cliente);
    }

    public void deletarCliente(Integer id) {
        clienteRepository.deleteById(id);
    }
    public Map<String, Object> getInfoCompletaCliente(Integer clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        List<Conta> contas = contaRepository.findByClienteId(clienteId);
        List<Cartao> cartoes = cartaoRepository.findCartoesByClienteId(clienteId);
        List<Emprestimo> emprestimos = emprestimoRepository.findByClienteId(clienteId);

        Map<String, Object> info = new HashMap<>();
        info.put("cliente", cliente);
        info.put("contas", contas);
        info.put("cartoes", cartoes);
        info.put("emprestimos", emprestimos);

        return info;
    }
}