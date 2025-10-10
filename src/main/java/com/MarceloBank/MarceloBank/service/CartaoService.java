package com.MarceloBank.MarceloBank.service;

import com.MarceloBank.MarceloBank.enums.TipoCartao;
import com.MarceloBank.MarceloBank.model.*;
import com.MarceloBank.MarceloBank.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.*;

@Service
@Transactional
public class CartaoService
{
    private final CartaoRepository cartaoRepository;
    private final ContaRepository contaRepository;

    public CartaoService(CartaoRepository cartaoRepository,
                         ContaRepository contaRepository)
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

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 3);
        cartao.setDataValidade(cal.getTime());

        cartao.setLimite(limite);
        cartao.setStatus("ATIVO");

        return cartaoRepository.save(cartao);
    }

    public Cartao bloquearCartao(Integer numeroCartao)
    {
        Cartao cartao = cartaoRepository.findById(numeroCartao)
                .orElseThrow(() -> new RuntimeException("Cartão não encontrado"));

        cartao.setStatus("BLOQUEADO");
        return cartaoRepository.save(cartao);
    }

    public Cartao desbloquearCartao(Integer numeroCartao) {
        Cartao cartao = cartaoRepository.findById(numeroCartao)
                .orElseThrow(() -> new RuntimeException("Cartão não encontrado"));
        cartao.setStatus("ATIVO");
        return cartaoRepository.save(cartao);
    }
}