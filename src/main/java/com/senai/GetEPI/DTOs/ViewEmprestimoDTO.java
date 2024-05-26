package com.senai.GetEPI.DTOs;

import com.senai.GetEPI.Models.ColaboradorModel;
import com.senai.GetEPI.Models.EmprestimoModel;
import com.senai.GetEPI.Models.EpiModel;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class ViewEmprestimoDTO {

    private Long id;

    private String colaborador;

    private String epi;

    private String emissaoData;

    private String devolucaoData;

    public ViewEmprestimoDTO() {
    }

    public ViewEmprestimoDTO(EmprestimoModel emprestimo) {
        this.id = emprestimo.getId();
        this.colaborador = emprestimo.getColaborador().getNome().toUpperCase();
        this.epi = emprestimo.getEpi().getNomeEpi().toUpperCase();
        this.emissaoData = formatarData(emprestimo.getEmissaoData());
        this.devolucaoData =  formatarData(emprestimo.getDevolucaoData());
    }


    private String formatarData(Date data) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(data);
    }

}
