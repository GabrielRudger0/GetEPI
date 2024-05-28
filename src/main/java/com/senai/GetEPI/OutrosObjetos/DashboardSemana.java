package com.senai.GetEPI.OutrosObjetos;

import lombok.Data;

import java.util.List;

@Data
public class DashboardSemana {

    private String diaSemana;

    private Integer quantidadeEmprestimo;

    private Integer quantidadeDevolucao;

    public DashboardSemana() {
    }

}
