package com.restaurantes.controller;

import com.restaurantes.DTO.RestauranteReqDTO;
import com.restaurantes.DTO.RestauranteRespDTO;
import com.restaurantes.service.GenericMapper;
import com.restaurantes.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurante")
public class RestauranteController {
    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private GenericMapper mapper;

    @PostMapping()
    public ResponseEntity<RestauranteRespDTO> novoRestaurante(@RequestBody RestauranteReqDTO restaurante) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.entidadeParaDTO(restauranteService.createRestaurante(restaurante), RestauranteRespDTO.class));
    }

    @GetMapping
    public ResponseEntity<List<RestauranteRespDTO>> listRestaurantes() {
        return ResponseEntity.ok().body(mapper.entidadeParaDTO(restauranteService.getRestaurantes(), RestauranteRespDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestauranteRespDTO> buscarRestaurantePorId(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(mapper.entidadeParaDTO(restauranteService.getRestauranteById(id), RestauranteRespDTO.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestauranteRespDTO> atualizarRestaurante(@PathVariable("id") Long id, @RequestBody RestauranteReqDTO restaurante) {
        return  ResponseEntity.ok().body(mapper.entidadeParaDTO(restauranteService.updateRestaurante(restaurante, id), RestauranteRespDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarRestaurante(@PathVariable("id") Long id) {
        restauranteService.deleteRestaurante(id);
        return ResponseEntity.ok().body("{status: \"Restaurante Deletado\"}");
    }
}
