package com.senai.GetEPI.Models;

import com.senai.GetEPI.DTOs.EmprestimoDTO;
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

    @Column(name = "registroInterno")
    private Boolean registroInterno;

    public EmprestimoModel() {
    }

    public EmprestimoModel(EmprestimoDTO emprestimo) {
        this.id = emprestimo.getId();
        this.colaborador = emprestimo.getColaborador();
        this.epi = emprestimo.getEpi();
        this.emissaoData = emprestimo.getEmissaoData();
        this.devolucaoData = emprestimo.getDevolucaoData();
        this.registroInterno = emprestimo.getRegistroInterno();
    }
}
