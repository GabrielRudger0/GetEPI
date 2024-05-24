package com.senai.GetEPI.Services;

import com.senai.GetEPI.DTOs.ColaboradorDto;
import com.senai.GetEPI.DTOs.FuncaoDto;
import com.senai.GetEPI.DTOs.UsuarioDTO;
import com.senai.GetEPI.Models.ColaboradorModel;
import com.senai.GetEPI.Models.FuncaoModel;
import com.senai.GetEPI.Models.UsuarioModel;
import com.senai.GetEPI.Repositories.FuncaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service

public class FuncaoService {

    @Autowired
    FuncaoRepository funcaoRepository;

    public List<FuncaoDto> retornaListaFuncaoDTO() {
        return converterListaFuncaoDTO(funcaoRepository.findAll());
    }

    public List<FuncaoDto> converterListaFuncaoDTO(List<FuncaoModel> listaFuncaoModel) {
        return listaFuncaoModel.stream().map(FuncaoDto::new).collect(Collectors.toList());
    }

    public String cadastrarFuncao(FuncaoDto funcao) {

        funcaoRepository.save(new FuncaoModel(funcao));
        return "";

    }



}
