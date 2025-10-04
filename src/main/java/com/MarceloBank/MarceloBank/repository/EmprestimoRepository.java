package com.MarceloBank.MarceloBank.repository;

import com.MarceloBank.MarceloBank.enums.StatusEmprestimo;
import com.MarceloBank.MarceloBank.model.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Date;
import java.util.List;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Integer>
{

    List<Emprestimo> findByClienteIdCliente(Integer idCliente);
    List<Emprestimo> findByStatus(StatusEmprestimo status);

    @Query("SELECT e FROM Emprestimo e WHERE e.status = 'APROVADO' AND FUNCTION" +
            "('MONTH', e.dataAprovacao) = FUNCTION('MONTH', CURRENT_DATE) " +
            "AND FUNCTION('YEAR', e.dataAprovacao) = FUNCTION('YEAR', CURRENT_DATE)")
    List<Emprestimo> findEmprestimosAprovadosEsteMes();

    @Query("SELECT e.cliente.idCliente, SUM(e.saldoDevedor) FROM Emprestimo " +
            "e WHERE e.status = 'ATIVO' GROUP BY e.cliente.idCliente")
    List<Object[]> findSaldoDevedorPorCliente();

    @Query("SELECT e FROM Emprestimo e WHERE e.status = 'ATIVO' AND e.saldoDevedor > 0 " +
            "AND e.dataAprovacao < :dataLimite")
    List<Emprestimo> findEmprestimosEmAtraso(@Param("dataLimite") Date dataLimite);

    @Query("SELECT e FROM Emprestimo e WHERE e.status = 'APROVADO' ORDER BY " +
            "e.valorAprovado DESC LIMIT 10")
    List<Emprestimo> findTop10MaioresEmprestimos();
}