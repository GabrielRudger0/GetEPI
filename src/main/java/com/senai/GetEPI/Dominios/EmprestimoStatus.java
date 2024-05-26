package com.senai.GetEPI.Dominios;

public enum EmprestimoStatus {

    NAO_DEVOLVIDO("NÃO DEVOLVIDO"),
    CANCELADO("CANCELADO");

    private final String descricao;

    private EmprestimoStatus(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
