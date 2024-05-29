package com.senai.GetEPI.Controllers.Movimentacao;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/listamovimentacoes")
@Controller
public class ListaMovimentacaoController {


    @GetMapping()
    public String exibirMovimentacoes(Model model){





        return "listamovimentacoes";
    }
}
