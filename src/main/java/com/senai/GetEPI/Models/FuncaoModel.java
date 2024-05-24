package com.senai.GetEPI.Models;

import com.senai.GetEPI.DTOs.FuncaoDto;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Funcao")
@Data
public class FuncaoModel {

    @Id
    @Column(name = "funcaoId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "funcao", nullable = false, unique = true)
    private String funcao;

    public FuncaoModel(){

    }

    public FuncaoModel(FuncaoDto funcaoDto){
        this.id = funcaoDto.getId();
        this.funcao = funcaoDto.getFuncao();
    }

}
