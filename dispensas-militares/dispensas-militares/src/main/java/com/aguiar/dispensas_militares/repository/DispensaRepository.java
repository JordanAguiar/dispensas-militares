package com.aguiar.dispensas_militares.repository;

import com.aguiar.dispensas_militares.model.Dispensa;
import com.aguiar.dispensas_militares.model.Militar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DispensaRepository extends JpaRepository<Dispensa, Long> {

    List<Dispensa> findByMilitar(Militar militar);

    List<Dispensa> findByMilitarOrderByDataHoraRegistroDesc(Militar militar);
}