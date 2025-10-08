package com.MarceloBank.MarceloBank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteResponseDTO {
    private Integer idCliente;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private Date dataNascimento;
    private String endereco;
    private Date dataCadastro;
}
