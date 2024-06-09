package com.senai.GetEPI.Controllers.Movimentacao;

import com.senai.GetEPI.DTOs.ViewEmprestimoDTO;
import com.senai.GetEPI.DTOs.ViewMovimentacaoDto;
import com.senai.GetEPI.OutrosObjetos.ApocalipseGetEPI;
import com.senai.GetEPI.OutrosObjetos.ErroGetEPI;
import com.senai.GetEPI.Services.MovimentacaoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RequestMapping("/listamovimentacoes")
@Controller
public class ListaMovimentacaoController {

    @Autowired
    MovimentacaoService movimentacaoService;

    @Autowired
    ApocalipseGetEPI apocalipseGetEPI;

    @GetMapping()
    public String exibirMovimentacoes(Model model, HttpServletRequest request){

        try {
            ErroGetEPI erro = apocalipseGetEPI.retornarErro(request);
            if (erro.getExibeErro()) {
                model.addAttribute("erro", true);
                model.addAttribute("tituloMensagemErro", erro.getMensagemErro());
                model.addAttribute("stacktraceMensagem", erro.getStackTrace());
            }

            List<ViewMovimentacaoDto> movimentacoes = movimentacaoService.retornaListaEmprestimos();
            boolean nenhumRegistro = false;
            if(movimentacoes.isEmpty()) {
                nenhumRegistro = true;
            }

            model.addAttribute("movimentacoes", movimentacoes);
            model.addAttribute("nenhumRegistro", nenhumRegistro);

        } catch (Exception e) {
            model.addAttribute("erro", true);
            model.addAttribute("tituloMensagemErro", apocalipseGetEPI.refatoraMensagem(e.getClass().getName(), e.toString()));
            model.addAttribute("stacktraceMensagem", e.toString());

            model.addAttribute("movimentacoes", new ArrayList<ViewMovimentacaoDto>());
            model.addAttribute("nenhumRegistro", true);

        }

        return "listamovimentacoes";
    }
}
