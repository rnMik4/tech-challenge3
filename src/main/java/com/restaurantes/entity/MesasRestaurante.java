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
@Table(name = "mesas_restaurante")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class MesasRestaurante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_registro", updatable = false)
    @CreationTimestamp
    private LocalDateTime dataRegistro;

    @Column(name = "nome_mesa", length = 200)
    private String nomeMesa;

    @Column(name = "qtd_lugares")
    private Integer lugares;

    @Column(name = "id_restaurante")
    private Long idRestaurante;

}
