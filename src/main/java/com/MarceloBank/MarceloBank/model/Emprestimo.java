package com.MarceloBank.MarceloBank.model;

import com.MarceloBank.MarceloBank.enums.StatusEmprestimo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "emprestimo")
@AllArgsConstructor
@NoArgsConstructor
public class Emprestimo
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Integer idEmprestimo;

    public Integer getIdEmprestimo() {
		return idEmprestimo;
	}

	public void setIdEmprestimo(Integer idEmprestimo) {
		this.idEmprestimo = idEmprestimo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public BigDecimal getValorSolicitado() {
		return valorSolicitado;
	}

	public void setValorSolicitado(BigDecimal valorSolicitado) {
		this.valorSolicitado = valorSolicitado;
	}

	public BigDecimal getValorAprovado() {
		return valorAprovado;
	}

	public void setValorAprovado(BigDecimal valorAprovado) {
		this.valorAprovado = valorAprovado;
	}

	public BigDecimal getTaxaJuros() {
		return taxaJuros;
	}

	public void setTaxaJuros(BigDecimal taxaJuros) {
		this.taxaJuros = taxaJuros;
	}

	public Integer getPrazoMeses() {
		return prazoMeses;
	}

	public void setPrazoMeses(Integer prazoMeses) {
		this.prazoMeses = prazoMeses;
	}

	public Date getDataSolicitacao() {
		return dataSolicitacao;
	}

	public void setDataSolicitacao(Date dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
	}

	public Date getDataAprovacao() {
		return dataAprovacao;
	}

	public void setDataAprovacao(Date dataAprovacao) {
		this.dataAprovacao = dataAprovacao;
	}

	public StatusEmprestimo getStatus() {
		return status;
	}

	public void setStatus(StatusEmprestimo status) {
		this.status = status;
	}

	public BigDecimal getSaldoDevedor() {
		return saldoDevedor;
	}

	public void setSaldoDevedor(BigDecimal saldoDevedor) {
		this.saldoDevedor = saldoDevedor;
	}

	@ManyToOne
    @JoinColumn(name = "id_cliente")
    @Getter
    @Setter
    private Cliente cliente;

    @Getter
    @Setter
    @Column(precision = 15, scale = 2)
    private BigDecimal valorSolicitado;

    @Getter
    @Setter
    @Column(precision = 15, scale = 2)
    private BigDecimal valorAprovado;

    @Getter
    @Setter
    @Column(precision = 5, scale = 2)
    private BigDecimal taxaJuros;

    @Getter
    @Setter
    private Integer prazoMeses;

    @Getter
    @Setter
    @Temporal(TemporalType.DATE)
    private Date dataSolicitacao;

    @Getter
    @Setter
    @Temporal(TemporalType.DATE)
    private Date dataAprovacao;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private StatusEmprestimo status;

    @Getter
    @Setter
    @Column(precision = 15, scale = 2)
    private BigDecimal saldoDevedor;

}
