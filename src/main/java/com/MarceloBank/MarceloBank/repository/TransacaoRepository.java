package com.MarceloBank.MarceloBank.repository;

import com.MarceloBank.MarceloBank.enums.StatusTransacao;
import com.MarceloBank.MarceloBank.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Date;
import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Integer>
{
    @Query("SELECT t FROM Transacao t WHERE t.contaOrigem.numeroConta = :contaId OR " +
            "t.contaDestino.numeroConta = :contaId ORDER BY t.dataTransacao DESC, " +
            "t.horaTransacao DESC")
    List<Transacao> findTransacoesByConta(@Param("contaId") Integer contaId);

    List<Transacao> findByStatus(StatusTransacao status);

    @Query("SELECT t FROM Transacao t WHERE t.dataTransacao BETWEEN :inicio " +
            "AND :fim ORDER BY t.dataTransacao, t.horaTransacao")
    List<Transacao> findTransacoesPorPeriodo(@Param("inicio") Date inicio,
                                             @Param("fim") Date fim);

    @Query("SELECT t FROM Transacao t WHERE (t.contaOrigem.numeroConta = :contaId " +
            "OR t.contaDestino.numeroConta = :contaId) AND t.dataTransacao BETWEEN " +
            ":inicio AND :fim ORDER BY t.dataTransacao DESC, t.horaTransacao DESC")
    List<Transacao> findExtratoPorPeriodo(@Param("contaId") Integer contaId,
                                          @Param("inicio") Date inicio,
                                          @Param("fim") Date fim);

    @Query("SELECT t.dataTransacao, SUM(t.valor) FROM Transacao t WHERE t.status " +
            "= 'CONCLUIDA' GROUP BY t.dataTransacao ORDER BY t.dataTransacao DESC")
    List<Object[]> findVolumeDiarioTransacionado();

    @Query("SELECT t FROM Transacao t WHERE t.status = 'CONCLUIDA' " +
            "ORDER BY t.valor DESC LIMIT 5")
    List<Transacao> findTop5MaioresTransacoes();
}