package com.MarceloBank.MarceloBank.dto;

import com.MarceloBank.MarceloBank.enums.TipoCartao;
import java.math.BigDecimal;
import java.util.Date;

public class CartaoResponseDTO {
    private Integer numeroCartao;
    private TipoCartao tipoCartao;
    private Date dataEmissao;
    private Date dataValidade;
    private String status;
    private BigDecimal limite;

    public CartaoResponseDTO(Integer numeroCartao, TipoCartao tipoCartao, Date dataEmissao, Date dataValidade, String status, BigDecimal limite) {
        this.numeroCartao = numeroCartao;
        this.tipoCartao = tipoCartao;
        this.dataEmissao = dataEmissao;
        this.dataValidade = dataValidade;
        this.status = status;
        this.limite = limite;
    }

    public Integer getNumeroCartao() { return numeroCartao; }
    public TipoCartao getTipoCartao() { return tipoCartao; }
    public Date getDataEmissao() { return dataEmissao; }
    public Date getDataValidade() { return dataValidade; }
    public String getStatus() { return status; }
    public BigDecimal getLimite() { return limite; }
}

