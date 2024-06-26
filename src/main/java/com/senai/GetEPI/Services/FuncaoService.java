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

        if (!mensagemErroFuncao(funcao).isEmpty()) {
            return mensagemErroFuncao(funcao);
        }
        funcaoRepository.save(new FuncaoModel(funcao));
        return "";

    }

    public String atualizarFuncao(FuncaoDto funcao) {
        Optional<FuncaoModel> funcaoBD = funcaoRepository.findById(funcao.getId());

        /*
        if (!funcaoBD.get().getFuncao().equals(funcao.getFuncao())) {
            if (funcaoBD.isPresent()) {
                return "Já existe cadastro com estas credenciais!";
            }
        }
*/
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

    public FuncaoDto buscaFuncaoDTO(Long id){
        FuncaoModel funcao = funcaoRepository.findById(id).get();

        return new FuncaoDto(funcao);
    }

    public String excluirFuncao(Long id){
        try {
            Optional<FuncaoModel> optionalFuncao = funcaoRepository.findById(id);

            funcaoRepository.delete(optionalFuncao.get());
            return "";
        } catch (Exception e) {
            return e.toString();
        }

    }

    private String mensagemErroFuncao(FuncaoDto funcao) {
        Optional<FuncaoModel> funcaoExistente = funcaoRepository.findByFuncao(funcao.getFuncao());
        if (funcaoExistente.isPresent()) {
            return "Já existe cadastro com estas credenciais!";
        }
        return "";
    }

    public List<FuncaoDto> buscarFuncaoPorDescricao(FuncaoDto funcao) {
        List<FuncaoModel> funcoesEncontradas = funcaoRepository.findByFuncaoContaining(funcao.getFuncao());
        return converterListaFuncaoDTO(funcoesEncontradas);
    }

    public FuncaoModel retornaFuncaoModel(Long id) {
        return funcaoRepository.findById(id).get();
    }


}
