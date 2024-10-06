package com.restaurantes.service;

import com.restaurantes.DTO.MesasReqDTO;
import com.restaurantes.entity.MesasRestaurante;
import java.util.List;
import java.util.Map;

public interface MesasService {
    MesasRestaurante addMesa(MesasReqDTO dto);
    MesasRestaurante updateMesa(MesasReqDTO dto, Long id);
    void deleteMesa(Long id);
    List<MesasRestaurante> getAllMesas();
    MesasRestaurante getMesaById(Long id);
    List<Map<String, Object>> getMesasDisponiveisData(Long idRestaurante, String data);
    List<MesasRestaurante> getMesasRestaurantes(Long idRestaurante);
    MesasRestaurante addMesasRestaurante(MesasRestaurante mesa);
}
