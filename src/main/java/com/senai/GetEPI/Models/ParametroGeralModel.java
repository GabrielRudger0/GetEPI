package com.senai.GetEPI.Models;

import com.senai.GetEPI.Dominios.TipoParametroGeral;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ParametroGeral")
public class ParametroGeralModel {

    @Id
    @Column(name = "ParametroGeralId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ParametroGeralTipo", nullable = false)
    private TipoParametroGeral tipoParametroGeral;

    @Column(name = "ParametroGeralValor", nullable = false)
    private Long valor;

    public ParametroGeralModel() {
    }

    public ParametroGeralModel(Long id, TipoParametroGeral tipoParametro, Long valor) {
        this.id = id;
        this.tipoParametroGeral = tipoParametro;
        this.valor = valor;
    }
}
