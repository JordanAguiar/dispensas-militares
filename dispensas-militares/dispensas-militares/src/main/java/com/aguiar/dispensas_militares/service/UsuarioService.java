package com.aguiar.dispensas_militares.service;

import com.aguiar.dispensas_militares.service.EmailService;
import java.time.LocalDateTime;
import java.util.UUID;
import com.aguiar.dispensas_militares.model.Perfil;
import com.aguiar.dispensas_militares.model.Usuario;
import com.aguiar.dispensas_militares.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final EmailService emailService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public void solicitarRecuperacao(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email não encontrado!"));

        String token = UUID.randomUUID().toString();
        usuario.setResetToken(token);
        usuario.setResetTokenExpiry(LocalDateTime.now().plusMinutes(30));
        usuarioRepository.save(usuario);

        emailService.enviarEmailRecuperacao(email, token);
    }

    public void redefinirSenha(String token, String novaSenha) {
        Usuario usuario = usuarioRepository.findByResetToken(token)
                .orElseThrow(() -> new RuntimeException("Token inválido!"));

        if (usuario.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expirado!");
        }

        usuario.setPassword(passwordEncoder.encode(novaSenha));
        usuario.setResetToken(null);
        usuario.setResetTokenExpiry(null);
        usuarioRepository.save(usuario);
    }

    public void salvar(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioRepository.save(usuario);
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
    }

    public void deletar(Long id, String usernameLogado) {
        Usuario usuarioAlvo = buscarPorId(id);
        Usuario usuarioLogado = usuarioRepository.findByUsername(usernameLogado)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        // ADMINISTRADOR não pode deletar MODERADOR
        if (usuarioLogado.getPerfil() == Perfil.ADMINISTRADOR
                && usuarioAlvo.getPerfil() == Perfil.MODERADOR) {
            throw new RuntimeException("Administrador não pode deletar um Moderador!");
        }

        usuarioRepository.deleteById(id);
    }
}