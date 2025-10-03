package com.MarceloBank.MarceloBank.repository;

import com.MarceloBank.MarceloBank.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    // Busca por CPF (unique)
    Optional<Cliente> findByCpf(String cpf);

    // Busca por nome (contendo)
    List<Cliente> findByNomeContainingIgnoreCase(String nome);

    // Busca por email
    Optional<Cliente> findByEmail(String email);

    // Clientes com contas ativas
    @Query("SELECT DISTINCT c FROM Cliente c JOIN c.contas ct WHERE ct.status = 'ATIVA'")
    List<Cliente> findClientesComContasAtivas();

    // Clientes com empréstimos ativos
    @Query("SELECT DISTINCT c FROM Cliente c JOIN c.emprestimos e WHERE e.status = 'ATIVO'")
    List<Cliente> findClientesComEmprestimosAtivos();

    // Contagem de clientes por status de conta
    @Query("SELECT ct.status, COUNT(DISTINCT c) FROM Cliente c JOIN c.contas ct GROUP BY ct.status")
    List<Object[]> countClientesPorStatusConta();
}