package com.senai.GetEPI.Controllers.EPI;

import com.senai.GetEPI.DTOs.EpiDto;
import com.senai.GetEPI.Services.EpiService;
import com.senai.GetEPI.Services.TipoEquipamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/atualizarquantidadeepi")
public class AtualizarQuantidadeEpi {


    @Autowired
    EpiService epiService;

    @Autowired
    TipoEquipamentoService tipoEquipamentoService;

    @GetMapping("/{id}")
    public String exibeAtualizaEpi(Model model, @PathVariable Long id) {
        EpiDto epi = epiService.buscaEpiDTO(id);
        model.addAttribute("tiposEquipamento", tipoEquipamentoService.obterListaTipoEquipamento());
        model.addAttribute("epiDTO", epi);

        return "atualizarquantidadeepi";
    }

    @PostMapping()
    public String botaoSalvar(@ModelAttribute("atualizarquantidadeepi") EpiDto epi, Model model) {
        String mensagemErro = epiService.atualizarQuantidadeEpi(epi);

        if (!mensagemErro.isEmpty()) {
            model.addAttribute("erro", true);
            model.addAttribute("mensagemErro", mensagemErro);
            model.addAttribute("epiDTO",epi);
            return "atualizarquantidadeepi";
        }
        return "redirect:/listaEPI";

    }

}
