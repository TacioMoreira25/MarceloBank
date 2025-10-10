package com.MarceloBank.MarceloBank.dto;

public class AgenciaResponseDTO {
    private Integer codigoAgencia;
    private String nomeAgencia;
    private String endereco;
    private String telefone;
    private String gerente;

    public AgenciaResponseDTO() {}

    public AgenciaResponseDTO(Integer codigoAgencia, String nomeAgencia, String endereco, String telefone, String gerente) {
        this.codigoAgencia = codigoAgencia;
        this.nomeAgencia = nomeAgencia;
        this.endereco = endereco;
        this.telefone = telefone;
        this.gerente = gerente;
    }

    public Integer getCodigoAgencia() {
        return codigoAgencia;
    }

    public void setCodigoAgencia(Integer codigoAgencia) {
        this.codigoAgencia = codigoAgencia;
    }

    public String getNomeAgencia() {
        return nomeAgencia;
    }

    public void setNomeAgencia(String nomeAgencia) {
        this.nomeAgencia = nomeAgencia;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getGerente() {
        return gerente;
    }

    public void setGerente(String gerente) {
        this.gerente = gerente;
    }
}

