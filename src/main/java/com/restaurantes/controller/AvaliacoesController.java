package com.restaurantes.controller;

import com.restaurantes.DTO.AvaliacoesReqDTO;
import com.restaurantes.entity.Avaliacoes;
import com.restaurantes.service.AvaliacoesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacoesController {
    @Autowired
    private AvaliacoesService avaliacoesService;


    @PostMapping()
    public ResponseEntity<Avaliacoes> save(@RequestBody AvaliacoesReqDTO avaliacoes) {
        return ResponseEntity.status(HttpStatus.CREATED).body(avaliacoesService.createAvaliacao(avaliacoes));
    }

    @GetMapping()
    public ResponseEntity<List<Avaliacoes>> findAll() {
        return ResponseEntity.ok().body(avaliacoesService.getAvaliacoes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Avaliacoes> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(avaliacoesService.getAvaliacao(id));
    }

    @GetMapping("/getbyrestaurante/{idRestaurante}")
    public ResponseEntity<List<Avaliacoes>> findByRestaurante(@PathVariable("idRestaurante") Long idRestaurante) {
        return ResponseEntity.ok().body(avaliacoesService.getAvaliacoesPorRestaurante(idRestaurante));
    }

    @GetMapping("/getbyreserva/{idReserva}")
    public ResponseEntity<Avaliacoes> findbyreserva(@PathVariable("idReserva") Long idReserva) {
        return ResponseEntity.ok().body(avaliacoesService.getAvaliacoesPorReserva(idReserva));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Avaliacoes> update(@PathVariable("id") Long id, @RequestBody AvaliacoesReqDTO avaliacoes) {
        return ResponseEntity.ok().body(avaliacoesService.updateAvaliacao(avaliacoes, id));
    }
}
