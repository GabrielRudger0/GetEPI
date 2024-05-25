package com.senai.GetEPI.Models;

import com.senai.GetEPI.DTOs.EpiDto;
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

    private String tipoEquipamentoEpi;

    public EpiModel(){

    }

    public EpiModel(EpiDto epi){
        this.id                 = epi.getId();
        this.nomeEpi            = epi.getNomeEpi();
        this.tipoEquipamentoEpi = epi.getTipoEpi();
    }

}
