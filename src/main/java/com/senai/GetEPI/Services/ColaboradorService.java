package com.senai.GetEPI.Services;

import com.senai.GetEPI.DTOs.ColaboradorDto;
import com.senai.GetEPI.DTOs.UsuarioDTO;
import com.senai.GetEPI.Models.ColaboradorModel;
import com.senai.GetEPI.Models.UsuarioModel;
import com.senai.GetEPI.Repositories.ColaboradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ColaboradorService {

    @Autowired
    ColaboradorRepository colaboradorRepository;

    public ColaboradorDto cadastrarColaborador (ColaboradorDto colaboradorDto){
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
       // Optional<ColaboradorModel> colaboradorModel = colaboradorRepository.findById(colaboradorDto.getId());
        Date dataNascimento = new Date();
        try {
             dataNascimento = formato.parse(colaboradorDto.getDataNascimento());
            System.out.println("Data de Nascimento: " + dataNascimento);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ColaboradorModel colaborador = new ColaboradorModel();
        colaborador.setNome(colaboradorDto.getNome());
        colaborador.setEmail(colaboradorDto.getEmail());
        colaborador.setFuncao(colaboradorDto.getFuncao());
        colaborador.setDataNascimento(dataNascimento);

        colaboradorRepository.save(colaborador);

        return colaboradorDto;

    }

    public List<ColaboradorDto> retornaListaColaboradorDTO() {
        return converterListaColaboradorDTO(colaboradorRepository.findAll());
    }

    private List<ColaboradorDto> converterListaColaboradorDTO(List<ColaboradorModel> listaColaboradorModel) {
        return listaColaboradorModel.stream().map(ColaboradorDto::new).collect(Collectors.toList());
    }
}
