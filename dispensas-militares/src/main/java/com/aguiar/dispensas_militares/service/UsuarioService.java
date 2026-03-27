package com.aguiar.dispensas_militares.service;

import com.aguiar.dispensas_militares.model.Perfil;
import com.aguiar.dispensas_militares.model.Usuario;
import com.aguiar.dispensas_militares.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final TotpService totpService;

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public void salvar(Usuario usuario) {
        // Verifica se já existe um MODERADOR cadastrado
        if (usuario.getPerfil() == Perfil.MODERADOR) {
            boolean jaExisteModerador = usuarioRepository.findAll()
                    .stream()
                    .anyMatch(u -> u.getPerfil() == Perfil.MODERADOR);

            if (jaExisteModerador) {
                throw new RuntimeException("Já existe um Moderador cadastrado no sistema!");
            }
        }

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

        if (usuarioLogado.getPerfil() == Perfil.ADMINISTRADOR
                && usuarioAlvo.getPerfil() == Perfil.MODERADOR) {
            throw new RuntimeException("Administrador não pode deletar um Moderador!");
        }

        usuarioRepository.deleteById(id);
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

    public String ativar2FA(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        String secret = totpService.gerarSecretKey();
        usuario.setTotpSecret(secret);
        usuarioRepository.save(usuario);

        return totpService.gerarQRUrl(username, secret);
    }

    public void confirmar2FA(String username, int codigo) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        if (!totpService.validarCodigo(usuario.getTotpSecret(), codigo)) {
            throw new RuntimeException("Código inválido!");
        }

        usuario.setTotpAtivo(true);
        usuarioRepository.save(usuario);
    }

    public boolean validar2FA(String username, int codigo) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        return totpService.validarCodigo(usuario.getTotpSecret(), codigo);
    }
}