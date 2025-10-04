package com.MarceloBank.MarceloBank.model;

import com.MarceloBank.MarceloBank.enums.TipoConta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "conta")
@AllArgsConstructor
@NoArgsConstructor
public class Conta
{
    @Id
    @Getter
    @Setter
    private Integer numeroConta;

    @ManyToOne
    @JoinColumn(name = "agencia_id")
    @Getter
    @Setter
    private Agencia agencia;

    public Integer getNumeroConta() {
		return numeroConta;
	}

	public void setNumeroConta(Integer numeroConta) {
		this.numeroConta = numeroConta;
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	public TipoConta getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(TipoConta tipoConta) {
		this.tipoConta = tipoConta;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public Date getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(Date dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Cartao> getCartoes() {
		return cartoes;
	}

	public void setCartoes(List<Cartao> cartoes) {
		this.cartoes = cartoes;
	}

	public List<Transacao> getTransacoesOrigem() {
		return transacoesOrigem;
	}

	public void setTransacoesOrigem(List<Transacao> transacoesOrigem) {
		this.transacoesOrigem = transacoesOrigem;
	}

	public List<Transacao> getTransacoesDestino() {
		return transacoesDestino;
	}

	public void setTransacoesDestino(List<Transacao> transacoesDestino) {
		this.transacoesDestino = transacoesDestino;
	}

	@Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_conta")
    private TipoConta tipoConta;
    @Getter
    @Setter
    @Column(precision = 15, scale = 2)
    private BigDecimal saldo;

    @Getter
    @Setter
    @Temporal(TemporalType.DATE)
    private Date dataAbertura;

    @Getter
    @Setter
    private String status;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    @Getter
    @Setter
    private Cliente cliente;

    @OneToMany(mappedBy = "conta")
    @Getter
    @Setter
    private List<Cartao> cartoes;

    @OneToMany(mappedBy = "contaOrigem")
    @Getter
    @Setter
    private List<Transacao> transacoesOrigem;

    @OneToMany(mappedBy = "contaDestino")
    @Getter
    @Setter
    private List<Transacao> transacoesDestino;
}
