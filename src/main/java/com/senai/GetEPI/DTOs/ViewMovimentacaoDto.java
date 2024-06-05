package com.senai.GetEPI.DTOs;

import com.senai.GetEPI.Dominios.EmprestimoStatus;
import com.senai.GetEPI.Models.EpiModel;
import com.senai.GetEPI.Models.MovimentacaoModel;
import com.senai.GetEPI.Repositories.EpiRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class ViewMovimentacaoDto {


     EpiRepository epiRepository;

    private Long id;

    private String dataMovimentacao;

    private Long quantidade;

    private String tipoMovimentacao;

    private String colaborador;

    private String EPI;



    public ViewMovimentacaoDto(){

    }

    public ViewMovimentacaoDto(MovimentacaoModel movimentacao){

        this.id = movimentacao.getId();
        this.dataMovimentacao = formatarData(movimentacao.getDataMovimentacao());
        this.quantidade = movimentacao.getQuantidade();
        this.colaborador = retornaColaborador(movimentacao);
        this.EPI = retornaEpi(movimentacao);
        this.tipoMovimentacao = movimentacao.getTipoMovimentacao().getDescricao();

    }


    private String retornaColaborador(MovimentacaoModel movimentacaoModel){

        if(movimentacaoModel.getEmprestimoModel() == null) {

            return "ENTRADA DE ESTOQUE";
        }
        String colaborador = movimentacaoModel.getEmprestimoModel().getColaborador().getNome();

        return colaborador;
    }


    private String retornaEpi(MovimentacaoModel movimentacaoModel){

        if(movimentacaoModel.getEmprestimoModel() == null) {


            return "EPI NÃO ENCONTRADO";
        }


        String epi = movimentacaoModel.getEmprestimoModel().getEpi().getNomeEpi();

        return epi;
    }



    private String formatarData(Date data) {
        if(data == null) {
            return EmprestimoStatus.NAO_DEVOLVIDO.getDescricao();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(data);
    }

}
