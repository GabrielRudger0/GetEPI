package com.senai.GetEPI.Models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Usuario")
@Data
public class UsuarioModel {

    @Id
    @Column(name = "usuarioId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuarioNome", nullable = false)
    private String nome;

    @Column(name = "usuarioEmail", nullable = false, unique = true)
    private String email;

    @Column(name = "usuarioSenha", nullable = false)
    private String senha;

}
