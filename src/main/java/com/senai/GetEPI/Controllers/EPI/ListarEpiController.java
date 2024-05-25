package com.senai.GetEPI.Controllers.EPI;

import com.senai.GetEPI.DTOs.ColaboradorDto;
import com.senai.GetEPI.DTOs.EpiDto;
import com.senai.GetEPI.Services.EpiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/listaEPI")
public class ListarEpiController {

    @Autowired
    EpiService epiService;

    @GetMapping()
    public String listarEpi(Model model,EpiDto epiDto) {

        List<EpiDto> listaEpi = epiService.retornaListaEpiDTO();

        model.addAttribute("epiDto",epiService.obterListaEpi());
        model.addAttribute("buscaEPIDto", new EpiDto());

        return "listaEPI";
    }
}
