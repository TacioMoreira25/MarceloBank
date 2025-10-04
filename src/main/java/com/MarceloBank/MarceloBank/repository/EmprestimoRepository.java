package com.MarceloBank.MarceloBank.repository;

import com.MarceloBank.MarceloBank.model.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Integer>
{
    List<Emprestimo> findByClienteIdCliente(Integer idCliente);

    @Query("SELECT e.cliente.idCliente, SUM(e.saldoDevedor) FROM Emprestimo " +
            "e WHERE e.status = 'ATIVO' GROUP BY e.cliente.idCliente")
    List<Object[]> findSaldoDevedorPorCliente();
}