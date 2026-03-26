package com.aguiar.dispensas_militares.config;

import com.aguiar.dispensas_militares.model.Perfil;
import com.aguiar.dispensas_militares.model.Usuario;
import com.aguiar.dispensas_militares.repository.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UsuarioRepository usuarioRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        String username = authentication.getName();
        Usuario usuario = usuarioRepository.findByUsername(username).orElse(null);

        if (usuario == null) {
            response.sendRedirect("/login");
            return;
        }

        // MODERADOR entra direto sem 2FA
        if (usuario.getPerfil() == Perfil.MODERADOR) {
            response.sendRedirect("/militares");
            return;
        }

        // Outros perfis — 2FA não configurado ainda, força configurar
        if (!usuario.isTotpAtivo()) {
            response.sendRedirect("/2fa/configurar");
            return;
        }

        // 2FA configurado — pede validação
        HttpSession session = request.getSession();
        session.setAttribute("2fa_username", username);
        response.sendRedirect("/2fa/validar");
    }
}