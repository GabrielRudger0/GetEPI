package com.senai.GetEPI.Controllers.Movimentacao;

import com.senai.GetEPI.DTOs.ViewEmprestimoDTO;
import com.senai.GetEPI.DTOs.ViewMovimentacaoDto;
import com.senai.GetEPI.OutrosObjetos.ApocalipseGetEPI;
import com.senai.GetEPI.OutrosObjetos.ErroGetEPI;
import com.senai.GetEPI.Services.MovimentacaoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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


            int paginaAtual = 1;
            model.addAttribute("paginaAtual", paginaAtual);
            Object pagina = request.getSession().getAttribute("paginaAtual");
            if (pagina != null) {
                paginaAtual = Integer.parseInt(pagina.toString());
                model.addAttribute("paginaAtual", paginaAtual);
                request.getSession().removeAttribute("paginaAtual");
            }

            int max = movimentacaoService.calcularNumeroMaximoPaginas(movimentacoes, 7);
            request.getSession().setAttribute("maxPaginas", max);

            List<ViewMovimentacaoDto> movimentacoesPagina = movimentacaoService.paginarListaEmprestimo(movimentacoes, paginaAtual);

            model.addAttribute("movimentacoes", movimentacoesPagina);
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
    @PostMapping("/passarpagina/{movimento}/{paginaAtual}")
    public ResponseEntity<String> paginaposterior(@PathVariable Integer movimento, @PathVariable Integer paginaAtual, HttpServletRequest request) {
        // Movimento = 0 voltar
        // Movimento = 1 avançar
        System.out.println("movimento: " + movimento);
        System.out.println("paginaAtual: " + paginaAtual);

        Object objectMaxPaginas = request.getSession().getAttribute("maxPaginas");
        int maxPaginas = 1;
        if (objectMaxPaginas != null) {
            maxPaginas = Integer.parseInt(objectMaxPaginas.toString());
        }

        System.out.println("maxPaginas: " + maxPaginas);

        if (movimento == 0) {
            if (paginaAtual > 1) {
                paginaAtual--;
            }
        }

        if (movimento == 1) {
            if (paginaAtual < maxPaginas) {
                paginaAtual++;
            }
        }

        request.getSession().setAttribute("paginaAtual", paginaAtual);
        return ResponseEntity.ok().body(paginaAtual.toString());
    }
}
