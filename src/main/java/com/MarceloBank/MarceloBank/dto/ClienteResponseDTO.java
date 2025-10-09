package com.MarceloBank.MarceloBank.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dataNascimento;

    private String endereco;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date dataCadastro;
}
