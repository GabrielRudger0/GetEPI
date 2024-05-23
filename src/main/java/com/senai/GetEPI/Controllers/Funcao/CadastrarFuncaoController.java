package com.senai.GetEPI.Controllers.Funcao;

import com.senai.GetEPI.DTOs.ColaboradorDto;
import com.senai.GetEPI.DTOs.FuncaoDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cadastrarfuncao")
public class CadastrarFuncaoController {


    @GetMapping()
    public String cadastrarColaborador(Model model){

        FuncaoDto funcaoDto = new FuncaoDto();

        model.addAttribute("funcaoDto",funcaoDto);

        return "cadastrarfuncao";
    }


}
