package com.restaurantes.DTO;

import java.time.LocalDateTime;

public record ReservasDTO(
        LocalDateTime dataHoraReserva,
        LocalDateTime dataHoraAtualizacao,
        String statusReserva,
        Long idRestaurante,
        Long idMesa,
        Long idUsuario

) {
}
