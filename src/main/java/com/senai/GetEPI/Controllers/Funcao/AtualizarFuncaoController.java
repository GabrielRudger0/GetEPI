package com.senai.GetEPI.Controllers.Funcao;

import com.senai.GetEPI.DTOs.ColaboradorDto;
import com.senai.GetEPI.DTOs.FuncaoDto;
import com.senai.GetEPI.Services.FuncaoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
    public String exibeAtualizaFuncao(Model model, @PathVariable Long id, HttpServletRequest request) {

        try {
            FuncaoDto funcao = funcaoService.buscaFuncaoDTO(id);
            model.addAttribute("funcaoDTO", funcao);
            return "atualizarfuncao";
        } catch (Exception e) {
            request.getSession().setAttribute("retornaErro", e);
            request.getSession().setAttribute("stacktrace", e);
            return "redirect:/listafuncao";
        }

    }

    @PostMapping()
    public String botaoSalvar(@ModelAttribute("funcaoDTO") FuncaoDto funcao, Model model, HttpServletRequest request) {
        try {
            String mensagemErro = funcaoService.atualizarFuncao(funcao);

            if (!mensagemErro.isEmpty()) {
                model.addAttribute("erro", true);
                model.addAttribute("mensagemErro", mensagemErro);
                return "atualizarfuncao";
            }
            return "redirect:/listafuncao";
        } catch (Exception e) {
            request.getSession().setAttribute("retornaErro", e);
            request.getSession().setAttribute("stacktrace", e);
            return "redirect:/listafuncao";
        }


    }

}
