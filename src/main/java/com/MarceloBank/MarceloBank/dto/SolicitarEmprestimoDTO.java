package com.MarceloBank.MarceloBank.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolicitarEmprestimoDTO {
    @NotBlank(message = "CPF é obrigatório")
    private String cpf;

    @NotNull(message = "Valor solicitado é obrigatório")
    @DecimalMin(value = "100", message = "Valor mínimo é R$ 100,00")
    private BigDecimal valorSolicitado;

    @NotNull(message = "Prazo é obrigatório")
    @Min(value = 1, message = "Prazo mínimo é 1 mês")
    @Max(value = 360, message = "Prazo máximo é 360 meses")
    private Integer prazoMeses;
}
