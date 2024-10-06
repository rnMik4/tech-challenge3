package com.restaurantes.DTO;

public record AvaliacoesReqDTO(
        Integer avaliacao,
        String comentario,
        Long idReserva,
        Long idRestaurante
) {
}
