package com.senai.GetEPI.Dominios;

public enum TipoMovimentacao {


    ENTRADA("ENTRADA"),
    SAIDA("SAÍDA");

    private final String descricao;

    private TipoMovimentacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
