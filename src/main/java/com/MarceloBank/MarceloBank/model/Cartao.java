package com.MarceloBank.MarceloBank.model;

import com.MarceloBank.MarceloBank.enums.TipoCartao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "cartao")
@AllArgsConstructor
@NoArgsConstructor
public class Cartao
{
    @Id
    @Getter
    @Setter
    private Integer numeroCartao;

    public Integer getNumeroCartao() {
		return numeroCartao;
	}

	public void setNumeroCartao(Integer numeroCartao) {
		this.numeroCartao = numeroCartao;
	}

	public TipoCartao getTipoCartao() {
		return tipoCartao;
	}

	public void setTipoCartao(TipoCartao tipoCartao) {
		this.tipoCartao = tipoCartao;
	}

	public Date getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public Date getDataValidade() {
		return dataValidade;
	}

	public void setDataValidade(Date dataValidade) {
		this.dataValidade = dataValidade;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getLimite() {
		return limite;
	}

	public void setLimite(BigDecimal limite) {
		this.limite = limite;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	@Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cartao")
    private TipoCartao tipoCartao;

    @Getter
    @Setter
    @Temporal(TemporalType.DATE)
    private Date dataEmissao;

    @Getter
    @Setter
    @Temporal(TemporalType.DATE)
    private Date dataValidade;

    @Getter
    @Setter
    private String status;

    @Getter
    @Setter
    @Column(precision = 15, scale = 2)
    private BigDecimal limite;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "numero_conta")
    private Conta conta;
}
