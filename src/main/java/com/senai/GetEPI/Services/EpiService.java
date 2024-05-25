package com.senai.GetEPI.Services;

import com.senai.GetEPI.DTOs.ColaboradorDto;
import com.senai.GetEPI.DTOs.EpiDto;
import com.senai.GetEPI.DTOs.FuncaoDto;
import com.senai.GetEPI.Models.ColaboradorModel;
import com.senai.GetEPI.Models.EpiModel;
import com.senai.GetEPI.Models.FuncaoModel;
import com.senai.GetEPI.Repositories.EpiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EpiService {

    @Autowired
    EpiRepository epiRepository;

    public String cadastrarEpi(EpiDto epi) {


        epiRepository.save(new EpiModel(epi));
        return "";

    }

    public List<EpiDto> retornaListaEpiDTO() {
        return converterListaEpiDto(epiRepository.findAll());
    }

    public List<EpiDto> converterListaEpiDto(List<EpiModel> listaEpiModel) {
        return listaEpiModel.stream().map(EpiDto::new).collect(Collectors.toList());
    }


    public List<EpiModel> obterListaEpi(){
        return epiRepository.findAll();
    }

}