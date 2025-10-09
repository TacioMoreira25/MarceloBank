package com.MarceloBank.MarceloBank.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtualizarClienteDTO {
    private String nome;
    private String email;
    private String telefone;
    private String endereco;
}
