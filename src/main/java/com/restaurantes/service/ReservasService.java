package com.restaurantes.service;

import com.restaurantes.DTO.ReservasDTO;
import com.restaurantes.entity.Reservas;

import java.util.List;

public interface ReservasService {
    Reservas addReserva(ReservasDTO reserva);
    Reservas updateReserva(ReservasDTO reserva, Long id);
    void deleteReserva(Long id);
    List<Reservas> getReservas();
    List<Reservas> getReservasByRestaurante(Long restauranteId);
    List<Reservas> getReservasByUsuario(Long usuarioId);
    Reservas getReservaById(Long id);
    Reservas getReservaByRestauranteAndMesa(Long restauranteId, Long mesaId);
}
