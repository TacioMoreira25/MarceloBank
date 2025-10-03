package com.MarceloBank.MarceloBank.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "agencia")
@AllArgsConstructor
@NoArgsConstructor
public class Agencia
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigoAgencia;

    private String nomeAgencia;
    private String endereco;
    private String telefone;
    private String gerente;

    @OneToMany(mappedBy = "agencia")
    private List<Conta> contas = new ArrayList<>();

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

    public String getGerente() {
        return gerente;
    }
    public void setGerente(String gerente) {
        this.gerente = gerente;
    }

    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public List<Conta> getContas() {
        return contas;
    }

    public void setContas(List<Conta> contas) {
        this.contas = contas;
    }
}
