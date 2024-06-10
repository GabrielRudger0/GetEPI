package com.senai.GetEPI.Controllers.Movimentacao;

import com.senai.GetEPI.DTOs.UsuarioDTO;
import com.senai.GetEPI.DTOs.ViewMovimentacaoDto;
import com.senai.GetEPI.Services.MovimentacaoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/visualizarmovimentacao")
public class VisualizarMovimentacaoController {

    @Autowired
    MovimentacaoService movimentacaoService;

    @GetMapping("/{id}")
    public String exibeVisualizarMovimentacao(Model model, @PathVariable Long id, HttpServletRequest request) {

        try {
            ViewMovimentacaoDto movimentacao = movimentacaoService.buscaMovimentacaoPorId(id);
            model.addAttribute("movimentacaoDTO", movimentacao);

            return "visualizarmovimentacao";
        } catch (Exception e) {
            HttpSession sessao = request.getSession();
            sessao.setAttribute("retornaErro", e);
            sessao.setAttribute("stacktrace", e);
            return "redirect:/listamovimentacoes";
        }


    }
}
