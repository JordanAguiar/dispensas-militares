package com.aguiar.dispensas_militares.controller;

import com.aguiar.dispensas_militares.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UsuarioService usuarioService;

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error,
                        @RequestParam(required = false) String logout,
                        Model model) {
        if (error != null) {
            model.addAttribute("erro", "Usuário ou senha inválidos!");
        }
        if (logout != null) {
            model.addAttribute("mensagem", "Você saiu do sistema.");
        }
        return "login";
    }

    @GetMapping("/acesso-negado")
    public String acessoNegado() {
        return "acesso-negado";
    }

    @GetMapping("/esqueci-senha")
    public String esqueciSenha() {
        return "esqueci-senha";
    }

    @PostMapping("/esqueci-senha")
    public String esqueciSenhaPost(@RequestParam String email, Model model) {
        try {
            usuarioService.solicitarRecuperacao(email);
            model.addAttribute("mensagem", "Email de recuperação enviado com sucesso!");
        } catch (Exception e) {
            model.addAttribute("erro", "Email não encontrado no sistema!");
        }
        return "esqueci-senha";
    }

    @GetMapping("/redefinir-senha")
    public String redefinirSenha(@RequestParam String token, Model model) {
        model.addAttribute("token", token);
        return "redefinir-senha";
    }

    @PostMapping("/redefinir-senha")
    public String redefinirSenhaPost(@RequestParam String token,
                                     @RequestParam String novaSenha,
                                     Model model) {
        try {
            usuarioService.redefinirSenha(token, novaSenha);
            model.addAttribute("mensagem", "Senha redefinida com sucesso! Faça login.");
            return "redirect:/login?logout=true";
        } catch (Exception e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("token", token);
            return "redefinir-senha";
        }
    }
}