package com.senai.GetEPI.Controllers.EPI;

import com.senai.GetEPI.DTOs.EpiDto;
import com.senai.GetEPI.DTOs.GerarMovimentacaoEntradaDTO;
import com.senai.GetEPI.Dominios.TipoMovimentacao;
import com.senai.GetEPI.Models.EpiModel;
import com.senai.GetEPI.Services.EpiService;
import com.senai.GetEPI.Services.MovimentacaoService;
import com.senai.GetEPI.Services.TipoEquipamentoService;
import jakarta.servlet.http.HttpServletRequest;
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
    public String exibeAtualizaEpi(Model model, HttpServletRequest request) {

        try {
            model.addAttribute("epis", epiService.retornaListaEpiDTO());
            model.addAttribute("gerarMovimentacaoDTO", new GerarMovimentacaoEntradaDTO());

            return "atualizarquantidadeepi";

        } catch (Exception e) {
            request.getSession().setAttribute("retornaErro", e);
            request.getSession().setAttribute("stacktrace", e);
            return "redirect:/listamovimentacoes";
        }

    }

    @PostMapping()
    public String botaoSalvar(@ModelAttribute("gerarMovimentacaoDTO") GerarMovimentacaoEntradaDTO movimentacao, Model model, HttpServletRequest request) {

        try {
            movimentacaoService.gerarMovimentacaoInterna(new EpiDto(movimentacao.getEpi()), movimentacao.getQuantidade(), TipoMovimentacao.ENTRADA);
        } catch (Exception e) {
            request.getSession().setAttribute("retornaErro", e);
            request.getSession().setAttribute("stacktrace", e);
            return "redirect:/listamovimentacoes";
        }

        return "redirect:/listamovimentacoes";

    }

}
