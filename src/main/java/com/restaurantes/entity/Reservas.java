package com.restaurantes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reservas")
public class Reservas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_registro", updatable = false)
    @CreationTimestamp
    private LocalDateTime dataRegistro;

    @Column(name = "datahora_reserva", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private LocalDateTime dataHoraReserva;

    @Column(name = "datahora_atualizacao", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private LocalDateTime dataHoraAtualizacao;

    @Column(name = "status_reserva")
    private String statusReserva;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurante_id", referencedColumnName = "id", nullable = false)
    private Restaurante restaurante;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false)
    private Usuario usuario;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "mesa_id", referencedColumnName = "id", nullable = false)
    private MesasRestaurante mesa;

}