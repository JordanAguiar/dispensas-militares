package com.aguiar.dispensas_militares.service;

import com.aguiar.dispensas_militares.model.Militar;
import com.aguiar.dispensas_militares.repository.MilitarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class MilitarService {
    private final MilitarRepository militarRepository;

    public List<Militar> listarTodos(){
        return militarRepository.findAll();
    }

    public Militar buscarPorNip(String nip){
        return militarRepository.findByNip(nip);
    }
    public List<Militar> buscarPorNome(String nome) {
        return militarRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Militar salvar(Militar militar) {
        if (militarRepository.findByNip(militar.getNip()) != null) {
            throw new RuntimeException("Já existe um militar com esse NIP!");
        }
        return militarRepository.save(militar);
    }

    public void deletar(Long id) {
        militarRepository.deleteById(id);
    }
    public Militar buscarPorId(Long id) {
        return militarRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Militar não encontrado!"));
    }
}
