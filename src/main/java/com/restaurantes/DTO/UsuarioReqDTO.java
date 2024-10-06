package com.restaurantes.DTO;

public record UsuarioReqDTO(
        String nomeCompleto,
        String email,
        String senha,
        String telefone
) {
}
