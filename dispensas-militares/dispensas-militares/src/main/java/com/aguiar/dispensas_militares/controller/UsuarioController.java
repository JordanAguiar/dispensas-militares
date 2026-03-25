package com.aguiar.dispensas_militares.controller;

import org.springframework.security.core.Authentication;
import com.aguiar.dispensas_militares.model.Perfil;
import com.aguiar.dispensas_militares.model.Usuario;
import com.aguiar.dispensas_militares.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("usuarios", usuarioService.listarTodos());
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("perfis", Perfil.values());
        return "usuarios/listar";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Usuario usuario) {
        usuarioService.salvar(usuario);
        return "redirect:/usuarios";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id, Authentication authentication, Model model) {
        try {
            usuarioService.deletar(id, authentication.getName());
            return "redirect:/usuarios";
        } catch (RuntimeException e) {
            return "redirect:/acesso-negado";
        }
    }
}