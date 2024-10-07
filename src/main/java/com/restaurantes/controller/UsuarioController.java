package com.restaurantes.controller;

import com.restaurantes.DTO.UsuarioReqDTO;
import com.restaurantes.DTO.UsuarioRespDTO;
import com.restaurantes.entity.Usuario;
import com.restaurantes.service.GenericMapper;
import com.restaurantes.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {
    @Autowired
    private final UsuarioService usuarioService;

    @Autowired
    private GenericMapper mapper;


    @PostMapping
    public ResponseEntity<Usuario> novoUsuario(@RequestBody UsuarioReqDTO dto) {
        return new ResponseEntity<>(usuarioService.novoUsuario(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioRespDTO>> listarUsuarios() {
        return ResponseEntity.ok().body(mapper.entidadeParaDTO(usuarioService.listarUsuarios(), UsuarioRespDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(usuarioService.getUsuario(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@RequestBody UsuarioReqDTO dto, @PathVariable("id") Long id) {
        return new ResponseEntity<>(usuarioService.updateUsuario(dto, id), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarUsuario(@PathVariable("id") Long id) {
        usuarioService.deleteUsuario(id);
        return ResponseEntity.ok().body("{ \"status\": \"DELETADO COM SUCESSO\" }");
    }
}
