package com.senai.GetEPI.Models;

import com.senai.GetEPI.DTOs.TipoEquipamentoDTO;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "tipoEquipamento")
public class TipoEquipamentoModel {

    @Id
    @Column(name = "tipoEquipamentoId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipoEquipamentoDescricao", nullable = false, unique = true)
    private String descricao;

    public TipoEquipamentoModel() {
    }

    public TipoEquipamentoModel(TipoEquipamentoDTO tipoEquipamentoDTO) {
        this.id = tipoEquipamentoDTO.getId();
        this.descricao = tipoEquipamentoDTO.getDescricao().trim().toUpperCase();
    }

}
