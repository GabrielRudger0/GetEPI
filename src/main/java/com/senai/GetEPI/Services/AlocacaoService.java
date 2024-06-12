package com.senai.GetEPI.Services;

import com.senai.GetEPI.DTOs.ColaboradorDto;
import com.senai.GetEPI.DTOs.EmprestimoDTO;
import com.senai.GetEPI.Models.ColaboradorModel;
import com.senai.GetEPI.Models.EmprestimoModel;
import com.senai.GetEPI.Models.EpiModel;
import com.senai.GetEPI.Models.UsuarioModel;
import com.senai.GetEPI.Repositories.ColaboradorRepository;
import com.senai.GetEPI.Repositories.EmprestimoRepository;
import com.senai.GetEPI.Repositories.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class AlocacaoService {

    @Autowired
    ColaboradorRepository colaboradorRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    EmprestimoRepository emprestimoRepository;

    public ColaboradorDto obterAlocacao(HttpServletRequest request) {
        String alocacaoEmail = request.getSession().getAttribute("alocacaoEmail").toString();
        UsuarioModel usuario = obterUsuarioModelPorEmail(alocacaoEmail);
        return new ColaboradorDto(colaboradorRepository.findByUsuario(usuario).get());

    }

    public String validaSessao(HttpServletRequest request) {
        Object objectAlocacao = request.getSession().getAttribute("alocacaoEmail");
        if (objectAlocacao != null) {
            String alocacaoEmail = objectAlocacao.toString();
            if (alocacaoLogadaValida(alocacaoEmail)) {
                return "";
            }
        }
        return "redirect:/login";
    }

    private boolean alocacaoLogadaValida(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public EmprestimoModel gerarEmprestimoInterno(HttpServletRequest request, EpiModel epi) {

        ColaboradorModel alocacao = new ColaboradorModel(obterAlocacao(request), obterAlocacao(request).getFuncao());

        EmprestimoModel emprestimoInterno = new EmprestimoModel();
        emprestimoInterno.setEmissaoData(new Date());
        emprestimoInterno.setDevolucaoData(new Date());
        emprestimoInterno.setColaborador(alocacao);
        emprestimoInterno.setEpi(epi);
        emprestimoInterno.setRegistroInterno(true);

        emprestimoRepository.save(emprestimoInterno);

        return emprestimoInterno;
    }

    public UsuarioModel obterUsuarioModelPorEmail(String email) {
        return usuarioRepository.findByEmail(email).get();
    }


}
