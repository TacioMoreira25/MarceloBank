package com.MarceloBank.MarceloBank.dto;

import com.MarceloBank.MarceloBank.enums.StatusTransacao;
import java.math.BigDecimal;
import java.util.Date;

public class TransacaoResponseDTO {
    private Integer id;
    private String tipo;
    private BigDecimal valor;
    private StatusTransacao status;
    private Date data;

    public TransacaoResponseDTO(Integer id, String tipo, BigDecimal valor, StatusTransacao status, Date data) {
        this.id = id;
        this.tipo = tipo;
        this.valor = valor;
        this.status = status;
        this.data = data;
    }

    public Integer getId() { return id; }
    public String getTipo() { return tipo; }
    public BigDecimal getValor() { return valor; }
    public StatusTransacao getStatus() { return status; }
    public Date getData() { return data; }
}

