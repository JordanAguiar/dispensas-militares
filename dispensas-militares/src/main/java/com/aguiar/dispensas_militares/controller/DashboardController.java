package com.aguiar.dispensas_militares.controller;

import com.aguiar.dispensas_militares.model.Companhia;
import com.aguiar.dispensas_militares.repository.DispensaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashboardController {

    private final DispensaRepository dispensaRepository;

    @GetMapping
    public String dashboard(Model model) {
        LocalDateTime agora = LocalDateTime.now();

        // Contadores de período
        model.addAttribute("totalHoje", dispensaRepository.countByDataHoraRegistroAfter(agora.toLocalDate().atStartOfDay()));
        model.addAttribute("totalSemana", dispensaRepository.countByDataHoraRegistroAfter(agora.minusWeeks(1)));
        model.addAttribute("totalMes", dispensaRepository.countByDataHoraRegistroAfter(agora.withDayOfMonth(1).toLocalDate().atStartOfDay()));
        model.addAttribute("totalAno", dispensaRepository.countByDataHoraRegistroAfter(agora.withDayOfYear(1).toLocalDate().atStartOfDay()));

        // Dispensas por companhia
        Map<String, Long> porCompanhia = new LinkedHashMap<>();
        for (Companhia c : Companhia.values()) {
            porCompanhia.put(c.getDescricao(), dispensaRepository.countByCompanhia(c));
        }
        model.addAttribute("porCompanhia", porCompanhia);

        // Top militares
        model.addAttribute("topMilitares", dispensaRepository.findMilitaresComMaisDispensas());

        // Dispensas por mês no ano atual
        int anoAtual = agora.getYear();
        Map<String, Long> porMes = new LinkedHashMap<>();
        String[] meses = {"Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"};
        for (String mes : meses) porMes.put(mes, 0L);

        List<Object[]> resultados = dispensaRepository.countByMesNoAno(anoAtual);
        for (Object[] row : resultados) {
            int mes = ((Number) row[0]).intValue();
            long count = ((Number) row[1]).longValue();
            porMes.put(meses[mes - 1], count);
        }
        model.addAttribute("porMes", porMes);
        model.addAttribute("anoAtual", anoAtual);

        return "dashboard";
    }
}