package com.senai.GetEPI.Controllers.Colaborador;

import com.senai.GetEPI.DTOs.ColaboradorDto;
import com.senai.GetEPI.Services.ColaboradorService;
import com.senai.GetEPI.Services.FuncaoService;
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
    @Autowired
    private FuncaoService funcaoService;

    @GetMapping()
    public String cadastrarColaborador(Model model){

        ColaboradorDto colaboradorDto = new ColaboradorDto();

        model.addAttribute("colaboradorDto",colaboradorDto);
        model.addAttribute("funcoes", funcaoService.obterListaFuncao());
        return "cadastrarcolaborador";
    }

    @PostMapping()
    public String enviarDadosColaborador(@ModelAttribute("colaboradorDto")ColaboradorDto colaboradorDto,Model model){

        String mensagemErro = colaboradorService.cadastrarColaborador(colaboradorDto);
        if (!mensagemErro.isEmpty()) {
            model.addAttribute("erro", true);
            model.addAttribute("mensagemErro", mensagemErro);
            model.addAttribute("funcoes", funcaoService.obterListaFuncao());

            return "cadastrarcolaborador";
        }

        return "redirect:/listacolaboradores";
    }
}
