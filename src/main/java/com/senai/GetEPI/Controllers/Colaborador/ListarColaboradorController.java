package com.senai.GetEPI.Controllers.Colaborador;

import com.senai.GetEPI.DTOs.ColaboradorDto;
import com.senai.GetEPI.DTOs.UsuarioDTO;
import com.senai.GetEPI.Services.ColaboradorService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping()
public class ListarColaboradorController {

    @Autowired
    ColaboradorService colaboradorService;

    @GetMapping("/listacolaboradores")
    public String exibirListaColaboradores(Model model, HttpServletRequest request, ColaboradorDto colaboladorDto){

        List<ColaboradorDto> listaColaborador = colaboradorService.retornaListaColaboradorDTO();

        model.addAttribute("colaboradores",colaboradorService.retornaListaColaboradorDTO());
        model.addAttribute("buscaColaboradorDTO",new ColaboradorDto());

        return "listacolaborador";


    }
}
