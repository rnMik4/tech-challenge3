package com.restaurantes.controller;

import com.restaurantes.DTO.UsuarioReqDTO;
import com.restaurantes.DTO.UsuarioRespDTO;
import com.restaurantes.service.GenericMapper;
import com.restaurantes.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private GenericMapper mapper;

    @PostMapping
    public ResponseEntity<UsuarioRespDTO> novoUsuario(@RequestBody UsuarioReqDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.entidadeParaDTO(usuarioService.novoUsuario(dto), UsuarioRespDTO.class));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioRespDTO>> listarUsuarios() {
        return ResponseEntity.ok().body(mapper.entidadeParaDTO(usuarioService.listarUsuarios(), UsuarioRespDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioRespDTO> buscarUsuarioPorId(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(mapper.entidadeParaDTO(usuarioService.getUsuario(id), UsuarioRespDTO.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioRespDTO> atualizarUsuario(@RequestBody UsuarioReqDTO dto, @PathVariable("id") Long id) {
        return ResponseEntity.ok().body(mapper.entidadeParaDTO(usuarioService.updateUsuario(dto, id), UsuarioRespDTO.class));
    }
}
