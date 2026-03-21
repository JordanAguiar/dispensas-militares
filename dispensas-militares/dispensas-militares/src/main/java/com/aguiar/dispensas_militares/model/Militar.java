package com.aguiar.dispensas_militares.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "militares")

public class Militar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nip;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String posto;

    @Column(nullable = false)
    private String secao;

    @Column(nullable = false)
    private LocalDate dataInclusao;

}
