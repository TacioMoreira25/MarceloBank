package com.MarceloBank.MarceloBank.controller;

import com.MarceloBank.MarceloBank.model.Transacao;
import com.MarceloBank.MarceloBank.repository.TransacaoRepository;
import com.MarceloBank.MarceloBank.dto.TransacaoResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/transacoes")
@CrossOrigin(origins = "*")
public class TransacaoController
{
    private final TransacaoRepository transacaoRepository;

    public TransacaoController(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    @GetMapping
    public ResponseEntity<List<TransacaoResponseDTO>> listarTodas() {
        List<Transacao> transacoes = transacaoRepository.findAll();
        List<TransacaoResponseDTO> dtos = transacoes.stream()
            .map(t -> new TransacaoResponseDTO(
                t.getIdTransacao(),
                t.getTipoTransacao(),
                t.getValor(),
                t.getStatus(),
                t.getDataTransacao()
            ))
            .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/conta/{numeroConta}")
    public ResponseEntity<List<TransacaoResponseDTO>> listarPorConta(@PathVariable Integer numeroConta) {
        List<Transacao> transacoes = transacaoRepository.findTransacoesByConta(numeroConta);
        List<TransacaoResponseDTO> dtos = transacoes.stream()
            .map(t -> new TransacaoResponseDTO(
                t.getIdTransacao(),
                t.getTipoTransacao(),
                t.getValor(),
                t.getStatus(),
                t.getDataTransacao()
            ))
            .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transacao> buscarPorId(@PathVariable Integer id) {
        return transacaoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}