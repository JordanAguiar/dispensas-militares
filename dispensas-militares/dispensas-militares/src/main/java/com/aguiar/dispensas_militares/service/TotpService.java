package com.aguiar.dispensas_militares.service;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import org.springframework.stereotype.Service;

@Service
public class TotpService {

    private final GoogleAuthenticator gAuth = new GoogleAuthenticator();

    public String gerarSecretKey() {
        GoogleAuthenticatorKey key = gAuth.createCredentials();
        return key.getKey();
    }

    public String gerarQRUrl(String username, String secret) {
        return GoogleAuthenticatorQRGenerator.getOtpAuthTotpURL(
                "SISDIS Marinha do Brasil",
                username,
                new GoogleAuthenticatorKey.Builder(secret).build()
        );
    }

    public boolean validarCodigo(String secret, int codigo) {
        return gAuth.authorize(secret, codigo);
    }
}