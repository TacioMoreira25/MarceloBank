package com.MarceloBank.MarceloBank.service;

import com.MarceloBank.MarceloBank.model.Agencia;
import com.MarceloBank.MarceloBank.repository.AgenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgenciaService {
    private final AgenciaRepository agenciaRepository;

    @Autowired
    public AgenciaService(AgenciaRepository agenciaRepository) {
        this.agenciaRepository = agenciaRepository;
    }

    public Agencia criarAgencia(Agencia agencia) {
        return agenciaRepository.save(agencia);
    }
}

