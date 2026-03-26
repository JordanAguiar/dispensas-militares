package com.aguiar.dispensas_militares.controller;

import com.aguiar.dispensas_militares.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/2fa")
public class TotpController {

    private final UsuarioService usuarioService;

    @GetMapping("/configurar")
    public String configurar(Authentication authentication, Model model) {
        String qrUrl = usuarioService.ativar2FA(authentication.getName());
        model.addAttribute("qrUrl", qrUrl);
        return "2fa/configurar";
    }

    @PostMapping("/confirmar")
    public String confirmar(@RequestParam int codigo,
                            Authentication authentication,
                            Model model) {
        try {
            usuarioService.confirmar2FA(authentication.getName(), codigo);
            return "redirect:/militares?2fa=ativado";
        } catch (RuntimeException e) {
            model.addAttribute("erro", "Código inválido! Tente novamente.");
            String qrUrl = usuarioService.ativar2FA(authentication.getName());
            model.addAttribute("qrUrl", qrUrl);
            return "2fa/configurar";
        }
    }

    @GetMapping("/validar")
    public String validar(HttpSession session, Model model) {
        String username = (String) session.getAttribute("2fa_username");
        if (username == null) return "redirect:/login";
        return "2fa/validar";
    }

    @PostMapping("/validar")
    public String validarPost(@RequestParam int codigo,
                              HttpSession session) {
        String username = (String) session.getAttribute("2fa_username");
        if (username == null) return "redirect:/login";

        try {
            boolean valido = usuarioService.validar2FA(username, codigo);
            if (valido) {
                session.removeAttribute("2fa_username");
                session.setAttribute("2fa_validado", true);
                return "redirect:/militares";
            } else {
                return "redirect:/2fa/validar?erro=true";
            }
        } catch (Exception e) {
            return "redirect:/2fa/validar?erro=true";
        }
    }
}