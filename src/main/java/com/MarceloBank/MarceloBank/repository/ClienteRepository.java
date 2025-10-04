package com.MarceloBank.MarceloBank.repository;

import com.MarceloBank.MarceloBank.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>
{

    Optional<Cliente> findByCpf(String cpf);
    List<Cliente> findByNomeContainingIgnoreCase(String nome);
    Optional<Cliente> findByEmail(String email);

    @Query("SELECT c FROM Cliente c JOIN c.contas ct WHERE ct.status = 'ATIVA'")
    List<Cliente> findClientesComContasAtivas();

    @Query("SELECT c FROM Cliente c JOIN c.emprestimos e WHERE e.status = 'ATIVO'")
    List<Cliente> findClientesComEmprestimosAtivos();

    @Query("SELECT ct.status, COUNT(c) FROM Cliente c JOIN c.contas ct GROUP BY ct.status")
    List<Object[]> countClientesPorStatusConta();
}