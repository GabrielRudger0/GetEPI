package com.senai.GetEPI.DTOs;

import com.senai.GetEPI.Models.EpiModel;
import com.senai.GetEPI.Models.TipoEquipamentoModel;
import lombok.Data;

@Data
public class EpiDto {

  private Long id;

  private String nomeEpi;

  private TipoEquipamentoModel tipoEquipamento;

  private Long QuatidadeEpi;

  public EpiDto(){

  }

  public EpiDto(EpiModel epi){
    this.id               = epi.getId();
    this.nomeEpi          = epi.getNomeEpi();
    this.tipoEquipamento  = epi.getTipoEquipamento();
    this.QuatidadeEpi     = epi.getQuatidadeEpi();
  }

  public EpiDto(EpiModel epi, TipoEquipamentoModel tipoEquipamento){
    this.id               = epi.getId();
    this.nomeEpi          = epi.getNomeEpi();
    this.tipoEquipamento = tipoEquipamento;
  }

}
