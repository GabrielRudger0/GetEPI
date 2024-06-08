package com.senai.GetEPI.Controllers.ParametroGeral;

import com.senai.GetEPI.DTOs.FuncaoDto;
import com.senai.GetEPI.DTOs.ParametroGeralDTO;
import com.senai.GetEPI.Services.ParametroGeralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/parametrogeral")
public class ParametroGeralController {

    @Autowired
    ParametroGeralService parametroGeralService;

    @GetMapping()
    public String exibeParametroGeral(Model model) {
        ParametroGeralDTO parametros = parametroGeralService.retornaParametrosGerais();
        List<FuncaoDto> funcoes = parametroGeralService.listarFuncoes();
        model.addAttribute("funcoes", funcoes);
        model.addAttribute("parametroGeralDTO", parametros);
        return "parametrogeral";
    }

    @PostMapping()
    public String salvarParametros(@ModelAttribute("parametroGeralDTO") ParametroGeralDTO parametros, Model model) {
        parametroGeralService.salvarParametros(parametros);

        model.addAttribute("salvo", true);
        ParametroGeralDTO parametrosTela = parametroGeralService.retornaParametrosGerais();
        List<FuncaoDto> funcoes = parametroGeralService.listarFuncoes();
        model.addAttribute("funcoes", funcoes);
        model.addAttribute("parametroGeralDTO", parametrosTela);

        return "parametrogeral";
    }
}
