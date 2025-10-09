package com.MarceloBank.MarceloBank.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDTO {
    private String erro;
    private Integer status;
    private Long timestamp;
    private String detalhes;

    public ErrorResponseDTO(String erro, Integer status) {
        this.erro = erro;
        this.status = status;
        this.timestamp = System.currentTimeMillis();
    }
}