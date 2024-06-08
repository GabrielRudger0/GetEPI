package com.senai.GetEPI.Controllers.Usuario;

import com.senai.GetEPI.DTOs.UsuarioDTO;
import com.senai.GetEPI.Services.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("/visualizarusuario")
public class VisualizarUsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/{id}")
    public String exibeVisualizarUsuario(Model model, @PathVariable Long id, HttpServletRequest request) {

        try {
            UsuarioDTO usuario = usuarioService.retornaUsuarioDTO(id);
            model.addAttribute("usuarioDTO", usuario);

            return "visualizausuario";
        } catch (Exception e) {
            HttpSession sessao = request.getSession();
            sessao.setAttribute("retornaErro", e);
            sessao.setAttribute("stacktrace", e);
            return "redirect:/listausuario";
        }


    }
}
