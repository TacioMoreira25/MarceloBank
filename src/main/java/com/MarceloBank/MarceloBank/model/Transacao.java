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