package com.senai.GetEPI.Services;

import com.senai.GetEPI.DTOs.ColaboradorDto;
import com.senai.GetEPI.DTOs.EpiDto;
import com.senai.GetEPI.DTOs.FuncaoDto;
import com.senai.GetEPI.DTOs.ViewEmprestimoDTO;
import com.senai.GetEPI.Dominios.TipoMovimentacao;
import com.senai.GetEPI.Models.*;
import com.senai.GetEPI.Repositories.EpiRepository;
import com.senai.GetEPI.Repositories.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EpiService {

    @Autowired
    EpiRepository epiRepository;
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    public List<EpiModel> retornaEPIModel() {
        return epiRepository.findAll();
    }

    public String cadastrarEpi(EpiDto epi) {

        EpiModel epiModel = new EpiModel();
        epiModel.setNomeEpi(epi.getNomeEpi().trim().toUpperCase());
        epiModel.setTipoEquipamento(epi.getTipoEquipamento());
        epiModel.setQuatidadeEpi(epi.getQuatidadeEpi());

        epiRepository.save(epiModel);

        inserirMovimentacao(epi,epiModel);
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
        inserirMovimentacao(epi,atualizar);

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

    public List<EpiDto> buscarEPIPorNome(EpiDto epiBuscado) {
        List<EpiModel> colaboradoresEncontrados = epiRepository.findByNomeEpiContaining(epiBuscado.getNomeEpi());
        return converterListaEpiDto(colaboradoresEncontrados);
    }




    private String inserirMovimentacao(EpiDto epiDto,EpiModel epiModel){


        MovimentacaoModel registro = new MovimentacaoModel();

        registro.setDataMovimentacao(new Date());
        registro.setQuantidade(epiDto.getQuatidadeEpi());
        registro.setEmprestimoModel(null);
        registro.setTipoMovimentacao(TipoMovimentacao.ENTRADA);
        movimentacaoRepository.save(registro);

        return "";
    }

    

}
