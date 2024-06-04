package com.senai.GetEPI.Controllers.Movimentacao;

import com.senai.GetEPI.DTOs.ViewEmprestimoDTO;
import com.senai.GetEPI.DTOs.ViewMovimentacaoDto;
import com.senai.GetEPI.Services.MovimentacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/listamovimentacoes")
@Controller
public class ListaMovimentacaoController {

    @Autowired
    MovimentacaoService movimentacaoService;

    @GetMapping()
    public String exibirMovimentacoes(Model model){

    List<ViewMovimentacaoDto> movimentacoes = movimentacaoService.retornaListaEmprestimos();
    boolean nenhumRegistro = false;
    if(movimentacoes.isEmpty()) {
        nenhumRegistro = true;
    }

    model.addAttribute("movimentacoes", movimentacoes);
    model.addAttribute("nenhumRegistro", nenhumRegistro);

        return "listamovimentacoes";
    }
}
