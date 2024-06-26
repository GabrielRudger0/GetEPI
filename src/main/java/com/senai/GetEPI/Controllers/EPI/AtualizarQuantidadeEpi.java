package com.senai.GetEPI.Controllers.EPI;

import com.senai.GetEPI.DTOs.EpiDto;
import com.senai.GetEPI.DTOs.GerarMovimentacaoEntradaDTO;
import com.senai.GetEPI.Dominios.OrigemMovimentacao;
import com.senai.GetEPI.Dominios.TipoMovimentacao;
import com.senai.GetEPI.Models.EmprestimoModel;
import com.senai.GetEPI.Models.EpiModel;
import com.senai.GetEPI.Services.AlocacaoService;
import com.senai.GetEPI.Services.EpiService;
import com.senai.GetEPI.Services.MovimentacaoService;
import com.senai.GetEPI.Services.TipoEquipamentoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
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

    @Autowired
    AlocacaoService alocacaoService;

    @GetMapping()
    public String exibeAtualizaEpi(Model model, HttpServletRequest request, HttpServletResponse response) {

        try {

            if (!alocacaoService.validaSessao(request).isEmpty()) {
                return alocacaoService.validaSessao(request);
            }
            CacheControl nocache = CacheControl.noStore().mustRevalidate();
            response.setHeader("Cache-Control", nocache.getHeaderValue());

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
            EmprestimoModel emprestimoInterno = alocacaoService.gerarEmprestimoInterno(request, movimentacao.getEpi());
            movimentacaoService.gerarMovimentacaoInterna(emprestimoInterno, movimentacao.getQuantidade(),TipoMovimentacao.ENTRADA, OrigemMovimentacao.ENTRADA_ESTOQUE);
        } catch (Exception e) {
            request.getSession().setAttribute("retornaErro", e);
            request.getSession().setAttribute("stacktrace", e);
            return "redirect:/listamovimentacoes";
        }

        return "redirect:/listamovimentacoes";

    }

}
