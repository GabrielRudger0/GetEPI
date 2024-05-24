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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    public List<FuncaoDto> obterListaFuncao() {

        List<FuncaoModel> listaFuncaoModel = funcaoRepository.findAll();

        List<FuncaoDto> listaFuncao = new ArrayList<>();

        for (FuncaoModel funcao : listaFuncaoModel) {

            FuncaoDto funcaoDto = new FuncaoDto();
            funcaoDto.setId(funcao.getId());
            funcaoDto.setFuncao(funcao.getFuncao());

            listaFuncao.add(funcaoDto);
        }

        return listaFuncao;

    }

    public boolean excluirFuncao(Long id){
        Optional<FuncaoModel> optionalFuncao = funcaoRepository.findById(id);
        if (!optionalFuncao.isPresent()){
            return false;
        }
        funcaoRepository.delete(optionalFuncao.get());
        return true;

    }



}
