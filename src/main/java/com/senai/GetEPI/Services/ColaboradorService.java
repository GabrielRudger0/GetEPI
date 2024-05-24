package com.senai.GetEPI.Services;

import com.senai.GetEPI.DTOs.ColaboradorDto;
import com.senai.GetEPI.DTOs.UsuarioDTO;
import com.senai.GetEPI.Models.ColaboradorModel;
import com.senai.GetEPI.Models.FuncaoModel;
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

    public String cadastrarColaborador (ColaboradorDto colaboradorDto){
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
       boolean existeColaborador = colaboradorRepository.existsByEmail(colaboradorDto.getEmail());
        Date dataNascimento = new Date();
        try {
             dataNascimento = formato.parse(colaboradorDto.getDataNascimento());
            System.out.println("Data de Nascimento: " + dataNascimento);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(existeColaborador){
            return "Colaborador já registrado!";
        }
        ColaboradorModel colaborador = new ColaboradorModel();
        colaborador.setNome(colaboradorDto.getNome());
        colaborador.setEmail(colaboradorDto.getEmail());
        colaborador.setFuncao(colaboradorDto.getFuncao());
        colaborador.setDataNascimento(dataNascimento);

        colaboradorRepository.save(colaborador);

        return "";

    }

    public List<ColaboradorDto> retornaListaColaboradorDTO() {
        return converterListaColaboradorDTO(colaboradorRepository.findAll());
    }

    public List<ColaboradorDto> converterListaColaboradorDTO(List<ColaboradorModel> listaColaboradorModel) {
        return listaColaboradorModel.stream().map(ColaboradorDto::new).collect(Collectors.toList());
    }

    public ColaboradorDto buscaColaboradorDTO(Long id){
        ColaboradorModel colaborador = colaboradorRepository.findById(id).get();

        return new ColaboradorDto(colaborador,new FuncaoModel());
    }

    public boolean excluirColaborador(Long id){
        Optional<ColaboradorModel> optionalColaborador = colaboradorRepository.findById(id);
        if (!optionalColaborador.isPresent()){
            return false;
        }
        colaboradorRepository.delete(optionalColaborador.get());
        return true;

    }

    public String atualizaColaborador(ColaboradorDto colaborador) {
        Optional<ColaboradorModel> colaboradorBD = colaboradorRepository.findById(colaborador.getId());

        if (!colaboradorBD.get().getEmail().equals(colaborador.getEmail())) {
            if (colaboradorRepository.existsByEmail(colaborador.getEmail())) {
                return "Já existe cadastro com estas credenciais!";
            }
        }

        colaboradorRepository.save(new ColaboradorModel(colaborador,new FuncaoModel()));
        return "";
    }


}
