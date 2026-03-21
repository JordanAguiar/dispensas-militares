package com.aguiar.dispensas_militares.model;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Data
@Entity
@Table(name = "militares")

public class Militar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 10)
    @Pattern(regexp = "\\d{2}\\.\\d{4}\\.\\d{2}",
            message = "NIP deve estar no formato XX.XXXX.XX")
    private String nip;

    @Column(nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Posto posto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Companhia companhia;

    @Column(nullable = false)
    private LocalDate dataInclusao;

}
