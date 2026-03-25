package com.aguiar.dispensas_militares.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Perfil perfil;
    @Column
    private String resetToken;

    @Column
    private LocalDateTime resetTokenExpiry;

    @Column(unique = true)
    private String email;

    @Column
    private String totpSecret;

    @Column(nullable = false)
    private boolean totpAtivo = false;
}