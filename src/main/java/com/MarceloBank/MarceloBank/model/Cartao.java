package com.MarceloBank.MarceloBank.model;

import com.MarceloBank.MarceloBank.enums.TipoCartao;
import jakarta.persistence.*;
import lombok.*;
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
