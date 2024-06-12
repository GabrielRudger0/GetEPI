package com.senai.GetEPI.DTOs;

import com.senai.GetEPI.Dominios.EmprestimoStatus;
import com.senai.GetEPI.Models.ColaboradorModel;
import com.senai.GetEPI.Models.FuncaoModel;
import com.senai.GetEPI.Models.UsuarioModel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class UpdColaboradorDTO {

    private Long id;

    private String nome;

    private String email;

    private FuncaoModel funcao;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataNascimento;

    private UsuarioModel usuarioVinculado;

    public UpdColaboradorDTO(){

    }

    public UpdColaboradorDTO(ColaboradorModel colaboradorModel){
        this.id             = colaboradorModel.getId();
        this.nome           = colaboradorModel.getNome();
        this.email          = colaboradorModel.getEmail();
        this.funcao         = colaboradorModel.getFuncao();
        this.dataNascimento = colaboradorModel.getDataNascimento();
        this.usuarioVinculado   = colaboradorModel.getUsuario();


    }

}
