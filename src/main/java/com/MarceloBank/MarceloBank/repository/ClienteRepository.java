package com.MarceloBank.MarceloBank.repository;

import com.MarceloBank.MarceloBank.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>
{
    Optional<Cliente> findByCpf(String cpf);

    long deleteByCpf(String cpf);
}