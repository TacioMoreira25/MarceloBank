package com.MarceloBank.MarceloBank.repository;

import com.MarceloBank.MarceloBank.enums.StatusEmprestimo;
import com.MarceloBank.MarceloBank.model.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Date;
import java.util.List;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Integer> {

    // Empréstimos por cliente
    List<Emprestimo> findByClienteId(Integer clienteId);

    // Empréstimos por status
    List<Emprestimo> findByStatus(StatusEmprestimo status);

    // Empréstimos aprovados no mês
    @Query("SELECT e FROM Emprestimo e WHERE e.status = 'APROVADO' AND MONTH(e.dataAprovacao) = MONTH(CURRENT_DATE) AND YEAR(e.dataAprovacao) = YEAR(CURRENT_DATE)")
    List<Emprestimo> findEmprestimosAprovadosEsteMes();

    // Saldo devedor total por cliente
    @Query("SELECT e.cliente.id, SUM(e.saldoDevedor) FROM Emprestimo e WHERE e.status = 'ATIVO' GROUP BY e.cliente.id")
    List<Object[]> findSaldoDevedorPorCliente();

    // Empréstimos com parcelas em atraso
    @Query("SELECT e FROM Emprestimo e WHERE e.status = 'ATIVO' AND e.saldoDevedor > 0 AND e.dataAprovacao < :dataLimite")
    List<Emprestimo> findEmprestimosEmAtraso(@Param("dataLimite") Date dataLimite);

    // Top 10 maiores empréstimos
    @Query("SELECT e FROM Emprestimo e WHERE e.status = 'APROVADO' ORDER BY e.valorAprovado DESC LIMIT 10")
    List<Emprestimo> findTop10MaioresEmprestimos();
}