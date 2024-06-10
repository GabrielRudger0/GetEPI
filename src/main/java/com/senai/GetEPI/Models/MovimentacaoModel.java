package com.senai.GetEPI.Models;

import com.senai.GetEPI.Dominios.OrigemMovimentacao;
import com.senai.GetEPI.Dominios.TipoMovimentacao;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "Movimentacao")
@Data
public class MovimentacaoModel {

    @Id
    @Column(name = "movimentacaoId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private OrigemMovimentacao origem;

    private Date dataMovimentacao;

    private Long quantidade;

    private TipoMovimentacao tipoMovimentacao;

    @ManyToOne
    private EmprestimoModel emprestimoModel;

}
