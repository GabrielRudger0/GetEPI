package com.senai.GetEPI.OutrosObjetos;

import lombok.Data;

@Data
public class ErroGetEPI {

    private Boolean exibeErro;

    private String mensagemErro;

    public ErroGetEPI() {
    }

    public ErroGetEPI(Boolean exibeErro, String mensagemErro) {
        this.exibeErro = exibeErro;
        this.mensagemErro = mensagemErro;
    }
}
