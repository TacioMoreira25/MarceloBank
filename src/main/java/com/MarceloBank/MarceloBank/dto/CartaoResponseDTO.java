package com.MarceloBank.MarceloBank.dto;

import com.MarceloBank.MarceloBank.enums.TipoCartao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartaoResponseDTO {
    private Integer numeroCartao;
    private TipoCartao tipoCartao;
    private Date dataEmissao;
    private Date dataValidade;
    private String status;
    private BigDecimal limite;
    private Integer numeroConta;
}
