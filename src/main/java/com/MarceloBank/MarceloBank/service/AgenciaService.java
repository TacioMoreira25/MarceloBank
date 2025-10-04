package com.MarceloBank.MarceloBank.service;

import com.MarceloBank.MarceloBank.model.Agencia;
import com.MarceloBank.MarceloBank.repository.AgenciaRepository;
import com.MarceloBank.MarceloBank.repository.ContaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AgenciaService {
    private final AgenciaRepository agenciaRepository;
    private final ContaRepository contaRepository;

    public AgenciaService(AgenciaRepository agenciaRepository, ContaRepository
            contaRepository) {
        this.agenciaRepository = agenciaRepository;
        this.contaRepository = contaRepository;
    }

    public Agencia criarAgencia(Agencia agencia) {
        return agenciaRepository.save(agencia);
    }

}