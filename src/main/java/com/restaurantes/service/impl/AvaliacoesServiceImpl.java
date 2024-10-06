package com.restaurantes.service.impl;

import com.restaurantes.DTO.AvaliacoesReqDTO;
import com.restaurantes.entity.Avaliacoes;
import com.restaurantes.exceptions.ResourceNotFoundException;
import com.restaurantes.repository.AvaliacoesRepository;
import com.restaurantes.service.AvaliacoesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliacoesServiceImpl implements AvaliacoesService {

    @Autowired
    private final AvaliacoesRepository repository;

    @Override
    public Avaliacoes createAvaliacao(AvaliacoesReqDTO avaliacao) {
        Avaliacoes avaliacaoExistente = new Avaliacoes();
        avaliacaoExistente = getAvaliacoesPorReserva(avaliacaoExistente.getIdReserva());

        if (avaliacaoExistente != null) {
            throw new RuntimeException("Avaliação ja realizada para esta reserva");
        }
        Avaliacoes toSave = new Avaliacoes();
        BeanUtils.copyProperties(avaliacao, toSave);
        return repository.save(toSave);
    }

    @Override
    public Avaliacoes updateAvaliacao(AvaliacoesReqDTO avaliacao, Long id) {
        Avaliacoes avaliacaoExistente = new Avaliacoes();
        avaliacaoExistente = getAvaliacao(id);
        Avaliacoes toUpdate = new Avaliacoes();
        if (avaliacaoExistente != null) {
            BeanUtils.copyProperties(avaliacao, avaliacaoExistente);
            toUpdate = repository.save(avaliacaoExistente);
        }

        return toUpdate;
    }

    @Override
    public void deleteAvaliacao(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Avaliacoes getAvaliacao(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avaliação não encontrada"));
    }

    @Override
    public List<Avaliacoes> getAvaliacoes() {
        return repository.findAll();
    }

    @Override
    public Avaliacoes getAvaliacoesPorReserva(Long reservaId) {
        return repository.findByIdReserva(reservaId);
    }

    @Override
    public List<Avaliacoes> getAvaliacoesPorRestaurante(Long restauranteId) {
        return repository.findByIdRestaurante(restauranteId);
    }
}
