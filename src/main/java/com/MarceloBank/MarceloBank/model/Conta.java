package com.MarceloBank.MarceloBank.model;

import com.MarceloBank.MarceloBank.enums.TipoConta;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.*;

@Entity
@Table(name = "conta")
@AllArgsConstructor
@NoArgsConstructor
public class Conta {
    @Id
    @Getter
    @Setter
    private Integer numeroConta;

    @ManyToOne
    @JoinColumn(name = "agencia_id")
    @Getter
    @Setter
    private Agencia agencia;

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

    @Getter
    @Setter
    @Column(length = 4)
    private String pin;

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
