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

    // Buscar contas por cliente
    List<Conta> findByClienteId(Integer clienteId);

    // Buscar contas por agência
    List<Conta> findByAgenciaCodigoAgencia(Integer codigoAgencia);

    // Buscar contas por tipo
    List<Conta> findByTipoConta(TipoConta tipoConta);

    // Buscar contas por status
    List<Conta> findByStatus(String status);

    // Buscar conta específica com saldo maior que X
    @Query("SELECT c FROM Conta c WHERE c.numeroConta = :numero AND c.saldo > :saldoMinimo")
    Optional<Conta> findContaComSaldoMinimo(@Param("numero") Integer numero,
                                            @Param("saldoMinimo") BigDecimal saldoMinimo);

    // Somatório de saldo por agência
    @Query("SELECT a.nomeAgencia, SUM(c.saldo) FROM Conta c JOIN c.agencia a GROUP BY a.codigoAgencia, a.nomeAgencia")
    List<Object[]> findSaldoTotalPorAgencia();

    // Contas com saldo negativo
    @Query("SELECT c FROM Conta c WHERE c.saldo < 0")
    List<Conta> findContasComSaldoNegativo();

    // Buscar conta com cartões (usando JOIN FETCH para evitar Lazy Loading)
    @Query("SELECT c FROM Conta c LEFT JOIN FETCH c.cartoes WHERE c.numeroConta = :numero")
    Optional<Conta> findContaComCartoes(@Param("numero") Integer numero);
}