package com.senai.GetEPI.DTOs;

import com.senai.GetEPI.Dominios.EmprestimoStatus;
import com.senai.GetEPI.Models.ColaboradorModel;
import com.senai.GetEPI.Models.FuncaoModel;
import com.senai.GetEPI.Models.UsuarioModel;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class ColaboradorDto {

    private Long id;

    private String nome;

    private String email;

    private FuncaoModel funcao;

    private String dataNascimento;

    public ColaboradorDto(){

    }
    public ColaboradorDto(Long id, String email,String nome,FuncaoModel funcao,String dataNascimento){
        this.id = id;
        this.email = email;
        this.nome = nome;
        this.funcao = funcao;
        this.dataNascimento = dataNascimento;
    }

    public ColaboradorDto(ColaboradorModel colaboradorModel,FuncaoModel funcao) {
        this.id = colaboradorModel.getId();
        this.email = colaboradorModel.getEmail();
        this.nome = colaboradorModel.getNome();
        this.funcao = funcao;
        this.dataNascimento = formatarData(colaboradorModel.getDataNascimento());
    }

    public ColaboradorDto(ColaboradorModel colaboradorModel) {
        this.id = colaboradorModel.getId();
        this.email = colaboradorModel.getEmail();
        this.nome = colaboradorModel.getNome();
        this.funcao = colaboradorModel.getFuncao();
        this.dataNascimento = formatarData(colaboradorModel.getDataNascimento());
    }

    private String formatarData(Date data) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(data);
    }

}
