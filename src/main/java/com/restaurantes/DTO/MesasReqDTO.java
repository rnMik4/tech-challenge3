package com.restaurantes.DTO;

public record MesasReqDTO(
        Integer lugares,
        Long idRestaurante,
        String nomeMesa
) {
}
