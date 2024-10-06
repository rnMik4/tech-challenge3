package com.restaurantes.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "avaliacoes")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class Avaliacoes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_registro", updatable = false)
    @CreationTimestamp
    private LocalDateTime dataRegistro;

    @Column(name = "avaliacao", length = 200)
    private Integer avaliacao;

    @Column(name = "comentario", length = 400)
    private String comentario;

    @Column(name = "id_reserva")
    private Long idReserva;

    @Column(name = "id_restaurante")
    private Long idRestaurante;
}
