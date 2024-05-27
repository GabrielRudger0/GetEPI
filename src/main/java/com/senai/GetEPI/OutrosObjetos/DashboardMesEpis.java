package com.senai.GetEPI.OutrosObjetos;

import lombok.Data;

import java.util.Calendar;
import java.util.List;

@Data
public class DashboardMesEpis {

    private String mes;

    private List<DashboardEpis> epis;

    private List<Integer> qtdEmprestimo;


}
