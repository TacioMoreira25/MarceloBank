package com.MarceloBank.MarceloBank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuccessResponseDTO {
    private String mensagem;
    private Object dados;
}
