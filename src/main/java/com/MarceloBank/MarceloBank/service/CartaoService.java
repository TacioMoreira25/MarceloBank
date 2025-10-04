package com.MarceloBank.MarceloBank.service;

import com.MarceloBank.MarceloBank.enums.TipoCartao;
import com.MarceloBank.MarceloBank.model.Cartao;
import com.MarceloBank.MarceloBank.model.Conta;
import com.MarceloBank.MarceloBank.repository.CartaoRepository;
import com.MarceloBank.MarceloBank.repository.ContaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CartaoService {
    private final CartaoRepository cartaoRepository;
    private final ContaRepository contaRepository;

    public CartaoService(CartaoRepository cartaoRepository, ContaRepository contaRepository)
    {
        this.cartaoRepository = cartaoRepository;
        this.contaRepository = contaRepository;
    }

    public Cartao emitirCartao(Integer numeroCartao, Integer numeroConta, TipoCartao
            tipoCartao, BigDecimal limite)
    {
        Conta conta = contaRepository.findById(numeroConta)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        Cartao cartao = new Cartao();
        cartao.setNumeroCartao(numeroCartao);
        cartao.setConta(conta);
        cartao.setTipoCartao(tipoCartao);
        cartao.setDataEmissao(new Date());

        // Calcula data de validade (3 anos)
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 3);
        cartao.setDataValidade(cal.getTime());

        cartao.setLimite(limite);
        cartao.setStatus("ATIVO");

        return cartaoRepository.save(cartao);
    }

    public Cartao bloquearCartao(Integer numeroCartao) {
        Cartao cartao = cartaoRepository.findById(numeroCartao)
                .orElseThrow(() -> new RuntimeException("Cartão não encontrado"));

        cartao.setStatus("BLOQUEADO");
        return cartaoRepository.save(cartao);
    }

    public List<Cartao> listarTodosCartoes() {
        return cartaoRepository.findAll();
    }
}