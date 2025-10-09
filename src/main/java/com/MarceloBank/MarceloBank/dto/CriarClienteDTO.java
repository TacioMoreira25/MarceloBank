package com.MarceloBank.MarceloBank.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CriarClienteDTO {
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "CPF deve ter 11 dígitos")
    private String cpf;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "Telefone é obrigatório")
    private String telefone;

    @NotNull(message = "Data de nascimento é obrigatória")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dataNascimento;

    @NotBlank(message = "Endereço é obrigatório")
    private String endereco;
}
