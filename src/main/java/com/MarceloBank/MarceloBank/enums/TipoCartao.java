package com.MarceloBank.MarceloBank.enums;

public enum TipoCartao {
    CREDITO("Crédito"),
    DEBITO("Débito"),
    CREDITO_DEBITO("Crédito e Débito"),
    VIRTUAL("Virtual"),
    PRE_PAGO("Pré-pago");

    private final String descricao;

    TipoCartao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
    public static TipoCartao fromString(String texto) {
        for (TipoCartao tipo : TipoCartao.values()) {
            if (tipo.name().equalsIgnoreCase(texto)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de cartão inválido: " + texto);
    }
}
