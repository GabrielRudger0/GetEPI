package com.senai.GetEPI.DTOs;

import com.senai.GetEPI.Dominios.EmprestimoStatus;
import com.senai.GetEPI.Models.MovimentacaoModel;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class ViewMovimentacaoDto {

    private Long id;

    private String dataMovimentacao;

    private Long quantidade;

    private String tipoMovimentacao;

    private String colaboradorEpi;

    public ViewMovimentacaoDto(){

    }

    public ViewMovimentacaoDto(MovimentacaoModel movimentacao){

        this.id = movimentacao.getId();
        this.dataMovimentacao = formatarData(movimentacao.getDataMovimentacao());
        this.quantidade = movimentacao.getQuantidade();
        this.colaboradorEpi = retornaEpiColaborador(movimentacao);
        this.tipoMovimentacao =movimentacao.getTipoMovimentacao().getDescricao();

    }


    private String retornaEpiColaborador(MovimentacaoModel movimentacaoModel){

        if(movimentacaoModel.getEmprestimoModel() == null) {
            return "ENTRADA DE ESTOQUE";
        }

        String colaborador = movimentacaoModel.getEmprestimoModel().getColaborador().getNome();
        String epi = movimentacaoModel.getEmprestimoModel().getEpi().getNomeEpi();

        return colaborador + "/" + epi;
    }

    private String formatarData(Date data) {
        if(data == null) {
            return EmprestimoStatus.NAO_DEVOLVIDO.getDescricao();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(data);
    }

}
