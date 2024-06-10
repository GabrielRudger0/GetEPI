package com.senai.GetEPI.Dominios;

public enum OrigemMovimentacao {


    EMPRESTIMO("EMPRÉSTIMO"),
    DEVOLUCAO("DEVOLUÇÃO"),
    EXCLUSAO_REGISTRO("EXCLUSÃO DE REGISTRO"),
    REGISTRO_EQUIPAMENTO("REGISTRO DE EQUIPAMENTO"),
    ENTRADA_ESTOQUE("ENTRADA DE ESTOQUE"),
    ALTERACAO_ESTOQUE("ALTERAÇÃO DE ESTOQUE");

    private final String descricao;

    private OrigemMovimentacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
