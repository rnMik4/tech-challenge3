package com.restaurantes.service.impl;

import com.restaurantes.DTO.ReservasDTO;
import com.restaurantes.entity.Reservas;
import com.restaurantes.exceptions.ResourceNotFoundException;
import com.restaurantes.repository.MesasRepository;
import com.restaurantes.repository.ReservasRepository;
import com.restaurantes.repository.RestauranteRepository;
import com.restaurantes.repository.UsuarioRepository;
import com.restaurantes.service.MesasService;
import com.restaurantes.service.ReservasService;
import com.restaurantes.service.RestauranteService;
import com.restaurantes.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservasServiceImpl implements ReservasService {

    @Autowired
    private final ReservasRepository reservasRepository;

    @Autowired
    private MesasService mesasService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RestauranteService restauranteService;

    @Override
    public Reservas addReserva(ReservasDTO reserva) {
        Reservas reservas = getReservaByRestauranteAndMesa(reserva.idRestaurante(), reserva.idMesa());
        if (reservas != null) {
            throw new ResourceNotFoundException("Reserva ja existente");
        }
        Reservas newReserva = new Reservas();
        BeanUtils.copyProperties(reserva, newReserva);
        newReserva.setMesa(mesasService.getMesaById(reserva.idMesa()));
        newReserva.setRestaurante(restauranteService.getRestauranteById(reserva.idRestaurante()));
        newReserva.setUsuario(usuarioService.getUsuario(reserva.idUsuario()));
        return reservasRepository.save(newReserva);
    }

    @Override
    public Reservas updateReserva(ReservasDTO reserva, Long id) {
        Reservas reservas = getReservaById(id);

        LocalDateTime lt = LocalDateTime.now();
        reservas.setDataHoraAtualizacao(lt);
        reservas.setStatusReserva(reserva.statusReserva());
        reservas.setUsuario(usuarioService.getUsuario(reserva.idUsuario()));
        reservas.setMesa(mesasService.getMesaById(reserva.idMesa()));
        reservas.setRestaurante(restauranteService.getRestauranteById(reserva.idRestaurante()));
        return reservasRepository.save(reservas);
    }

    @Override
    public void deleteReserva(Long id) {
        reservasRepository.deleteById(id);
    }

    @Override
    public List<Reservas> getReservas() {
        return reservasRepository.findAll();
    }

    @Override
    public List<Reservas> getReservasByRestaurante(Long restauranteId) {
        return reservasRepository.findByRestaurante_Id(restauranteId);
    }

    @Override
    public List<Reservas> getReservasByUsuario(Long usuarioId) {
        return reservasRepository.findByUsuario_Id(usuarioId);
    }

    @Override
    public Reservas getReservaById(Long id) {
        return reservasRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva não encontrada"));
    }

    @Override
    public Reservas getReservaByRestauranteAndMesa(Long restauranteId, Long mesaId) {
        Reservas reserva = new Reservas();
        reserva = reservasRepository.findByRestaurante_IdAndMesa_Id(restauranteId, mesaId);
        return reserva;
    }
}
