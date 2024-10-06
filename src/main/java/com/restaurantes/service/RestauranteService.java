package com.restaurantes.service;

import com.restaurantes.DTO.RestauranteReqDTO;
import com.restaurantes.entity.Restaurante;

import java.util.List;

public interface RestauranteService {
    Restaurante createRestaurante(RestauranteReqDTO restaurante);
    Restaurante updateRestaurante(RestauranteReqDTO restaurante, Long id);
    void deleteRestaurante(Long id);
    Restaurante getRestauranteById(Long id);
    List<Restaurante> getRestaurantes();
    Restaurante getRestauranteByCnpj(String cnpj);
    Restaurante addRestaurante(Restaurante restaurante);
}
