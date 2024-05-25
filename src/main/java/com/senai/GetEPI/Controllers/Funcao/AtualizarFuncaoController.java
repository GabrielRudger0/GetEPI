package com.senai.GetEPI.Controllers.Funcao;

import com.senai.GetEPI.DTOs.ColaboradorDto;
import com.senai.GetEPI.DTOs.FuncaoDto;
import com.senai.GetEPI.Services.FuncaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/atualizarfuncao")
public class AtualizarFuncaoController {

    @Autowired
    FuncaoService funcaoService;

    @GetMapping("/{id}")
    public String exibeAtualizaFuncao(Model model, @PathVariable Long id) {
        FuncaoDto funcao = funcaoService.buscaFuncaoDTO(id);
        model.addAttribute("funcaoDTO", funcao);
        return "atualizarfuncao";
    }

    @PostMapping()
    public String botaoSalvar(@ModelAttribute("funcaoDTO") FuncaoDto funcao, Model model) {
        String mensagemErro = funcaoService.atualizarFuncao(funcao);

        if (!mensagemErro.isEmpty()) {
            model.addAttribute("erro", true);
            model.addAttribute("mensagemErro", mensagemErro);
            return "atualizarfuncao";
        }
        return "redirect:/listafuncao";

    }

}
