package com.senai.GetEPI.DTOs;

import com.senai.GetEPI.Models.ColaboradorModel;
import com.senai.GetEPI.Models.EmprestimoModel;
import com.senai.GetEPI.Models.EpiModel;
import lombok.Data;

import java.util.Date;

@Data
public class EmprestimoDTO {

    private Long id;

    private ColaboradorModel colaborador;

    private EpiModel epi;

    private Date emissaoData;

    private Date devolucaoData;

    private Boolean registroInterno;

    public EmprestimoDTO() {
    }

    public EmprestimoDTO(EmprestimoModel emprestimoModel) {
        this.id = emprestimoModel.getId();
        this.colaborador = emprestimoModel.getColaborador();
        this.epi = emprestimoModel.getEpi();
        this.emissaoData = emprestimoModel.getEmissaoData();
        this.devolucaoData = emprestimoModel.getDevolucaoData();
        this.registroInterno = emprestimoModel.getRegistroInterno();
    }

}
