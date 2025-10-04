package com.MarceloBank.MarceloBank.repository;

import com.MarceloBank.MarceloBank.enums.TipoCartao;
import com.MarceloBank.MarceloBank.model.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface CartaoRepository extends JpaRepository<Cartao, Integer>
{

    List<Cartao> findByContaNumeroConta(Integer numeroConta);

    List<Cartao> findByTipoCartao(TipoCartao tipoCartao);

    List<Cartao> findByStatus(String status);

    @Query("SELECT c FROM Cartao c WHERE c.dataValidade BETWEEN :inicio AND :fim AND c.status = 'ATIVO'")
    List<Cartao> findCartoesProximosExpiracao(@Param("inicio") Date inicio,
                                              @Param("fim") Date fim);

    @Query("SELECT c FROM Cartao c WHERE c.conta.cliente.idCliente = :clienteId")
    List<Cartao> findCartoesByClienteId(@Param("clienteId") Integer clienteId);

    @Query("SELECT c FROM Cartao c WHERE c.limite > :limiteMinimo")
    List<Cartao> findCartoesComLimiteAlto(@Param("limiteMinimo") BigDecimal limiteMinimo);
}