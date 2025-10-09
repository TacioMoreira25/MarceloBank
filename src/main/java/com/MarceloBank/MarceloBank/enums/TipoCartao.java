package com.MarceloBank.MarceloBank.enums;

public enum TipoCartao {
    CREDITO("Crédito"),
    DEBITO("Débito"),
    PRE_PAGO("Pré-pago");

    private final String descricao;

    TipoCartao(String descricao) {
        this.descricao = descricao;
    }
}