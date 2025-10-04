package com.MarceloBank.MarceloBank.repository;

import com.MarceloBank.MarceloBank.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface ContaRepository extends JpaRepository<Conta, Integer>
{

    List<Conta> findByClienteIdCliente(Integer idCliente);
    List<Conta> findByAgenciaCodigoAgencia(Integer codigoAgencia);

}