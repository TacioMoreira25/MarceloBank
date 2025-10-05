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
