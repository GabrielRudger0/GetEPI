package com.senai.GetEPI.DTOs;

import com.senai.GetEPI.Dominios.TipoParametroGeral;
import com.senai.GetEPI.Models.FuncaoModel;
import lombok.Data;

@Data
public class ParametroGeralDTO {

    private Long id;
    private TipoParametroGeral tipoFuncaoUsuario;
    private FuncaoModel valorFuncaoUsuario;

    public ParametroGeralDTO() {
    }

    public ParametroGeralDTO(Long id,
                             TipoParametroGeral tipoFuncaoUsuario,
                             FuncaoModel valorFuncaoUsuario) {
        this.id = id;
        this.tipoFuncaoUsuario = tipoFuncaoUsuario;
        this.valorFuncaoUsuario = valorFuncaoUsuario;
    }
}
