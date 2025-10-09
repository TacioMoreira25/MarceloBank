package com.MarceloBank.MarceloBank.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class OperacaoValorDTO {

    @NotBlank(message = "PIN é obrigatório")
    @Pattern(regexp = "\\d{4}", message = "PIN deve conter exatamente 4 dígitos")
    private String pin;

    @NotNull(message = "Valor é obrigatório")
    @Positive(message = "Valor deve ser maior que zero")
    private BigDecimal valor;

    public String getPin() { return pin; }
    public BigDecimal getValor() { return valor; }

    public void setPin(String pin) { this.pin = pin; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
}

