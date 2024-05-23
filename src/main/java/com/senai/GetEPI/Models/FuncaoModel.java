package com.senai.GetEPI.Models;

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

    private String funcao;

}
