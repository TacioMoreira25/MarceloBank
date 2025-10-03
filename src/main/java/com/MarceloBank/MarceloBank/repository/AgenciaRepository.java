package com.MarceloBank.MarceloBank.repository;

import com.MarceloBank.MarceloBank.model.Agencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface AgenciaRepository extends JpaRepository<Agencia, Integer> {

    // Buscar por nome
    Optional<Agencia> findByNomeAgencia(String nomeAgencia);

    // Buscar por gerente
    List<Agencia> findByGerente(String gerente);

    // Agências com maior número de contas
    @Query("SELECT a, COUNT(c) as totalContas FROM Agencia a LEFT JOIN a.contas c GROUP BY a.codigoAgencia ORDER BY totalContas DESC")
    List<Object[]> findAgenciasComMaisContas();

    // Estatísticas por agência
    @Query("SELECT a.nomeAgencia, COUNT(c), SUM(c.saldo), AVG(c.saldo) FROM Agencia a LEFT JOIN a.contas c GROUP BY a.codigoAgencia, a.nomeAgencia")
    List<Object[]> findEstatisticasPorAgencia();
}


