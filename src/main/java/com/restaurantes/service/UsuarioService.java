package com.restaurantes.service;

import com.restaurantes.DTO.UsuarioReqDTO;
import com.restaurantes.entity.Usuario;

import java.util.List;

public interface UsuarioService {
    Usuario getByEmail(String email);
    Usuario novoUsuario(UsuarioReqDTO usuario);
    List<Usuario> listarUsuarios();
    Usuario updateUsuario(UsuarioReqDTO usuario, Long id);
    void deleteUsuario(Long id);
    Usuario getUsuario(Long id);
}
