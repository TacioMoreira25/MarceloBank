package com.MarceloBank.MarceloBank.repository;

import com.MarceloBank.MarceloBank.model.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Integer>
{
    List<Emprestimo> findByClienteIdCliente(Integer idCliente);

    @Query("SELECT e.cliente.idCliente, SUM(e.saldoDevedor) FROM Emprestimo " +
                "e WHERE e.cliente.idCliente = :clienteId GROUP BY e.cliente.idCliente")
        List<Object[]> findSaldoDevedorPorCliente(@Param("clienteId") Integer clienteId);
}
