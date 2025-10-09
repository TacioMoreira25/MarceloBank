package com.MarceloBank.MarceloBank.mapper;

import com.MarceloBank.MarceloBank.dto.ContaResponseDTO;
import com.MarceloBank.MarceloBank.model.Conta;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContaMapper {

    public ContaResponseDTO toDTO(Conta conta) {
        if (conta == null) return null;
        return new ContaResponseDTO(
                conta.getNumeroConta(),
                conta.getTipoConta(),
                conta.getSaldo(),
                conta.getDataAbertura(),
                conta.getStatus(),
                conta.getCliente() != null ? conta.getCliente().getCpf() : null,
                conta.getCliente() != null ? conta.getCliente().getNome() : null,
                conta.getAgencia() != null ? conta.getAgencia().getCodigoAgencia() : null,
                conta.getAgencia() != null ? conta.getAgencia().getNomeAgencia() : null
        );
    }

    public List<ContaResponseDTO> toDTOList(List<Conta> contas) {
        return contas.stream().map(this::toDTO).collect(Collectors.toList());
    }
}

