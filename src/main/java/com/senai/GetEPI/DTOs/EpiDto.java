package com.senai.GetEPI.DTOs;

import com.senai.GetEPI.Models.EpiModel;
import com.senai.GetEPI.Models.TipoEquipamentoModel;
import lombok.Data;

@Data
public class EpiDto {

  private Long id;

  private String nomeEpi;

  private TipoEquipamentoModel tipoEpi;

  public EpiDto(){

  }

  public EpiDto(Long id, String nome, TipoEquipamentoModel tipoEpi){
    this.id = id;
    this.nomeEpi = nome;
    this.tipoEpi = tipoEpi;
  }

  public EpiDto(EpiModel epi,TipoEquipamentoModel tpEquipamento){
    this.id           = epi.getId();
    this.nomeEpi      = epi.getNomeEpi();
    this.tipoEpi      = tpEquipamento;
  }

  public EpiDto(EpiModel epi){
    this.id           = epi.getId();
    this.nomeEpi      = epi.getNomeEpi();
    this.tipoEpi      = epi.getDescricao();
  }
}
