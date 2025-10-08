package com.MarceloBank.MarceloBank.controller;

import com.MarceloBank.MarceloBank.dto.ContaDTO;
import com.MarceloBank.MarceloBank.dto.TransferenciaDTO;
import com.MarceloBank.MarceloBank.model.Conta;
import com.MarceloBank.MarceloBank.repository.ContaRepository;
import com.MarceloBank.MarceloBank.service.ContaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contas")
@CrossOrigin(origins = "*")
public class ContaController {

    private final ContaService contaService;
    private final ContaRepository contaRepository;

    public ContaController(ContaService contaService, ContaRepository contaRepository) {
        this.contaService = contaService;
        this.contaRepository = contaRepository;
    }

    @PostMapping
    public ResponseEntity<Conta> criarConta(@RequestBody Conta conta) {
        try {
            Conta novaConta = contaService.criarConta(conta);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaConta);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Conta>> listarTodas() {
        List<Conta> contas = contaRepository.findAll();
        return ResponseEntity.ok(contas);
    }

    @GetMapping("/{numeroConta}")
    public ResponseEntity<Conta> buscarPorNumero(@PathVariable Integer numeroConta) {
        try {
            Conta conta = contaService.buscarContaPorNumero(numeroConta);
            return ResponseEntity.ok(conta);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/cliente/{cpf}")
    public ResponseEntity<List<Conta>> listarPorCliente(@PathVariable String cpf) {
        List<Conta> contas = contaRepository.findByClienteCpf(cpf);
        return ResponseEntity.ok(contas);
    }

    @GetMapping("/{numeroConta}/saldo")
    public ResponseEntity<Map<String, Object>> consultarSaldo(@PathVariable Integer numeroConta) {
        try {
            Conta conta = contaService.buscarContaPorNumero(numeroConta);
            Map<String, Object> response = Map.of(
                    "numeroConta", conta.getNumeroConta(),
                    "saldo", conta.getSaldo(),
                    "tipoConta", conta.getTipoConta(),
                    "status", conta.getStatus()
            );
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/{numeroConta}/deposito")
    public ResponseEntity<Map<String, String>> depositar(
            @PathVariable Integer numeroConta,
            @Valid @RequestBody ContaDTO request) {
        try {
            BigDecimal valor = request.getValor();
            contaService.depositar(numeroConta, valor);
            return ResponseEntity.ok(Map.of(
                    "mensagem", "Depósito realizado com sucesso",
                    "valor", valor.toString()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("erro", e.getMessage()));
        }
    }

    @PostMapping("/{numeroConta}/saque")
    public ResponseEntity<Map<String, String>> sacar(
            @PathVariable Integer numeroConta,
            @Valid @RequestBody ContaDTO request) {
        try {
            BigDecimal valor = request.getValor();
            String pin = request.getPin();

            contaService.sacar(numeroConta, valor, pin);
            return ResponseEntity.ok(Map.of(
                    "mensagem", "Saque realizado com sucesso",
                    "valor", valor.toString()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("erro", e.getMessage()));
        }
    }

    @PostMapping("/transferencia")
    public ResponseEntity<Map<String, String>> transferir(
            @Valid @RequestBody TransferenciaDTO request) {
        try {
            Integer contaOrigem = request.getContaOrigem();
            Integer contaDestino = request.getContaDestino();
            BigDecimal valor = request.getValor();
            String pin = request.getPin();

            contaService.transferir(contaOrigem, contaDestino, valor, pin);

            return ResponseEntity.ok(Map.of(
                    "mensagem", "Transferência realizada com sucesso",
                    "valor", valor.toString()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("erro", e.getMessage()));
        }
    }
}