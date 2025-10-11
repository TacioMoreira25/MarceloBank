package com.MarceloBank.MarceloBank.controller;

import com.MarceloBank.MarceloBank.dto.*;
import com.MarceloBank.MarceloBank.mapper.ClienteMapper;
import com.MarceloBank.MarceloBank.model.Cliente;
import com.MarceloBank.MarceloBank.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

    private final ClienteService clienteService;
    private final ClienteMapper clienteMapper;

    public ClienteController(ClienteService clienteService, ClienteMapper clienteMapper)
    {
        this.clienteService = clienteService;
        this.clienteMapper = clienteMapper;
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> criarCliente(
            @Valid @RequestBody CriarClienteDTO dto)
    {
        Cliente cliente = clienteMapper.toEntity(dto);

        Cliente clienteSalvo = clienteService.criarCliente(cliente);

        ClienteResponseDTO response = clienteMapper.toDTO(clienteSalvo);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarTodos() {
        List<Cliente> clientes = clienteService.listarTodos();

        List<ClienteResponseDTO> response = clientes.stream()
                .map(clienteMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<ClienteResponseDTO> buscarPorCpf(@PathVariable String cpf) {
        Cliente cliente = clienteService.getClientePorCpf(cpf);
        ClienteResponseDTO response = clienteMapper.toDTO(cliente);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cpf/{cpf}/completo")
    public ResponseEntity<Map<String, Object>> buscarInfoCompleta(@PathVariable String cpf) {
        Map<String, Object> info = clienteService.getInfoCompletaCliente(cpf);
        return ResponseEntity.ok(info);
    }

    @PutMapping("/cpf/{cpf}")
    public ResponseEntity<ClienteResponseDTO> atualizarCliente(
            @PathVariable String cpf,
            @Valid @RequestBody AtualizarClienteDTO dto) {

        Cliente cliente = clienteService.getClientePorCpf(cpf);

        clienteMapper.updateEntity(cliente, dto);

        Cliente clienteAtualizado = clienteService.atualizarCliente(cpf, cliente);

        ClienteResponseDTO response = clienteMapper.toDTO(clienteAtualizado);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/cpf/{cpf}")
    public ResponseEntity<?> deletarCliente(@PathVariable String cpf) {
        try {
            clienteService.deletarCliente(cpf);
            SuccessResponseDTO response = new SuccessResponseDTO(
                    "Cliente deletado com sucesso",
                    Map.of("cpf", cpf)
            );
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponseDTO(e.getMessage(), HttpStatus.CONFLICT.value()));
        }
    }
}