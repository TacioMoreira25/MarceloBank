package com.MarceloBank.MarceloBank.dto;

import com.MarceloBank.MarceloBank.enums.TipoConta;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class CriarContaDTO {

    @NotBlank(message = "CPF é obrigatório")
    private String cpf;

    @NotNull(message = "Tipo de conta é obrigatório")
    private TipoConta tipoConta;

    @NotBlank(message = "PIN é obrigatório")
    @Pattern(regexp = "\\d{4}", message = "PIN deve conter exatamente 4 dígitos")
    private String pin;

    @PositiveOrZero(message = "Saldo inicial não pode ser negativo")
    private BigDecimal saldoInicial;

    public String getCpf() { return cpf; }
    public TipoConta getTipoConta() { return tipoConta; }
    public String getPin() { return pin; }
    public BigDecimal getSaldoInicial() { return saldoInicial; }

    // Setters para desserialização
    public void setCpf(String cpf) { this.cpf = cpf; }
    public void setTipoConta(TipoConta tipoConta) { this.tipoConta = tipoConta; }
    public void setPin(String pin) { this.pin = pin; }
    public void setSaldoInicial(BigDecimal saldoInicial) { this.saldoInicial = saldoInicial; }
}
