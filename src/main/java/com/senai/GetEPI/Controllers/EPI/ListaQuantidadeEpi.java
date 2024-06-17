package com.senai.GetEPI.Controllers.EPI;

import com.senai.GetEPI.DTOs.EpiDto;
import com.senai.GetEPI.Services.AlocacaoService;
import com.senai.GetEPI.Services.EpiService;
import com.senai.GetEPI.Services.TipoEquipamentoService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/visualizarquantidadeepi")
public class ListaQuantidadeEpi {

    @Autowired
    EpiService epiService;
    @Autowired
    private TipoEquipamentoService tipoEquipamentoService;

    @GetMapping()
    public String exibirListaQuantidadeEpi(Model model, EpiDto epiDto, HttpServletResponse response) {

        List<EpiDto> listaEpi = epiService.retornaListaEpiDTO();

        model.addAttribute("epis",epiService.obterListaEpi());

        model.addAttribute("buscaEPIDto", new EpiDto());

        return "visualizarquantidadeepi";
    }
}
