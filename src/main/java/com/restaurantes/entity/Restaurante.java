package com.restaurantes.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "restaurante")
@JsonIgnoreProperties(ignoreUnknown=true)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Restaurante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_registro")
    @CreationTimestamp
    private LocalDateTime dataRegistro;

    @Column(name = "nome_restaurante", length = 100)
    private String nomeRestaurante;

    @Column(name = "tipo_cozinha", length = 100)
    private String tipoCozinha;

    @Column(name = "logradouro", length = 100)
    private String logradouro;

    @Column(name = "bairro", length = 100)
    private String bairro;

    @Column(name = "numero", length = 10)
    private String numero;

    @Column(name = "cidade", length = 50)
    private String cidade;

    @Column(name = "uf", length = 2)
    private String uf;

    @Column(name = "pais", length = 50)
    private String pais;

    @Column(name = "cep", length = 8)
    private String cep;

    @Column(name = "telefone", length = 11)
    private String telefone;

    @Column(name = "cnpj", length = 14)
    private String cnpj;

    @Column(name = "horario_abertura")
    private Integer horarioAbertura;

    @Column(name = "horario_fechamento")
    private Integer horarioFechamento;

    @OneToMany(mappedBy = "idRestaurante")
    private List<MesasRestaurante> mesas;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = false)
    private Usuario dono;

    }
