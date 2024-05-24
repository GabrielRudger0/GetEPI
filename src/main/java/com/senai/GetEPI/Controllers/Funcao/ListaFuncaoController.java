package com.senai.GetEPI.Controllers.Funcao;

import com.senai.GetEPI.DTOs.ColaboradorDto;
import com.senai.GetEPI.DTOs.FuncaoDto;
import com.senai.GetEPI.Services.ColaboradorService;
import com.senai.GetEPI.Services.FuncaoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/listafuncao")
public class ListaFuncaoController {

    @Autowired
    FuncaoService funcaoService;

    @GetMapping()
    public String exibirListaColaboradores(Model model, HttpServletRequest request, FuncaoDto funcaoDto) {

        List<FuncaoDto> listaColaborador = funcaoService.retornaListaFuncaoDTO();

        model.addAttribute("funcao", funcaoService.retornaListaFuncaoDTO());
        model.addAttribute("buscaFuncaoDTO", new FuncaoDto());

        return "listafuncao";


    }
}
