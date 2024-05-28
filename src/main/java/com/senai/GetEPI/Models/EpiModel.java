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

    @Column(unique = true)
    private String nomeEpi;

    @ManyToOne
    private TipoEquipamentoModel tipoEquipamento;

    private Long QuatidadeEpi;

    public EpiModel(){

    }

    public EpiModel(Long id, String nomeEpi, TipoEquipamentoModel tipoEquipamento,Long QuatidadeEpi){
        this.id              = id;
        this.nomeEpi         = nomeEpi;
        this.tipoEquipamento = tipoEquipamento;
        this.QuatidadeEpi    =  QuatidadeEpi;
    }

    public EpiModel(EpiDto epi, TipoEquipamentoModel tipoEquipamento){
        this.id              = epi.getId();
        this.nomeEpi         = epi.getNomeEpi();
        this.tipoEquipamento = tipoEquipamento;
        this.QuatidadeEpi    = epi.getQuatidadeEpi();
    }

}
