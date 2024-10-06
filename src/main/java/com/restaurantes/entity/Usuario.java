package com.restaurantes.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuario")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_registro")
    @CreationTimestamp
    private LocalDateTime dataRegistro;

    @Column(name = "nome_completo", length = 100, nullable = false)
    @NotEmpty(message = "Usuário não pode ser vazio")
    private String nomeCompleto;

    @Column(name = "email", length = 100, nullable = false)
    @NotEmpty(message = "E-mail não pode ser vazio")
    private String email;

    @Column(name = "senha", length = 100)
    private String senha;

    @Column(name = "telefone", length = 11)
    private String telefone;

   }
