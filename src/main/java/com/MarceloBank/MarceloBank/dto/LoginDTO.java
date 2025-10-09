package com.MarceloBank.MarceloBank.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class LoginDTO {

    @NotBlank(message = "CPF é obrigatório")
    private String cpf;

    @NotBlank(message = "PIN é obrigatório")
    @Pattern(regexp = "\\d{4}", message = "PIN deve conter exatamente 4 dígitos")
    private String pin;

    public LoginDTO() {}

    public LoginDTO(String cpf, String pin) {
        this.cpf = cpf;
        this.pin = pin;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}