package com.senai.GetEPI.Models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "Emprestimo")
@Data
public class EmprestimoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emprestimoId")
    private Long id;

    @ManyToOne
    private ColaboradorModel colaborador;

    @ManyToOne
    private EpiModel epi;

    @Column(name = "emprestimoEmissaoData")
    private Date emissaoData;

    @Column(name = "emprestimoDevolucao")
    private Date devolucaoData;

    public EmprestimoModel() {
    }
}
