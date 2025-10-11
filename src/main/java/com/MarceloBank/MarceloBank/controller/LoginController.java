package com.MarceloBank.MarceloBank.controller;

import com.MarceloBank.MarceloBank.dto.*;
import com.MarceloBank.MarceloBank.model.Conta;
import com.MarceloBank.MarceloBank.service.ContaService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class LoginController {

    private final ContaService contaService;

    public LoginController(ContaService contaService) {
        this.contaService = contaService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
        try {
            Conta conta = contaService.autenticar(
                    loginDTO.getCpf(),
                    loginDTO.getPin(),
                    loginDTO.getNumeroConta()
            );

            if (!"ATIVA".equals(conta.getStatus())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new LoginResponseDTO(false, "Conta inativa"));
            }

            LoginResponseDTO response = new LoginResponseDTO(
                    conta.getNumeroConta(),
                    conta.getCliente().getNome(),
                    conta.getCliente().getCpf(),
                    conta.getTipoConta().getDescricao(),
                    conta.getSaldo(),
                    conta.getStatus()
            );

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponseDTO(false, e.getMessage()));
        }
    }

    @PostMapping("/verificar-conta")
    public ResponseEntity<LoginResponseDTO> verificarConta(@RequestParam Integer numeroConta) {
        try {
            Conta conta = contaService.buscarContaPorNumero(numeroConta);

            LoginResponseDTO response = new LoginResponseDTO();
            response.setNumeroConta(conta.getNumeroConta());
            response.setNomeCliente(conta.getCliente().getNome());
            response.setTipoConta(conta.getTipoConta().getDescricao());
            response.setStatus(conta.getStatus());
            response.setSucesso(true);
            response.setMensagem("Conta encontrada");

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new LoginResponseDTO(false, "Conta não encontrada"));
        }
    }
}