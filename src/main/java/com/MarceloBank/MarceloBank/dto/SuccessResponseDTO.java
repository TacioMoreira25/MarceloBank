package com.MarceloBank.MarceloBank.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuccessResponseDTO {
    private String mensagem;
    private Object dados;
}
