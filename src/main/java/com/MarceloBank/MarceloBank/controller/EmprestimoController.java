package com.MarceloBank.MarceloBank.controller;

import com.MarceloBank.MarceloBank.dto.EmprestimoResponseDTO;
import com.MarceloBank.MarceloBank.model.Emprestimo;
import com.MarceloBank.MarceloBank.repository.EmprestimoRepository;
import com.MarceloBank.MarceloBank.service.EmprestimoService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/api/emprestimos")
@CrossOrigin(origins = "*")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;
    private final EmprestimoRepository emprestimoRepository;

    public EmprestimoController(EmprestimoService emprestimoService,
                                EmprestimoRepository emprestimoRepository) {
        this.emprestimoService = emprestimoService;
        this.emprestimoRepository = emprestimoRepository;
    }

    @PostMapping
    public ResponseEntity<Emprestimo> solicitarEmprestimo(@RequestBody Map<String,
            Object> request) {
        try {
            String cpf = (String) request.get("cpf");
            BigDecimal valorSolicitado = new BigDecimal(request.get("valorSolicitado").
                    toString());
            Integer prazoMeses = (Integer) request.get("prazoMeses");

            Emprestimo emprestimo = emprestimoService.solicitarEmprestimo(
                    cpf, valorSolicitado, prazoMeses
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(emprestimo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Emprestimo>> listarTodos() {
        List<Emprestimo> emprestimos = emprestimoRepository.findAll();
        return ResponseEntity.ok(emprestimos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Emprestimo> buscarPorId(@PathVariable Integer id) {
        return emprestimoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{cpf}")
    public ResponseEntity<List<EmprestimoResponseDTO>> listarPorCliente(@PathVariable String cpf) {
        List<Emprestimo> emprestimos = emprestimoRepository.findByClienteCpf(cpf);
        List<EmprestimoResponseDTO> dtos = emprestimos.stream()
            .map(e -> new EmprestimoResponseDTO(
                e.getIdEmprestimo(),
                e.getValorSolicitado(),
                e.getValorAprovado(),
                e.getPrazoMeses(),
                e.getStatus(),
                e.getDataSolicitacao(),
                e.getSaldoDevedor()
            ))
            .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/cliente/{cpf}/saldo-devedor")
    public ResponseEntity<Map<String, Object>> consultarSaldoDevedor(@PathVariable String cpf) {
        BigDecimal saldoDevedor = emprestimoService.getSaldoDevedorTotal(cpf);
        return ResponseEntity.ok(Map.of(
                "cpf", cpf,
                "saldoDevedor", saldoDevedor
        ));
    }

    @PatchMapping("/{id}/aprovar")
    public ResponseEntity<Emprestimo> aprovarEmprestimo(
            @PathVariable Integer id,
            @RequestBody Map<String, BigDecimal> request) {
        try {
            BigDecimal valorAprovado = request.get("valorAprovado");
            Emprestimo emprestimo = emprestimoService.aprovarEmprestimo(id, valorAprovado);
            return ResponseEntity.ok(emprestimo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/{id}/pagar-parcela")
    public ResponseEntity<Map<String, String>> pagarParcela(
            @PathVariable Integer id,
            @RequestBody Map<String, BigDecimal> request) {
        try {
            BigDecimal valorPagamento = request.get("valorPagamento");
            emprestimoService.pagarParcela(id, valorPagamento);
            return ResponseEntity.ok(Map.of(
                    "mensagem", "Parcela paga com sucesso",
                    "valor", valorPagamento.toString()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("erro", e.getMessage()));
        }
    }
}