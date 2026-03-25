package com.aguiar.dispensas_militares.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void enviarEmailRecuperacao(String email, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Recuperação de Senha - SISDIS Marinha do Brasil");
        message.setText(
                "Olá!\n\n" +
                        "Recebemos uma solicitação de recuperação de senha para sua conta no SISDIS.\n\n" +
                        "Clique no link abaixo para redefinir sua senha:\n\n" +
                        "http://localhost:8080/redefinir-senha?token=" + token + "\n\n" +
                        "Este link expira em 30 minutos.\n\n" +
                        "Se você não solicitou a recuperação de senha, ignore este email.\n\n" +
                        "Marinha do Brasil - SISDIS"
        );
        mailSender.send(message);
    }
}