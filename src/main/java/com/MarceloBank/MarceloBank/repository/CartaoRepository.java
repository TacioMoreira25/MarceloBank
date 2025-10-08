package com.MarceloBank.MarceloBank.repository;

import com.MarceloBank.MarceloBank.model.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface CartaoRepository extends JpaRepository<Cartao, Integer>
{
    @Query("SELECT c FROM Cartao c WHERE c.conta.cliente.cpf = :cpf")
    List<Cartao> findCartoesByClienteCpf(@Param("cpf") String cpf);
}