package com.MarceloBank.MarceloBank.enums;

public enum StatusEmprestimo {
    SOLICITADO("Solicitado"),
    EM_ANALISE("Em Análise"),
    APROVADO("Aprovado"),
    REPROVADO("Reprovado"),
    LIQUIDADO("Liquidado"),
    EM_ATRASO("Em Atraso");

    private final String descricao;

    StatusEmprestimo(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}