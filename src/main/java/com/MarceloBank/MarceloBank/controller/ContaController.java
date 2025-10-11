package com.MarceloBank.MarceloBank.controller;

import com.MarceloBank.MarceloBank.dto.*;
import com.MarceloBank.MarceloBank.model.Conta;
import com.MarceloBank.MarceloBank.repository.ContaRepository;
import com.MarceloBank.MarceloBank.service.ContaService;
import com.MarceloBank.MarceloBank.mapper.ContaMapper;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/api/contas")
@CrossOrigin(origins = "*")
public class ContaController {

    private final ContaService contaService;
    private final ContaRepository contaRepository;
    private final ContaMapper contaMapper;

    public ContaController(ContaService contaService, ContaRepository contaRepository,
                           ContaMapper contaMapper) {
        this.contaService = contaService;
        this.contaRepository = contaRepository;
        this.contaMapper = contaMapper;
    }

    @PostMapping
    public ResponseEntity<?> criarConta(@Valid @RequestBody CriarContaDTO dto) {
        try {
            Conta novaConta = contaService.criarConta(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(contaMapper.toDTO(novaConta));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("erro", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<ContaResponseDTO>> listarTodas() {
        List<Conta> contas = contaRepository.findAll();
        return ResponseEntity.ok(contaMapper.toDTOList(contas));
    }

    @GetMapping("/{numeroConta}")
    public ResponseEntity<ContaResponseDTO> buscarPorNumero(@PathVariable Integer numeroConta) {
        try {
            Conta conta = contaService.buscarContaPorNumero(numeroConta);
            return ResponseEntity.ok(contaMapper.toDTO(conta));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/cliente/{cpf}")
    public ResponseEntity<List<ContaResponseDTO>> listarPorCliente(@PathVariable String cpf) {
        List<Conta> contas = contaRepository.findByClienteCpf(cpf);
        return ResponseEntity.ok(contaMapper.toDTOList(contas));
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
            @Valid @RequestBody DepositoDTO request) {
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
            @Valid @RequestBody OperacaoValorDTO request) {
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

    @DeleteMapping("/{numeroConta}")
    public ResponseEntity<?> deletarConta(@PathVariable Integer numeroConta) {
        try {
            contaService.deletarConta(numeroConta);
            SuccessResponseDTO response = new SuccessResponseDTO(
                "Conta deletada com sucesso",
                Map.of("numeroConta", numeroConta)
            );
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            String msg = e.getMessage();
            if (msg != null && (msg.contains("transações vinculadas") ||
                    msg.contains("cartões vinculados"))) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponseDTO(msg, HttpStatus.CONFLICT.value()));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(msg != null ? msg :
                        "Conta não encontrada", HttpStatus.NOT_FOUND.value()));
        }
    }
}