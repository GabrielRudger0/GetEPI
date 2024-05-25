package com.senai.GetEPI.DTOs;

import com.senai.GetEPI.Models.TipoEquipamentoModel;
import lombok.Data;

@Data
public class TipoEquipamentoDTO {

    private Long id;

    private String descricao;

    public TipoEquipamentoDTO() {
    }

    public TipoEquipamentoDTO(TipoEquipamentoModel tipoEquipamentoModel) {
        this.id = tipoEquipamentoModel.getId();
        this.descricao = tipoEquipamentoModel.getDescricao();
    }

}
