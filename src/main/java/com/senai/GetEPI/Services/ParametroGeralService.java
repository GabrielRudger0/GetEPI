package com.senai.GetEPI.Services;

import com.senai.GetEPI.DTOs.FuncaoDto;
import com.senai.GetEPI.DTOs.ParametroGeralDTO;
import com.senai.GetEPI.Dominios.TipoParametroGeral;
import com.senai.GetEPI.Models.FuncaoModel;
import com.senai.GetEPI.Models.ParametroGeralModel;
import com.senai.GetEPI.Repositories.ParametroGeralRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParametroGeralService {

    @Autowired
    ParametroGeralRepository parametroGeralRepository;

    @Autowired
    FuncaoService funcaoService;

    public ParametroGeralDTO retornaParametrosGerais() {

        ParametroGeralDTO parametros = new ParametroGeralDTO();
        Optional<ParametroGeralModel> parametroGeralModel = parametroGeralRepository.findByTipoParametroGeral(TipoParametroGeral.FuncaoPadraoUsuario);
        parametros.setTipoFuncaoUsuario(TipoParametroGeral.FuncaoPadraoUsuario);

        if (parametroGeralModel.isPresent()) {
            FuncaoModel funcao = funcaoService.retornaFuncaoModel(parametroGeralModel.get().getValor());
            parametros.setValorFuncaoUsuario(funcao);
            parametros.setTipoFuncaoUsuario(TipoParametroGeral.FuncaoPadraoUsuario);

        }

        return parametros;
    }

    public List<FuncaoDto> listarFuncoes() {
        return funcaoService.retornaListaFuncaoDTO();
    }

    public boolean salvarParametros(ParametroGeralDTO parametroGeral) {
        Optional<ParametroGeralModel> funcaoUsuario = parametroGeralRepository.findByTipoParametroGeral(TipoParametroGeral.FuncaoPadraoUsuario);
        if (!funcaoUsuario.isPresent()) {
            ParametroGeralModel parametroGeralFuncaousuario = new ParametroGeralModel(0l, TipoParametroGeral.FuncaoPadraoUsuario, parametroGeral.getValorFuncaoUsuario().getId());
            parametroGeralRepository.save(parametroGeralFuncaousuario);
            return true;
        }

        parametroGeralRepository.save(new ParametroGeralModel(funcaoUsuario.get().getId(), TipoParametroGeral.FuncaoPadraoUsuario, parametroGeral.getValorFuncaoUsuario().getId()));
        return true;
    }

    public FuncaoModel obterParametroFuncaoUsuario() {
        Optional<ParametroGeralModel> parametro = parametroGeralRepository.findByTipoParametroGeral(TipoParametroGeral.FuncaoPadraoUsuario);
        return funcaoService.retornaFuncaoModel(parametro.get().getValor());
    }

    public FuncaoModel criarFuncaoPadrao() {
        FuncaoDto funcaoPadrao = new FuncaoDto();
        funcaoPadrao.setFuncao("OPERADOR DO SISTEMA");
        funcaoService.cadastrarFuncao(funcaoPadrao);

        return funcaoService.funcaoRepository.findByFuncao(funcaoPadrao.getFuncao()).get();
    }





}
