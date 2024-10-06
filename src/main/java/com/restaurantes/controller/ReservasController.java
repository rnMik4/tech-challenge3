package com.restaurantes.controller;

import com.restaurantes.DTO.ReservasDTO;
import com.restaurantes.entity.Reservas;
import com.restaurantes.service.ReservasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservasController {
    @Autowired
    private ReservasService reservasService;

    @PostMapping()
    public ResponseEntity<Reservas> createReservas(@RequestBody ReservasDTO reservas) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservasService.addReserva(reservas));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservas> getReservaById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(reservasService.getReservaById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservas> updateReserva(@PathVariable("id") Long id, @RequestBody ReservasDTO reservas) {
        return ResponseEntity.ok().body(reservasService.updateReserva(reservas,id));
    }

    @GetMapping("/reservasbyrestaurante/{id}")
    public ResponseEntity<List<Reservas>> getReservasByRestaurante(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(reservasService.getReservasByRestaurante(id));
    }

    @GetMapping("/reservasbyusuario/{id}")
    public ResponseEntity<List<Reservas>> getReservasByUsuario(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(reservasService.getReservasByUsuario(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteReserva(@PathVariable("id") Long id) {
        reservasService.deleteReserva(id);
        return ResponseEntity.ok().body("\"status\": \"Reserva deletada com sucesso\"");
    }
}
