package com.MarceloBank.MarceloBank.dto;

import com.MarceloBank.MarceloBank.enums.TipoConta;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContaResponseDTO
{
    private Integer numeroConta;
    private TipoConta tipoConta;
    private BigDecimal saldo;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dataAbertura;
    private String status;

    private String cpfCliente;
    private String nomeCliente;

    private Integer codigoAgencia;
    private String nomeAgencia;
}

