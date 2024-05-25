package com.senai.GetEPI.Controllers.Funcao;

import com.senai.GetEPI.DTOs.ColaboradorDto;
import com.senai.GetEPI.DTOs.FuncaoDto;
import com.senai.GetEPI.Services.FuncaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cadastrarfuncao")
public class CadastrarFuncaoController {

    @Autowired
    FuncaoService funcaoService;

    @GetMapping()
    public String cadastrarFucao(Model model){

        FuncaoDto funcaoDto = new FuncaoDto();

        model.addAttribute("funcaoDto",funcaoDto);

        return "cadastrarfuncao";
    }

    @PostMapping()
    public String enviarDadosFuncao(@ModelAttribute("funcaoDto")FuncaoDto funcaoDto, Model model){

        String mensagemErro = funcaoService.cadastrarFuncao(funcaoDto);
        if (!mensagemErro.isEmpty()) {
            model.addAttribute("erro", true);
            model.addAttribute("mensagemErro", mensagemErro);
            return "cadastrarfuncao";
        }

        return "redirect:/listafuncao";
    }


}
