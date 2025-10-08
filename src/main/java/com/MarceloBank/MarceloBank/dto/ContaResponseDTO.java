package com.MarceloBank.MarceloBank.dto;

import com.MarceloBank.MarceloBank.enums.TipoConta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContaResponseDTO {
    private Integer numeroConta;
    private String nomeCliente;
    private String cpfCliente;
    private String nomeAgencia;
    private TipoConta tipoConta;
    private BigDecimal saldo;
    private String status;
    private Date dataAbertura;
}
