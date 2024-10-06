package com.restaurantes.repository;

import com.restaurantes.entity.Avaliacoes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvaliacoesRepository extends JpaRepository<Avaliacoes, Long> {
    Avaliacoes findByIdReserva(Long idReserva);
    List<Avaliacoes> findByIdRestaurante(Long idRestaurante);
}
