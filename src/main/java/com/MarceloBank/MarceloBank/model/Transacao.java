package com.MarceloBank.MarceloBank.model;

import com.MarceloBank.MarceloBank.enums.StatusTransacao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "transacao")
@AllArgsConstructor
@NoArgsConstructor
public class Transacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Integer idTransacao;

    @ManyToOne
    @JoinColumn(name = "numero_conta_origem")

    @Getter
    @Setter
    private Conta contaOrigem;

    public Integer getIdTransacao() {
		return idTransacao;
	}

	public void setIdTransacao(Integer idTransacao) {
		this.idTransacao = idTransacao;
	}

	public Conta getContaOrigem() {
		return contaOrigem;
	}

	public void setContaOrigem(Conta contaOrigem) {
		this.contaOrigem = contaOrigem;
	}

	public Conta getContaDestino() {
		return contaDestino;
	}

	public void setContaDestino(Conta contaDestino) {
		this.contaDestino = contaDestino;
	}

	public String getTipoTransacao() {
		return tipoTransacao;
	}

	public void setTipoTransacao(String tipoTransacao) {
		this.tipoTransacao = tipoTransacao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Date getDataTransacao() {
		return dataTransacao;
	}

	public void setDataTransacao(Date dataTransacao) {
		this.dataTransacao = dataTransacao;
	}

	public Date getHoraTransacao() {
		return horaTransacao;
	}

	public void setHoraTransacao(Date horaTransacao) {
		this.horaTransacao = horaTransacao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public StatusTransacao getStatus() {
		return status;
	}

	public void setStatus(StatusTransacao status) {
		this.status = status;
	}

	@Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "numero_conta_destino")
    private Conta contaDestino;

    @Getter
    @Setter
    private String tipoTransacao;

    @Getter
    @Setter
    @Column(precision = 15, scale = 2)
    private BigDecimal valor;

    @Getter
    @Setter
    @Temporal(TemporalType.DATE)
    private Date dataTransacao;

    @Getter
    @Setter
    @Temporal(TemporalType.TIMESTAMP)
    private Date horaTransacao;

    @Getter
    @Setter
    private String descricao;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private StatusTransacao status;
}
