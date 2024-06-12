package com.senai.GetEPI.Controllers.EPI;

import com.senai.GetEPI.DTOs.EpiDto;
import com.senai.GetEPI.Services.EpiService;
import com.senai.GetEPI.Services.TipoEquipamentoService;
import jakarta.servlet.http.HttpServletRequest;
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

    @Autowired
    private TipoEquipamentoService tipoEquipamentoService;

    @GetMapping()
    public String cadastrarEpi(Model model, HttpServletRequest request){

        try {
            EpiDto epiDto = new EpiDto();

            model.addAttribute("epiDto", epiDto);
            model.addAttribute("tiposEquipamento", tipoEquipamentoService.obterListaTipoEquipamento());

            return "cadastrarepi";

        } catch (Exception e) {
            request.getSession().setAttribute("retornaErro", e);
            request.getSession().setAttribute("stacktrace", e);
            return "redirect:/listaEPI";
        }

    }

    @PostMapping
    public String enviarDadosCadastro(@ModelAttribute("epiDto") EpiDto epiDto, Model model, HttpServletRequest request){

        try {
            String mensagemErro = epiService.cadastrarEpi(epiDto, request);
            if (!mensagemErro.isEmpty()) {
                model.addAttribute("erro", true);
                model.addAttribute("mensagemErro", mensagemErro);
                model.addAttribute("tiposEquipamento", tipoEquipamentoService.obterListaTipoEquipamento());
                return "cadastrarepi";
            }
            return "redirect:/listaEPI";

        } catch (Exception e) {
            request.getSession().setAttribute("retornaErro", e);
            request.getSession().setAttribute("stacktrace", e);
            return "redirect:/listaEPI";
        }


    }
}
