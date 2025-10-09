package com.MarceloBank.MarceloBank.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class TransferenciaDTO {

    @NotNull(message = "Conta origem é obrigatória")
    private Integer contaOrigem;

    @NotNull(message = "Conta destino é obrigatória")
    private Integer contaDestino;

    @NotNull(message = "Valor é obrigatório")
    private BigDecimal valor;

    @NotBlank(message = "PIN é obrigatório")
    @Pattern(regexp = "\\d{4}", message = "PIN deve conter exatamente 4 dígitos")
    private String pin;

    public TransferenciaDTO() {}

    public TransferenciaDTO(Integer contaOrigem, Integer contaDestino, BigDecimal valor,
                            String pin)
    {
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
        this.valor = valor;
        this.pin = pin;
    }

    public Integer getContaOrigem() {
        return contaOrigem;
    }

    public Integer getContaDestino() {
        return contaDestino;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getPin() {
        return pin;
    }
}
