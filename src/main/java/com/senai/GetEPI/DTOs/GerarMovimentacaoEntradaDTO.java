package com.senai.GetEPI.DTOs;

import com.senai.GetEPI.Models.EpiModel;
import com.senai.GetEPI.Models.UsuarioModel;
import lombok.Data;

@Data
public class GerarMovimentacaoEntradaDTO {

    private EpiModel epi;

    private Long quantidade;

    public GerarMovimentacaoEntradaDTO() {
    }
}
