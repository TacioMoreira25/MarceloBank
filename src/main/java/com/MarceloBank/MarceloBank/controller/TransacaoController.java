package com.MarceloBank.MarceloBank.controller;

import com.MarceloBank.MarceloBank.model.Transacao;
import com.MarceloBank.MarceloBank.repository.TransacaoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transacoes")
@CrossOrigin(origins = "*")
public class TransacaoController {

    private final TransacaoRepository transacaoRepository;

    public TransacaoController(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    @GetMapping
    public ResponseEntity<List<Transacao>> listarTodas() {
        List<Transacao> transacoes = transacaoRepository.findAll();
        return ResponseEntity.ok(transacoes);
    }

    @GetMapping("/conta/{numeroConta}")
    public ResponseEntity<List<Transacao>> listarPorConta(@PathVariable Integer numeroConta) {
        List<Transacao> transacoes = transacaoRepository.findTransacoesByConta(numeroConta);
        return ResponseEntity.ok(transacoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transacao> buscarPorId(@PathVariable Integer id) {
        return transacaoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}