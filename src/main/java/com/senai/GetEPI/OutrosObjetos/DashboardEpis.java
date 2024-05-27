package com.senai.GetEPI.OutrosObjetos;

import lombok.Data;

@Data
public class DashboardEpis {

    private Long epiId;

    private String epiDescricao;

    private Integer quantidadeEmprestimos;

    public DashboardEpis() {
    }

    public DashboardEpis(Long epiId, String epiDescricao, Integer quantidadeEmprestimos) {
        this.epiId = epiId;
        this.epiDescricao = epiDescricao;
        this.quantidadeEmprestimos = quantidadeEmprestimos;
    }
}
