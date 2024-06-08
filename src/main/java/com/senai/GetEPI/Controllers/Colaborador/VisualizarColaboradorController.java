package com.senai.GetEPI.Controllers.Colaborador;

import com.senai.GetEPI.DTOs.ColaboradorDto;
import com.senai.GetEPI.DTOs.UsuarioDTO;
import com.senai.GetEPI.Services.ColaboradorService;
import com.senai.GetEPI.Services.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/visualizarcolaborador")
public class VisualizarColaboradorController {

    @Autowired
    ColaboradorService colaboradorService;

    @GetMapping("/{id}")
    public String exibeVisualizarUsuario(Model model, @PathVariable Long id, HttpServletRequest request) {
        try {
            ColaboradorDto colaborador = colaboradorService.buscaColaboradorDTO(id);
            model.addAttribute("colaboradorDTO", colaborador);

            return "visualizarcolaborador";
        } catch (Exception e) {
            HttpSession sessao = request.getSession();
            sessao.setAttribute("retornaErro", e);
            sessao.setAttribute("stacktrace", e);
            return "redirect:/listacolaboradores";
        }

    }

}
