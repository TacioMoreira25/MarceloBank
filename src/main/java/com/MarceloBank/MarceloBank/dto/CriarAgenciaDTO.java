package com.MarceloBank.MarceloBank.dto;

import jakarta.validation.constraints.NotBlank;

public class CriarAgenciaDTO {
    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "Endereço é obrigatório")
    private String endereco;

    @NotBlank(message = "Telefone é obrigatório")
    private String telefone;

    @NotBlank(message = "Gerente é obrigatório")
    private String gerente;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getGerente() { return gerente; }
    public void setGerente(String gerente) { this.gerente = gerente; }
}

