package com.senai.GetEPI.Controllers.Usuario;

import com.senai.GetEPI.DTOs.UsuarioDTO;
import com.senai.GetEPI.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/listausuario")
public class ListarUsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping
    public String exibeListaUsuario(Model model) {
        List<UsuarioDTO> listaUsuario = usuarioService.retornaListaUsuarioDTO();

        boolean nenhumUsuario = false;
        if(listaUsuario.isEmpty()) {
            nenhumUsuario = true;
        }

        model.addAttribute("usuarios", listaUsuario);
        model.addAttribute("nenhumUsuario", nenhumUsuario);
        return "listausuario";
    }

}
