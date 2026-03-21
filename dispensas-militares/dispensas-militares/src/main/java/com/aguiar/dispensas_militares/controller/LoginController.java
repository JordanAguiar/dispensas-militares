package com.aguiar.dispensas_militares.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

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
}
