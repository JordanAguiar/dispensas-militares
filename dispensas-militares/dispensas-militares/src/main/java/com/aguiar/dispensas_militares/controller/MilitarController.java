package com.aguiar.dispensas_militares.controller;

import com.aguiar.dispensas_militares.model.Militar;
import com.aguiar.dispensas_militares.service.MilitarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@RequestMapping("/militares")
public class MilitarController {

    private final MilitarService militarService;

    @GetMapping
    public String listar(@RequestParam(required = false) String busca, Model model) {
        if (busca != null && !busca.isEmpty()) {
            model.addAttribute("militares", militarService.buscarPorNipContaining(busca));
        } else {
            model.addAttribute("militares", militarService.listarTodos());
        }
        model.addAttribute("busca", busca);
        return "militares/listar";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("militar", new Militar());
        return "militares/form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Militar militar) {
        militar.setDataInclusao(LocalDate.now());
        militarService.salvar(militar);
        return "redirect:/militares";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        militarService.deletar(id);
        return "redirect:/militares";
    }
}