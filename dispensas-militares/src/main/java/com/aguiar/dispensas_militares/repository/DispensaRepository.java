package com.aguiar.dispensas_militares.repository;

import com.aguiar.dispensas_militares.model.Companhia;
import com.aguiar.dispensas_militares.model.Dispensa;
import com.aguiar.dispensas_militares.model.Militar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DispensaRepository extends JpaRepository<Dispensa, Long> {

    List<Dispensa> findByMilitar(Militar militar);

    List<Dispensa> findByMilitarOrderByDataHoraRegistroDesc(Militar militar);

    @Query("SELECT COUNT(d) FROM Dispensa d WHERE d.dataHoraRegistro >= :inicio")
    Long countByDataHoraRegistroAfter(@Param("inicio") LocalDateTime inicio);

    @Query("SELECT COUNT(d) FROM Dispensa d WHERE d.militar.companhia = :companhia")
    Long countByCompanhia(@Param("companhia") Companhia companhia);

    @Query("SELECT d.militar.nome, COUNT(d) as total FROM Dispensa d GROUP BY d.militar.nome ORDER BY total DESC")
    List<Object[]> findMilitaresComMaisDispensas();

    @Query("SELECT MONTH(d.dataHoraRegistro), COUNT(d) FROM Dispensa d WHERE YEAR(d.dataHoraRegistro) = :ano GROUP BY MONTH(d.dataHoraRegistro) ORDER BY MONTH(d.dataHoraRegistro)")
    List<Object[]> countByMesNoAno(@Param("ano") int ano);
}