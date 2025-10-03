package com.MarceloBank.MarceloBank.repository;

import com.MarceloBank.MarceloBank.enums.TipoCartao;
import com.MarceloBank.MarceloBank.model.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface CartaoRepository extends JpaRepository<Cartao, Integer> {

    // Buscar cartões por conta
    List<Cartao> findByContaNumeroConta(Integer numeroConta);

    // Buscar cartões por tipo
    List<Cartao> findByTipoCartao(TipoCartao tipoCartao);

    // Buscar cartões por status
    List<Cartao> findByStatus(String status);

    // Buscar cartões que vão expirar
    @Query("SELECT c FROM Cartao c WHERE c.dataValidade BETWEEN :inicio AND :fim AND c.status = 'ATIVO'")
    List<Cartao> findCartoesProximosExpiracao(@Param("inicio") Date inicio,
                                              @Param("fim") Date fim);

    // Cartões por cliente
    @Query("SELECT c FROM Cartao c WHERE c.conta.cliente.idCliente = :clienteId")
    List<Cartao> findCartoesByClienteId(@Param("clienteId") Integer clienteId);

    // Cartões com limite maior que X
    @Query("SELECT c FROM Cartao c WHERE c.limite > :limiteMinimo")
    List<Cartao> findCartoesComLimiteAlto(@Param("limiteMinimo") BigDecimal limiteMinimo);
}