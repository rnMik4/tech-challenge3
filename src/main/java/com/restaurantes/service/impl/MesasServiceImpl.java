package com.restaurantes.service.impl;

import com.restaurantes.DTO.MesasReqDTO;
import com.restaurantes.entity.MesasRestaurante;
import com.restaurantes.exceptions.ResourceNotFoundException;
import com.restaurantes.repository.MesasRepository;
import com.restaurantes.service.MesasService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MesasServiceImpl implements MesasService {
    @Autowired
    private final MesasRepository mesasRepository;

    @Override
    public MesasRestaurante addMesa(MesasReqDTO dto) {
        MesasRestaurante mesa = new MesasRestaurante();
        BeanUtils.copyProperties(dto, mesa);
        return mesasRepository.save(mesa);
    }

    @Override
    public MesasRestaurante updateMesa(MesasReqDTO dto, Long id) {
        MesasRestaurante mesa = new MesasRestaurante();
        mesa = getMesaById(id);
        BeanUtils.copyProperties(dto, mesa);
        return mesasRepository.save(mesa);
    }

    @Override
    public void deleteMesa(Long id) {
        mesasRepository.deleteById(id);
    }

    @Override
    public List<MesasRestaurante> getAllMesas() {
        return mesasRepository.findAll();
    }

    @Override
    public MesasRestaurante getMesaById(Long id) {
        return mesasRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mesa não encontrada"));
    }

    @Override
    public List<Map<String, Object>> getMesasDisponiveisData(Long idRestaurante, String data) {
        return mesasRepository.findLugaresDisponiveisRestaurante(idRestaurante, data);
    }

    @Override
    public List<MesasRestaurante> getMesasRestaurantes(Long idRestaurante) {
        return mesasRepository.findByIdRestaurante(idRestaurante);
    }

    @Override
    public MesasRestaurante addMesasRestaurante(MesasRestaurante mesa) {
        return mesasRepository.save(mesa);
    }


}
