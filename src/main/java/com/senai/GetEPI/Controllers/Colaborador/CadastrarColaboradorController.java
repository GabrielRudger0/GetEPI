package com.senai.GetEPI.Controllers.Colaborador;

import com.senai.GetEPI.DTOs.ColaboradorDto;
import com.senai.GetEPI.Services.ColaboradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cadastrarcolaborador")
public class CadastrarColaboradorController {

    @Autowired
    ColaboradorService colaboradorService;

    @GetMapping()
    public String cadastrarColaborador(Model model){

        ColaboradorDto colaboradorDto = new ColaboradorDto();

        model.addAttribute("colaboradorDto",colaboradorDto);

        return "cadastrarcolaborador";
    }

    @PostMapping()
    public String enviarDadosColaborador(@ModelAttribute("colaboradorDto")ColaboradorDto colaboradorDto,Model model){

        ColaboradorDto colaborador = colaboradorService.cadastrarColaborador(colaboradorDto);

        model.addAttribute("colaboradordto",colaboradorDto);

        return "redirect:/listacolaboradores";
    }
}
