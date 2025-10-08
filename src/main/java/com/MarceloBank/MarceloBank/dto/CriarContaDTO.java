package com.MarceloBank.MarceloBank.dto;

import com.MarceloBank.MarceloBank.enums.TipoConta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CriarContaDTO {
    @NotNull(message = "Número da conta é obrigatório")
    private Integer numeroConta;

    @NotBlank(message = "CPF do cliente é obrigatório")
    private String cpf;

    @NotNull(message = "Código da agência é obrigatório")
    private Integer codigoAgencia;

    @NotNull(message = "Tipo de conta é obrigatório")
    private TipoConta tipoConta;

    @Min(value = 0, message = "Saldo inicial não pode ser negativo")
    private BigDecimal saldoInicial;
}
