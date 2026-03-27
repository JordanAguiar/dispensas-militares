package com.aguiar.dispensas_militares.model;

public enum Posto {
    MN("MN"),
    SD("SD"),
    CB("CB"),
    SG("SG"),
    TEN("TEN"),
    CT("CT"),
    CC("CC"),
    CF("CF"),
    CMG("CMG");

    private final String descricao;

    Posto(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}