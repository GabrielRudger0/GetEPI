package com.senai.GetEPI.OutrosObjetos;

import lombok.Data;

@Data
public class ErroGetEPI {

    private Boolean exibeErro;

    private String mensagemErro;

    private String stackTrace;

    public ErroGetEPI() {
    }

    public ErroGetEPI(Boolean exibeErro, String mensagemErro, String stackTrace) {
        this.exibeErro = exibeErro;
        this.mensagemErro = mensagemErro;
        this.stackTrace = stackTrace;
    }
}
