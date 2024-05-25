package com.senai.GetEPI.Controllers.EPI;

import com.senai.GetEPI.DTOs.EpiDto;
import com.senai.GetEPI.Services.EpiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cadastrarepi")
public class CadastrarEpiController {

    @Autowired
    EpiService epiService;

    @GetMapping()
    public String cadastrarEpi(Model model){

        EpiDto epiDto = new EpiDto();

        model.addAttribute("epiDto", epiDto);

        return "cadastrarepi";

    }

    @PostMapping
    public String enviarDadosCadastro(@ModelAttribute("epiDto") EpiDto epiDto, Model model){

        String mensagemErro = epiService.cadastrarEpi(epiDto);
        if (!mensagemErro.isEmpty()) {
            model.addAttribute("erro", true);
            model.addAttribute("mensagemErro", mensagemErro);

            return "cadastrarepi";
        }

        return "redirect:/listaEPI";
    }
}
