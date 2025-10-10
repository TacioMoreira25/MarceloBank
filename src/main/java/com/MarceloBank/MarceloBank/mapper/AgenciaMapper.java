package com.MarceloBank.MarceloBank.mapper;

import com.MarceloBank.MarceloBank.model.Agencia;
import com.MarceloBank.MarceloBank.dto.AgenciaResponseDTO;

public class AgenciaMapper {
    public static AgenciaResponseDTO toDTO(Agencia agencia) {
        if (agencia == null) return null;
        return new AgenciaResponseDTO(
            agencia.getCodigoAgencia(),
            agencia.getNomeAgencia(),
            agencia.getEndereco(),
            agencia.getTelefone(),
            agencia.getGerente()
        );
    }
}

