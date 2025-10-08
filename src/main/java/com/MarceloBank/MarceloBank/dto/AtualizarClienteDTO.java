package com.MarceloBank.MarceloBank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtualizarClienteDTO {
    private String nome;
    private String email;
    private String telefone;
    private String endereco;
}
