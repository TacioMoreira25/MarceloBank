package com.MarceloBank.MarceloBank.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public class ContaDTO {

    @NotBlank(message = "PIN é obrigatório")
    @Pattern(regexp = "\\d{4}", message = "PIN deve conter exatamente 4 dígitos")
    private String pin;

    private BigDecimal valor;

    public ContaDTO() {}

    public ContaDTO(String pin, BigDecimal valor) {
        this.pin = pin;
        this.valor = valor;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}

