package com.MarceloBank.MarceloBank.enums;

public enum StatusTransacao {
    PENDENTE("Pendente"),
    CONCLUIDA("Concluída"),
    CANCELADA("Cancelada"),
    FALHA("Falha"),
    SALDO_INSUFICIENTE("Saldo Insuficiente"),
    ESTORNADA("Estornada");

    private final String descricao;

    StatusTransacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}