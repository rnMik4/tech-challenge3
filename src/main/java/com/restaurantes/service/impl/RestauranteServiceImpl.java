package com.restaurantes.service.impl;

import com.restaurantes.DTO.RestauranteReqDTO;
import com.restaurantes.entity.Restaurante;
import com.restaurantes.entity.Usuario;
import com.restaurantes.exceptions.ResourceNotFoundException;
import com.restaurantes.repository.RestauranteRepository;
import com.restaurantes.service.RestauranteService;
import com.restaurantes.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestauranteServiceImpl implements RestauranteService {

    @Autowired
    private final RestauranteRepository restauranteRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public Restaurante createRestaurante(RestauranteReqDTO restaurante) {
        Restaurante restauranteEntity = new Restaurante();
        restauranteEntity = getRestauranteByCnpj(restaurante.cnpj());
        if (restauranteEntity != null) {
            throw new ResourceNotFoundException("Restaurante já cadastrado");
        }
        //valida se o usuario owner existe, caso nao exista o método do usuário irá retornar a exception
        Usuario owner = usuarioService.getUsuario(restaurante.owner_id());
        Usuario dono = new Usuario();
        if (owner != null) {
            dono = owner;
        }
        Restaurante restauranteEntity1 = new Restaurante();
        BeanUtils.copyProperties(restaurante, restauranteEntity1);
        restauranteEntity1.setDono(dono);
        return restauranteRepository.save(restauranteEntity1);
    }

    public Restaurante addRestaurante(Restaurante restaurante) {
        return restauranteRepository.save(restaurante);
    }

    @Override
    public Restaurante  updateRestaurante(RestauranteReqDTO restaurante, Long id) {
        Restaurante restauranteEntity = getRestauranteById(id);
        BeanUtils.copyProperties(restaurante, restauranteEntity);
        return restauranteRepository.save(restauranteEntity);
    }

    @Override
    public void deleteRestaurante(Long id) {
        Restaurante restauranteEntity = getRestauranteById(id);
        restauranteRepository.deleteById(id);
    }

    @Override
    public Restaurante getRestauranteById(Long id) {
        return restauranteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado"));
    }

    @Override
    public List<Restaurante> getRestaurantes() {
        return restauranteRepository.findAll();
    }

    @Override
    public Restaurante getRestauranteByCnpj(String cnpj) {
        return restauranteRepository.findByCnpj(cnpj);
    }
}
