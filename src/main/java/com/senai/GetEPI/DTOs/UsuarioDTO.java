package com.senai.GetEPI.DTOs;

import lombok.Data;

@Data
public class UsuarioDTO {

    private Long id;

    private String email;

    private String nome;

    private String senha;
}
