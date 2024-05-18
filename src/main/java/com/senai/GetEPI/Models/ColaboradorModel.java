package com.senai.GetEPI.Models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name="COLABORADOR")
@Data
public class ColaboradorModel {

    @Id
    @Column(name = "id_colaborador")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String email;


    private String funcao;


    private Date dataNascimento;
}
