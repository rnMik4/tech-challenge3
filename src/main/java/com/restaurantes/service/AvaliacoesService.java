package com.restaurantes.service;

import com.restaurantes.DTO.AvaliacoesReqDTO;
import com.restaurantes.entity.Avaliacoes;

import java.util.List;

public interface AvaliacoesService {
    Avaliacoes createAvaliacao(AvaliacoesReqDTO avaliacao);
    Avaliacoes updateAvaliacao(AvaliacoesReqDTO avaliacao, Long id);
    void deleteAvaliacao(Long id);
    Avaliacoes getAvaliacao(Long id);
    List<Avaliacoes> getAvaliacoes();
    Avaliacoes getAvaliacoesPorReserva(Long reservaId);
    List<Avaliacoes> getAvaliacoesPorRestaurante(Long restauranteId);
}
