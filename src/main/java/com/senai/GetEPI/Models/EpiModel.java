package com.senai.GetEPI.Models;

import com.senai.GetEPI.DTOs.EpiDto;
import com.senai.GetEPI.DTOs.TipoEquipamentoDTO;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Epi")
public class EpiModel {


    @Id
    @Column(name = "id_Epi")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeEpi;

    @ManyToOne
    private TipoEquipamentoModel descricao;

    public EpiModel(){

    }

    public EpiModel(Long id, String nomeEpi, TipoEquipamentoModel descricao){
        this.id = id;
        this.nomeEpi = nomeEpi;
        this.descricao = descricao;
    }

    public EpiModel(EpiDto epi, TipoEquipamentoModel descricao){
        this.id                 = epi.getId();
        this.nomeEpi            = epi.getNomeEpi();
        this.descricao          = descricao;
    }

}
