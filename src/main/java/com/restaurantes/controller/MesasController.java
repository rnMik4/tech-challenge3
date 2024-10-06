package com.restaurantes.controller;


import com.restaurantes.DTO.MesasReqDTO;
import com.restaurantes.entity.MesasRestaurante;
import com.restaurantes.service.MesasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mesas")
public class MesasController {
    @Autowired
    private MesasService mesasService;


    @PostMapping()
    public ResponseEntity<MesasRestaurante> addMesas(@RequestBody MesasReqDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mesasService.addMesa(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MesasRestaurante> updateMesas(@PathVariable("id") Long id, @RequestBody MesasReqDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(mesasService.updateMesa(dto, id));
    }

    @GetMapping("/mesasdisponiveis/{idRestaurante}/{data}")
    public ResponseEntity<List<Map<String, Object>>> getMesasDisponiveis(@PathVariable("idRestaurante") Long idRestaurante, @PathVariable("data") String
            data) {
        return ResponseEntity.ok().body(mesasService.getMesasDisponiveisData(idRestaurante,data));
    }

    @GetMapping("/mesasrestaurante/{id}")
    public ResponseEntity<List<MesasRestaurante>> getMesasRestaurante(@PathVariable("id") Long idRestaurante) {
        return ResponseEntity.ok().body(mesasService.getMesasRestaurantes(idRestaurante));
    }
}
