package com.MarceloBank.MarceloBank.repository;

import com.MarceloBank.MarceloBank.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Integer>
{
    @Query("SELECT t FROM Transacao t WHERE t.contaOrigem.numeroConta = :contaId OR " +
            "t.contaDestino.numeroConta = :contaId ORDER BY t.dataTransacao DESC, " +
            "t.horaTransacao DESC")
    List<Transacao> findTransacoesByConta(@Param("contaId") Integer contaId);
}