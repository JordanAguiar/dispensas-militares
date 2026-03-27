package com.aguiar.dispensas_militares.service;

import com.aguiar.dispensas_militares.model.Dispensa;
import com.aguiar.dispensas_militares.model.Militar;
import com.aguiar.dispensas_militares.repository.DispensaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DispensaService {

    private final DispensaRepository dispensaRepository;

    public List<Dispensa> listarPorMilitar(Militar militar) {
        return dispensaRepository.findByMilitarOrderByDataHoraRegistroDesc(militar);
    }

    public Dispensa salvar(Dispensa dispensa, MultipartFile arquivo) throws IOException {
        dispensa.setDataHoraRegistro(LocalDateTime.now());

        if (arquivo != null && !arquivo.isEmpty()) {
            dispensa.setPdf(arquivo.getBytes());
            dispensa.setNomeArquivoPdf(arquivo.getOriginalFilename());
        }

        return dispensaRepository.save(dispensa);
    }

    public Dispensa buscarPorId(Long id) {
        return dispensaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dispensa não encontrada!"));
    }

    public void deletar(Long id) {
        dispensaRepository.deleteById(id);
    }
}