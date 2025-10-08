package com.MarceloBank.MarceloBank.mapper;

import com.MarceloBank.MarceloBank.dto.ContaResponseDTO;
import com.MarceloBank.MarceloBank.model.Conta;
import org.springframework.stereotype.Component;

@Component
public class ContaMapper {

    public ContaResponseDTO toDTO(Conta conta) {
        return new ContaResponseDTO(
                conta.getNumeroConta(),
                conta.getCliente() != null ? conta.getCliente().getNome() : null,
                conta.getCliente() != null ? conta.getCliente().getCpf() : null,
                conta.getAgencia() != null ? conta.getAgencia().getNomeAgencia() : null,
                conta.getTipoConta(),
                conta.getSaldo(),
                conta.getStatus(),
                conta.getDataAbertura()
        );
    }
}
