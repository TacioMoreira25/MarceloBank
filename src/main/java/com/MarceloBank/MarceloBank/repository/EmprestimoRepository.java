package com.MarceloBank.MarceloBank.repository;

import com.MarceloBank.MarceloBank.model.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Integer>
{
    List<Emprestimo> findByClienteCpf(String cpf);

    @Query("SELECT e.cliente.cpf, SUM(e.saldoDevedor) FROM Emprestimo " +
                "e WHERE e.cliente.cpf = :cpf GROUP BY e.cliente.cpf")
        List<Object[]> findSaldoDevedorPorCliente(@Param("cpf") String cpf);
}
