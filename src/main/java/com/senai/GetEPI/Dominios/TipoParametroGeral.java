package com.senai.GetEPI.Dominios;

public enum TipoParametroGeral {

    FuncaoPadraoUsuario("FUNÇÃO PADRÃO"),
    fucaoteste("TESTE");



    private final String descricao;

    private TipoParametroGeral(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
