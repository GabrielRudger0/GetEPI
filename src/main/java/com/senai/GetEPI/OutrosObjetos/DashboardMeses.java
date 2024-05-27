package com.senai.GetEPI.OutrosObjetos;

import lombok.Data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Data
public class DashboardMeses {

    private String mesReferente;

    private Calendar numeroMesReferente;

    private Date dataInicio;

    private Date dataFim;

    public DashboardMeses() {
    }

    public DashboardMeses(String mesReferente, Date dataInicio, Date dataFim, Calendar numeroMesReferente) {
        this.mesReferente = mesReferente;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.numeroMesReferente = numeroMesReferente;
    }
}
