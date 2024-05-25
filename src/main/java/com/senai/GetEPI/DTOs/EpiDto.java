package com.senai.GetEPI.DTOs;

import com.senai.GetEPI.Models.EpiModel;
import lombok.Data;

@Data
public class EpiDto {

  private Long id;

  private String nomeEpi;

  private String descricaoEpi;

  public EpiDto(){

  }

  public EpiDto(EpiModel epi){
    this.id           = epi.getId();
    this.nomeEpi      = epi.getNomeEpi();
    this.descricaoEpi = epi.getTipoEquipamentoEpi();
  }
}
