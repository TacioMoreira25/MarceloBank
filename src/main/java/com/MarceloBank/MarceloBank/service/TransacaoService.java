package com.MarceloBank.MarceloBank.service;

import com.MarceloBank.MarceloBank.enums.StatusTransacao;
import com.MarceloBank.MarceloBank.model.Transacao;
import com.MarceloBank.MarceloBank.repository.ContaRepository;
import com.MarceloBank.MarceloBank.repository.TransacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TransacaoService {
    private final TransacaoRepository transacaoRepository;
    private final ContaRepository contaRepository;

    public TransacaoService(TransacaoRepository transacaoRepository, ContaRepository contaRepository) {
        this.transacaoRepository = transacaoRepository;
        this.contaRepository = contaRepository;
    }

    // EXTRATO DETALHADO COM FILTROS
    public List<Transacao> getExtratoComFiltros(Integer contaId, Date dataInicio, Date dataFim, StatusTransacao status) {
        if (dataInicio != null && dataFim != null) {
            return transacaoRepository.findExtratoPorPeriodo(contaId, dataInicio, dataFim);
        } else if (status != null) {
            return transacaoRepository.findByStatus(status);
        } else {
            return transacaoRepository.findTransacoesByConta(contaId);
        }
    }

    // ESTATÍSTICAS DE TRANSAÇÕES
    public Map<String, Object> getEstatisticasTransacoes() {
        List<Object[]> volumeDiario = transacaoRepository.findVolumeDiarioTransacionado();
        List<Transacao> topTransacoes = transacaoRepository.findTop5MaioresTransacoes();

        Map<String, Object> estatisticas = new HashMap<>();
        estatisticas.put("volumeDiario", volumeDiario);
        estatisticas.put("topTransacoes", topTransacoes);

        return estatisticas;
    }
}