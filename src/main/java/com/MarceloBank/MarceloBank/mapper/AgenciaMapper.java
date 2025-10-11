package com.MarceloBank.MarceloBank.mapper;

import com.MarceloBank.MarceloBank.model.Agencia;
import com.MarceloBank.MarceloBank.dto.AgenciaResponseDTO;
import com.MarceloBank.MarceloBank.dto.CriarAgenciaDTO;

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

    public static Agencia fromCreateDTO(CriarAgenciaDTO dto) {
        if (dto == null) return null;
        Agencia a = new Agencia();
        a.setNomeAgencia(dto.getNome());
        a.setEndereco(dto.getEndereco());
        a.setTelefone(dto.getTelefone());
        a.setGerente(dto.getGerente());
        return a;
    }
}
