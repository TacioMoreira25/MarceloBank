package com.MarceloBank.MarceloBank.repository;

import com.MarceloBank.MarceloBank.enums.TipoConta;
import com.MarceloBank.MarceloBank.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ContaRepository extends JpaRepository<Conta, Integer> {

    List<Conta> findByClienteIdCliente(Integer idCliente);
    List<Conta> findByAgenciaCodigoAgencia(Integer codigoAgencia);
    List<Conta> findByTipoConta(TipoConta tipoConta);
    List<Conta> findByStatus(String status);

    // ✅ CORRIGIDO
    @Query("SELECT c FROM Conta c WHERE c.numeroConta = :numero AND c.saldo > :saldoMinimo")
    Optional<Conta> findContaComSaldoMinimo(@Param("numero") Integer numero,
                                            @Param("saldoMinimo") BigDecimal saldoMinimo);

    // ✅ CORRIGIDO
    @Query("SELECT a.nomeAgencia, COALESCE(SUM(c.saldo), 0) FROM Agencia a LEFT JOIN a.contas c GROUP BY a.codigoAgencia, a.nomeAgencia")
    List<Object[]> findSaldoTotalPorAgencia();

    // ✅ CORRIGIDO - Contas com saldo negativo
    @Query("SELECT c FROM Conta c WHERE c.saldo < 0")
    List<Conta> findContasComSaldoNegativo();

    // ✅ CORRIGIDO - Buscar conta com cartões
    @Query("SELECT c FROM Conta c LEFT JOIN FETCH c.cartoes WHERE c.numeroConta = :numero")
    Optional<Conta> findContaComCartoes(@Param("numero") Integer numero);
}