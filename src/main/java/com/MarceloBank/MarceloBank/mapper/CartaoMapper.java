package com.MarceloBank.MarceloBank.mapper;

import com.MarceloBank.MarceloBank.dto.CartaoResponseDTO;
import com.MarceloBank.MarceloBank.model.Cartao;
import org.springframework.stereotype.Component;

@Component
public class CartaoMapper {

    public CartaoResponseDTO toDTO(Cartao cartao) {
        return new CartaoResponseDTO(
                cartao.getNumeroCartao(),
                cartao.getTipoCartao(),
                cartao.getDataEmissao(),
                cartao.getDataValidade(),
                cartao.getStatus(),
                cartao.getLimite(),
                cartao.getConta() != null ? cartao.getConta().getNumeroConta() : null
        );
    }
}