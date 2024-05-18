package com.senai.GetEPI.DTOs;

import com.senai.GetEPI.Models.UsuarioModel;
import lombok.Data;

@Data
public class UsuarioDTO {

    private Long id;

    private String email;

    private String nome;

    private String senha;

    public UsuarioDTO() {
    }

    public UsuarioDTO(Long id, String email, String nome, String senha) {
        this.id = id;
        this.email = email;
        this.nome = nome;
        this.senha = senha;
    }

    public UsuarioDTO(UsuarioModel usuarioModel) {
        this.id = usuarioModel.getId();
        this.email = usuarioModel.getEmail();
        this.nome = usuarioModel.getNome();
        this.senha = usuarioModel.getSenha();
    }
}
