package com.restaurantes.repository;

import com.restaurantes.entity.Reservas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservasRepository extends JpaRepository<Reservas, Long> {
    Reservas findByRestaurante_IdAndMesa_Id(Long id, Long mesa_Id);
    List<Reservas> findByRestaurante_Id(Long id);
    List<Reservas> findByUsuario_Id(Long usuario_Id);
}
