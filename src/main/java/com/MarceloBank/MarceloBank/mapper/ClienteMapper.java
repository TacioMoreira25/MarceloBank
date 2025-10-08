package com.MarceloBank.MarceloBank.mapper;

import com.MarceloBank.MarceloBank.dto.AtualizarClienteDTO;
import com.MarceloBank.MarceloBank.dto.ClienteResponseDTO;
import com.MarceloBank.MarceloBank.dto.CriarClienteDTO;
import com.MarceloBank.MarceloBank.model.Cliente;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ClienteMapper {

    public Cliente toEntity(CriarClienteDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setCpf(dto.getCpf());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefone(dto.getTelefone());
        cliente.setDataNascimento(dto.getDataNascimento());
        cliente.setEndereco(dto.getEndereco());
        cliente.setDataCadastro(new Date());
        return cliente;
    }

    public ClienteResponseDTO toDTO(Cliente cliente) {
        return new ClienteResponseDTO(
                cliente.getIdCliente(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getEmail(),
                cliente.getTelefone(),
                cliente.getDataNascimento(),
                cliente.getEndereco(),
                cliente.getDataCadastro()
        );
    }

    public void updateEntity(Cliente cliente, AtualizarClienteDTO dto) {
        if (dto.getNome() != null && !dto.getNome().isBlank()) {
            cliente.setNome(dto.getNome());
        }
        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            cliente.setEmail(dto.getEmail());
        }
        if (dto.getTelefone() != null && !dto.getTelefone().isBlank()) {
            cliente.setTelefone(dto.getTelefone());
        }
        if (dto.getEndereco() != null && !dto.getEndereco().isBlank()) {
            cliente.setEndereco(dto.getEndereco());
        }
    }
}