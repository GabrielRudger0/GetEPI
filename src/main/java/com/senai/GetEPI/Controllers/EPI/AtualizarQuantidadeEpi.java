package com.senai.GetEPI.Controllers.EPI;

import com.senai.GetEPI.DTOs.GerarMovimentacaoEntradaDTO;
import com.senai.GetEPI.Services.EpiService;
import com.senai.GetEPI.Services.MovimentacaoService;
import com.senai.GetEPI.Services.TipoEquipamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/atualizarquantidadeepi")
public class AtualizarQuantidadeEpi {


    @Autowired
    EpiService epiService;

    @Autowired
    MovimentacaoService movimentacaoService;

    @GetMapping()
    public String exibeAtualizaEpi(Model model) {

        model.addAttribute("epis", epiService.retornaListaEpiDTO());
        model.addAttribute("gerarMovimentacaoDTO", new GerarMovimentacaoEntradaDTO());

        return "atualizarquantidadeepi";
    }

    @PostMapping()
    public String botaoSalvar(@ModelAttribute("gerarMovimentacaoDTO") GerarMovimentacaoEntradaDTO movimentacao, Model model) {

        System.out.println("Qtd: " + movimentacao.getQuantidade());
        System.out.println("EPI: " + movimentacao.getEpi().getNomeEpi());

        movimentacaoService.gerarMovimentacaoEntrada(movimentacao);

//        if (!mensagemErro.isEmpty()) {
//            model.addAttribute("erro", true);
//            model.addAttribute("mensagemErro", mensagemErro);
//            model.addAttribute("epiDTO",epi);
//            return "atualizarquantidadeepi";
//        }
        return "redirect:/listamovimentacoes";

    }

}
