package com.aguiar.dispensas_militares.repository;

import com.aguiar.dispensas_militares.model.Militar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface MilitarRepository extends JpaRepository<Militar, Long> {
    List<Militar> findByNomeContainingIgnoreCase(String nome);

    Militar findByNip(String nip);

    List<Militar> findByNipContainingIgnoreCase(String nip);
}
