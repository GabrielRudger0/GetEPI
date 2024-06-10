package com.senai.GetEPI.Services;

import com.senai.GetEPI.DTOs.EpiDto;
import com.senai.GetEPI.Dominios.OrigemMovimentacao;
import com.senai.GetEPI.Dominios.TipoMovimentacao;
import com.senai.GetEPI.Models.*;
import com.senai.GetEPI.Repositories.EpiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EpiService {

    @Autowired
    EpiRepository epiRepository;
    @Autowired
    MovimentacaoService movimentacaoService;

    public List<EpiModel> retornaEPIModel() {
        return epiRepository.findAll();
    }

    public String cadastrarEpi(EpiDto epi) {

        EpiModel epiModel = new EpiModel();
        epiModel.setNomeEpi(epi.getNomeEpi().trim().toUpperCase());
        epiModel.setTipoEquipamento(epi.getTipoEquipamento());
        epiModel.setQuatidadeEpi(epi.getQuatidadeEpi());

        epiRepository.save(epiModel);

        movimentacaoService.gerarMovimentacaoInterna(epi, epi.getQuatidadeEpi(),
                TipoMovimentacao.ENTRADA, OrigemMovimentacao.REGISTRO_EQUIPAMENTO);

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

    public String excluirEpi(Long id){
        try {
            Optional<EpiModel> optionalEpi = epiRepository.findById(id);

            epiRepository.delete(optionalEpi.get());
            return "";

        } catch (Exception e) {
            return e.toString();
        }

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

        TipoMovimentacao tipoMovimentacao;
        Long qtdMovimentacao = 0l;
        if(epi.getQuatidadeEpi() >= epiBD.get().getQuatidadeEpi()) {
            tipoMovimentacao = TipoMovimentacao.ENTRADA;
            qtdMovimentacao = epi.getQuatidadeEpi();
        } else {
            tipoMovimentacao = TipoMovimentacao.SAIDA;
            qtdMovimentacao  = Math.negateExact(epi.getQuatidadeEpi());
        }

        movimentacaoService.gerarMovimentacaoInterna(epi, qtdMovimentacao, tipoMovimentacao, OrigemMovimentacao.ALTERACAO_ESTOQUE);

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

}
