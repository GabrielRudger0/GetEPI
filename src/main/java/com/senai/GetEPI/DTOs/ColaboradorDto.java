package com.senai.GetEPI.DTOs;

import com.senai.GetEPI.Models.ColaboradorModel;
import com.senai.GetEPI.Models.UsuarioModel;
import lombok.Data;

import java.util.Date;

@Data
public class ColaboradorDto {

    private Long id;
    private String nome;

    private String email;

    private String funcao;

    private String dataNascimento;

    public ColaboradorDto(){

    }
    public ColaboradorDto(Long id, String email, String funcao,String dataNascimento){
        this.id = id;
        this.email = email;
        this.nome = nome;
        this.funcao = funcao;
        this.dataNascimento = dataNascimento;
    }

    public ColaboradorDto(ColaboradorModel colaboradorModel) {
        this.id = colaboradorModel.getId();
        this.email = colaboradorModel.getEmail();
        this.nome = colaboradorModel.getNome();
        this.funcao = colaboradorModel.getFuncao();
        this.dataNascimento = colaboradorModel.getDataNascimento().toString();
    }
}
