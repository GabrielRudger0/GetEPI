package com.senai.GetEPI.Services;

import com.senai.GetEPI.DTOs.ColaboradorDto;
import com.senai.GetEPI.DTOs.EpiDto;
import com.senai.GetEPI.DTOs.FuncaoDto;
import com.senai.GetEPI.DTOs.ViewEmprestimoDTO;
import com.senai.GetEPI.Models.ColaboradorModel;
import com.senai.GetEPI.Models.EpiModel;
import com.senai.GetEPI.Models.FuncaoModel;
import com.senai.GetEPI.Models.TipoEquipamentoModel;
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

    public List<EpiModel> retornaEPIModel() {
        return epiRepository.findAll();
    }

    public String cadastrarEpi(EpiDto epi) {
        epi.setNomeEpi(epi.getNomeEpi().trim().toUpperCase());
        epiRepository.save(new EpiModel(epi, epi.getTipoEquipamento()));
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

    public boolean excluirEpi(Long id){
        Optional<EpiModel> optionalEpi = epiRepository.findById(id);
        if (!optionalEpi.isPresent()){
            return false;
        }
        epiRepository.delete(optionalEpi.get());
        return true;

    }

    public EpiDto buscaEpiDTOs(Long id){
        EpiModel epi = epiRepository.findById(id).get();

        return new EpiDto(epi,epi.getTipoEquipamento());
    }

    public String atualizarEpi(EpiDto epi) {
        Optional<EpiModel> epiBD = epiRepository.findById(epi.getId());

        EpiModel atualizar = new EpiModel();

        if (!epiBD.get().getNomeEpi().equals(epi.getNomeEpi())) {
            if (epiRepository.existsByNomeEpi(epi.getNomeEpi())) {
                return "Já existe cadastro com estas credenciais!";
            }
        }
        atualizar.setId(epi.getId());
        atualizar.setNomeEpi(epi.getNomeEpi().trim().toUpperCase());
        atualizar.setTipoEquipamento(epi.getTipoEquipamento());
        atualizar.setQuatidadeEpi(epi.getQuatidadeEpi());


        epiRepository.save(atualizar);

        return "";
    }

    public String atualizarQuantidadeEpi(EpiDto epi) {
        Optional<EpiModel> epiBD = epiRepository.findById(epi.getId());
        EpiModel atualizar = new EpiModel();

        atualizar.setId(epi.getId());
        atualizar.setNomeEpi(epi.getNomeEpi());
        atualizar.setTipoEquipamento(epi.getTipoEquipamento());
        atualizar.setQuatidadeEpi(epi.getQuatidadeEpi());


        epiRepository.save(atualizar);

        return "";
    }


    public EpiDto buscaEpiDTO(Long id){
        EpiModel epi = epiRepository.findById(id).get();

        return new EpiDto(epi);
    }

    public void alterarEstoque(Long movimentacaoQuantidade, EpiDto epi) {

        Long estoqueFinal = epi.getQuatidadeEpi() + movimentacaoQuantidade;
        System.out.println("Estoque final: " + estoqueFinal);
        epi.setQuatidadeEpi(estoqueFinal);

        epiRepository.save(new EpiModel(epi, epi.getTipoEquipamento()));
    }




}
