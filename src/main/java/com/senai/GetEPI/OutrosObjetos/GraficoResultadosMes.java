package com.senai.GetEPI.OutrosObjetos;

import lombok.Data;

@Data
public class GraficoResultadosMes {

    private String[] meses;

    private String[] graficoEPIs;

    private String[] resultadosMesEPI1;

    private String[] resultadosMesEPI2;

    private String[] resultadosMesEPI3;

    public GraficoResultadosMes() {
    }

    public GraficoResultadosMes(String[] meses, String[] graficoEPIs, String[] resultadosMesEPI1, String[] resultadosMesEPI2, String[] resultadosMesEPI3) {
        this.meses = meses;
        this.graficoEPIs = graficoEPIs;
        this.resultadosMesEPI1 = resultadosMesEPI1;
        this.resultadosMesEPI2 = resultadosMesEPI2;
        this.resultadosMesEPI3 = resultadosMesEPI3;
    }
}
