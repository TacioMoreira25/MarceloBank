package com.MarceloBank.MarceloBank.dto;

import com.MarceloBank.MarceloBank.enums.StatusEmprestimo;

import java.math.BigDecimal;
import java.util.Date;

public class EmprestimoResponseDTO {
    private Integer id;
    private BigDecimal valorSolicitado;
    private BigDecimal valorAprovado;
    private Integer prazoMeses;
    private StatusEmprestimo status;
    private Date dataSolicitacao;
    private BigDecimal saldoDevedor;

    public EmprestimoResponseDTO(Integer id, BigDecimal valorSolicitado, BigDecimal valorAprovado,
                                 Integer prazoMeses,  StatusEmprestimo status, Date dataSolicitacao,
                                 BigDecimal saldoDevedor)
    {
        this.id = id;
        this.valorSolicitado = valorSolicitado;
        this.valorAprovado = valorAprovado;
        this.prazoMeses = prazoMeses;
        this.status = status;
        this.dataSolicitacao = dataSolicitacao;
        this.saldoDevedor = saldoDevedor;
    }

    public Integer getId() { return id; }
    public BigDecimal getValorSolicitado() { return valorSolicitado; }
    public BigDecimal getValorAprovado() { return valorAprovado; }
    public Integer getPrazoMeses() { return prazoMeses; }
    public  StatusEmprestimo getStatus() { return status; }
    public Date getDataSolicitacao() { return dataSolicitacao; }
    public BigDecimal getSaldoDevedor() { return saldoDevedor; }
}

