package com.aguiar.dispensas_militares.model;

public enum Companhia {
    CIA_1("1ªCia"),
    CIA_2("2ªCia"),
    CIA_3("3ªCia"),
    CIAPOL("CIAPOL"),
    CCS("CCS");

    private final String descricao;

    Companhia(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}