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

    public DashboardSemana(int diaSemana, Integer quantidadeEmprestimo, Integer quantidadeDevolucao) {
        this.diaSemana = retornaStringDia(diaSemana);
        this.quantidadeEmprestimo = quantidadeEmprestimo;
        this.quantidadeDevolucao = quantidadeDevolucao;
    }

    private String retornaStringDia(Integer diaInteiro) {
        String dia;
        switch (diaInteiro) {
            case 1:
                dia = "Domingo";
                break;
            case 2:
                dia = "Segunda-feira";
                break;
            case 3:
                dia = "Terça-feira";
                break;
            case 4:
                dia = "Quarta-feira";
                break;
            case 5:
                dia = "Quinta-feira";
                break;
            case 6:
                dia = "Sexta-feira";
                break;
            case 7:
                dia = "Sábado";
                break;
            default:
                dia = "Número inválido";
                break;
        }
        return dia;
    }
}
