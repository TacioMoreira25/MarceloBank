package com.MarceloBank.MarceloBank.controller;

import com.MarceloBank.MarceloBank.dto.CartaoResponseDTO;
import com.MarceloBank.MarceloBank.enums.TipoCartao;
import com.MarceloBank.MarceloBank.model.Cartao;
import com.MarceloBank.MarceloBank.repository.CartaoRepository;
import com.MarceloBank.MarceloBank.service.CartaoService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/api/cartoes")
@CrossOrigin(origins = "*")
public class CartaoController {

    private final CartaoService cartaoService;
    private final CartaoRepository cartaoRepository;

    public CartaoController(CartaoService cartaoService, CartaoRepository cartaoRepository)
    {
        this.cartaoService = cartaoService;
        this.cartaoRepository = cartaoRepository;
    }

    @PostMapping
    public ResponseEntity<Cartao> emitirCartao(@RequestBody Map<String, Object> request)
    {
        try {
            Integer numeroCartao = (Integer) request.get("numeroCartao");
            Integer numeroConta = (Integer) request.get("numeroConta");
            TipoCartao tipoCartao = TipoCartao.valueOf((String) request.get("tipoCartao"));
            BigDecimal limite = new BigDecimal(request.get("limite").toString());

            Cartao cartao = cartaoService.emitirCartao(numeroCartao, numeroConta, tipoCartao,
                    limite);
            return ResponseEntity.status(HttpStatus.CREATED).body(cartao);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<List<CartaoResponseDTO>> listarPorCpf(@PathVariable String cpf) {
        List<Cartao> cartoes = cartaoRepository.findCartoesByClienteCpf(cpf);
        List<CartaoResponseDTO> dtos = cartoes.stream()
            .map(c -> new CartaoResponseDTO(
                c.getNumeroCartao(),
                c.getTipoCartao(),
                c.getDataEmissao(),
                c.getDataValidade(),
                c.getStatus(),
                c.getLimite()
            ))
            .toList();
        return ResponseEntity.ok(dtos);
    }

    @PatchMapping("/{numeroCartao}/bloquear")
    public ResponseEntity<Cartao> bloquearCartao(@PathVariable Integer numeroCartao) {
        try {
            Cartao cartao = cartaoService.bloquearCartao(numeroCartao);
            return ResponseEntity.ok(cartao);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}