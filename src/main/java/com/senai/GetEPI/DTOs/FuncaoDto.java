package com.senai.GetEPI.DTOs;


import com.senai.GetEPI.Models.FuncaoModel;
import lombok.Data;

@Data
public class FuncaoDto {

    private Long id;

    private String funcao;

    public FuncaoDto(){

    }

    public FuncaoDto(FuncaoModel funcao){
        this.id = funcao.getId();
        this.funcao = funcao.getFuncao();
    }
}
