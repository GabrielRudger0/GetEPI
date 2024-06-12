package com.senai.GetEPI.Controllers.EPI;

import com.senai.GetEPI.DTOs.ColaboradorDto;
import com.senai.GetEPI.DTOs.EpiDto;
import com.senai.GetEPI.Services.EpiService;
import com.senai.GetEPI.Services.TipoEquipamentoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/atualizarepi")
public class AtualizarEpiController {

    @Autowired
    EpiService epiService;

    @Autowired
    TipoEquipamentoService tipoEquipamentoService;

    @GetMapping("/{id}")
    public String exibeAtualizaEpi(Model model, @PathVariable Long id, HttpServletRequest request) {
        try {
            EpiDto epi = epiService.buscaEpiDTO(id);
            model.addAttribute("tiposEquipamento", tipoEquipamentoService.obterListaTipoEquipamento());
            model.addAttribute("epiDTO", epi);

            return "atualizarepi";

        } catch (Exception e) {
            request.getSession().setAttribute("retornaErro", e);
            request.getSession().setAttribute("stacktrace", e);
            return "redirect:/listaEPI";
        }


    }

    @PostMapping()
    public String botaoSalvar(@ModelAttribute("atualizarepi") EpiDto epi, Model model, HttpServletRequest request) {

        try {
            String mensagemErro = epiService.atualizarEpi(epi, request);

            if (!mensagemErro.isEmpty()) {
                model.addAttribute("tiposEquipamento", tipoEquipamentoService.obterListaTipoEquipamento());
                model.addAttribute("erro", true);
                model.addAttribute("mensagemErro", mensagemErro);
                model.addAttribute("epiDTO",epi);
                return "atualizarepi";
            }

        } catch (Exception e) {
            request.getSession().setAttribute("retornaErro", e);
            request.getSession().setAttribute("stacktrace", e);
        }

        return "redirect:/listaEPI";

    }
}
