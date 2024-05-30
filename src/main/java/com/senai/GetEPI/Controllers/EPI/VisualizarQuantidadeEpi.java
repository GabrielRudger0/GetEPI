package com.senai.GetEPI.Controllers.EPI;

import com.senai.GetEPI.DTOs.EpiDto;
import com.senai.GetEPI.Services.EpiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/listaquantidadeepi")
public class VisualizarQuantidadeEpi {

    @Autowired
    EpiService epiService;

    @GetMapping("/{id}")
    public String exibeVisualizarEmprestimo(Model model, @PathVariable Long id) {

        EpiDto epi = epiService.buscaEpiDTO(id);

        model.addAttribute("epiDTO", epi);
        return "listaquantidadeepi";
    }

}
