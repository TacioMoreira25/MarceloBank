package com.MarceloBank.MarceloBank.enums;

public enum StatusTransacao
{
    PENDENTE("Pendente"),
    CONCLUIDA("Concluída"),
    CANCELADA("Cancelada"),

    AGUARDANDO_CONFIRMACAO("Aguardando Confirmação"),
    CONFIRMADA("Confirmada"),

    AGUARDANDO_COMPENSACAO("Aguardando Compensação"),
    COMPENSADA("Compensada"),

    ESTORNADA("Estornada"),
    FALHA("Falha"),
    SALDO_INSUFICIENTE("Saldo Insuficiente");

    private final String descricao;

    StatusTransacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}