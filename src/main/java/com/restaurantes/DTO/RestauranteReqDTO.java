package com.restaurantes.DTO;

import jakarta.persistence.Column;

import java.time.LocalTime;

public record RestauranteReqDTO(
        String nomeRestaurante,
        String tipoCozinha,
        String logradouro,
        String bairro,
        String numero,
        String cidade,
        String uf,
        String pais,
        String cep,
        String telefone,
        String cnpj,
        Long owner_id,
        Integer horarioAbertura,
        Integer horarioFechamento
) {

}
