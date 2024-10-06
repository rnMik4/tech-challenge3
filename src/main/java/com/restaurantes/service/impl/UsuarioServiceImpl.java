package com.restaurantes.service.impl;

import com.restaurantes.DTO.UsuarioReqDTO;
import com.restaurantes.entity.Usuario;
import com.restaurantes.exceptions.ResourceNotFoundException;
import com.restaurantes.repository.UsuarioRepository;
import com.restaurantes.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private final UsuarioRepository usuarioRepository;


    @Override
    public Usuario getByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public Usuario novoUsuario(UsuarioReqDTO usuario) {
        Usuario usuarioEntity = new Usuario();
        BeanUtils.copyProperties(usuario, usuarioEntity);
        return usuarioRepository.save(usuarioEntity);
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario updateUsuario(UsuarioReqDTO usuario, Long id) {
        Usuario usuario1 = getUsuario(id);
        BeanUtils.copyProperties(usuario, usuario1);
        Usuario usuario2 = usuarioRepository.save(usuario1);
        return usuario2;
    }

    @Override
    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public Usuario getUsuario(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }
}
