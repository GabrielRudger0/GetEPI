package com.senai.GetEPI.Services;

import com.senai.GetEPI.DTOs.FuncaoDto;
import com.senai.GetEPI.DTOs.UsuarioDTO;
import com.senai.GetEPI.Models.UsuarioModel;
import org.springframework.stereotype.Service;

@Service
public class FuncaoService {

    public String inserirFuncao(FuncaoDto funcao) {
        if (!mensagemErroUsuario(funcao).isEmpty()) {
            return mensagemErroUsuario(funcao);
        }
        usuarioRepository.save(new UsuarioModel(funcao));
        return "";

    }

}
