package com.MarceloBank.MarceloBank.service;

import com.MarceloBank.MarceloBank.model.Agencia;
import com.MarceloBank.MarceloBank.model.Conta;
import com.MarceloBank.MarceloBank.repository.AgenciaRepository;
import com.MarceloBank.MarceloBank.repository.ContaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<String, Object> getRelatorioAgencia(Integer codigoAgencia)
    {
        Agencia agencia = agenciaRepository.findById(codigoAgencia)
                .orElseThrow(() -> new RuntimeException("Agência não encontrada"));

        List<Conta> contas = contaRepository.findByAgenciaCodigoAgencia(codigoAgencia);

        BigDecimal saldoTotal = contas.stream()
                .map(Conta::getSaldo)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        long totalClientes = contas.stream()
                .map(Conta::getCliente)
                .distinct()
                .count();

        Map<String, Object> relatorio = new HashMap<>();
        relatorio.put("agencia", agencia);
        relatorio.put("totalContas", contas.size());
        relatorio.put("totalClientes", totalClientes);
        relatorio.put("saldoTotal", saldoTotal);
        relatorio.put("contas", contas);

        return relatorio;
    }
}