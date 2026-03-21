package com.aguiar.dispensas_militares.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "dispensas")
public class Dispensa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "militar_id", nullable = false)
    private Militar militar;

    @Column(nullable = false)
    private LocalDateTime dataHoraRegistro;

    @Column(nullable = false)
    private LocalDate dataInicio;

    @Column(nullable = false)
    private LocalDate dataFim;

    private String observacoes;

    @Column(columnDefinition = "bytea")
    private byte[] pdf;

    private String nomeArquivoPdf;
}