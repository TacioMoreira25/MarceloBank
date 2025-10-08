package com.MarceloBank.MarceloBank.dto;

import com.MarceloBank.MarceloBank.enums.TipoCartao;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmitirCartaoDTO {
    @NotNull(message = "Número do cartão é obrigatório")
    private Integer numeroCartao;

    @NotNull(message = "Número da conta é obrigatório")
    private Integer numeroConta;

    @NotNull(message = "Tipo de cartão é obrigatório")
    private TipoCartao tipoCartao;

    @NotNull(message = "Limite é obrigatório")
    @DecimalMin(value = "0", message = "Limite não pode ser negativo")
    private BigDecimal limite;
}
