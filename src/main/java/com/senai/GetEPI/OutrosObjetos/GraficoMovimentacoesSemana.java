package com.senai.GetEPI.OutrosObjetos;

import lombok.Data;

@Data
public class GraficoMovimentacoesSemana {

    private String[] diasSemana;

    private String[] resultadoSemanaDevolucao;

    private String[] resultadoSemanaEmprestimo;

    public GraficoMovimentacoesSemana(String[] diasSemana, String[] resultadoSemanaDevolucao, String[] resultadoSemanaEmprestimo) {
        this.diasSemana = diasSemana;
        this.resultadoSemanaDevolucao = resultadoSemanaDevolucao;
        this.resultadoSemanaEmprestimo = resultadoSemanaEmprestimo;
    }
}
