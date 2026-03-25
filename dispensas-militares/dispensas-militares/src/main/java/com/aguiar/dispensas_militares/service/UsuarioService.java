package com.aguiar.dispensas_militares.service;

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

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
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