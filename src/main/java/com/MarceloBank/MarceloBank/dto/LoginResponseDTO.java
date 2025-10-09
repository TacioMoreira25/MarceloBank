package com.MarceloBank.MarceloBank.dto;

import java.math.BigDecimal;

public class LoginResponseDTO {

    private Integer numeroConta;
    private String nomeCliente;
    private String cpfCliente;
    private String tipoConta;
    private BigDecimal saldo;
    private String status;
    private Boolean sucesso;
    private String mensagem;

    public LoginResponseDTO() {}

    public LoginResponseDTO(Integer numeroConta, String nomeCliente, String cpfCliente,
                            String tipoConta, BigDecimal saldo, String status) {
        this.numeroConta = numeroConta;
        this.nomeCliente = nomeCliente;
        this.cpfCliente = cpfCliente;
        this.tipoConta = tipoConta;
        this.saldo = saldo;
        this.status = status;
        this.sucesso = true;
        this.mensagem = "Login realizado com sucesso";
    }

    public LoginResponseDTO(Boolean sucesso, String mensagem) {
        this.sucesso = sucesso;
        this.mensagem = mensagem;
    }

    // Getters e Setters
    public Integer getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(Integer numeroConta) {
        this.numeroConta = numeroConta;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public String getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(String tipoConta) {
        this.tipoConta = tipoConta;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getSucesso() {
        return sucesso;
    }

    public void setSucesso(Boolean sucesso) {
        this.sucesso = sucesso;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}