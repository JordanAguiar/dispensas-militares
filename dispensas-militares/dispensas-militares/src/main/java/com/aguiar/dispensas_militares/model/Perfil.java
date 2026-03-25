package com.aguiar.dispensas_militares.model;

public enum Perfil {
    MODERADOR("ROLE_MODERADOR"),
    ADMINISTRADOR("ROLE_ADMINISTRADOR"),
    CONSULTOR("ROLE_CONSULTOR"),
    AFILHADO("ROLE_AFILHADO");

    private final String role;

    Perfil(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}