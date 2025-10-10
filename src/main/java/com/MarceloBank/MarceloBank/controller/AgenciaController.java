package com.MarceloBank.MarceloBank.controller;

import com.MarceloBank.MarceloBank.dto.AgenciaResponseDTO;
import com.MarceloBank.MarceloBank.mapper.AgenciaMapper;
import com.MarceloBank.MarceloBank.model.Agencia;
import com.MarceloBank.MarceloBank.service.AgenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agencia")
public class AgenciaController {
    private final AgenciaService agenciaService;

    @Autowired
    public AgenciaController(AgenciaService agenciaService) {
        this.agenciaService = agenciaService;
    }

    @PostMapping
    public ResponseEntity<AgenciaResponseDTO> criarAgencia(@RequestBody Agencia agencia) {
        Agencia agenciaCriada = agenciaService.criarAgencia(agencia);
        AgenciaResponseDTO dto = AgenciaMapper.toDTO(agenciaCriada);
        return ResponseEntity.ok(dto);
    }
}
