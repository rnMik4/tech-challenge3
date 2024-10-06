package com.restaurantes.repository;

import com.restaurantes.entity.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
    public List<Restaurante> findAll();
    public Restaurante findByCnpj(String cnpj);
}
