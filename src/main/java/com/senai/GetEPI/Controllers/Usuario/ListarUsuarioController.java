package com.senai.GetEPI.Controllers.Usuario;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/listausuario")
public class ListarUsuarioController {

    @GetMapping
    public String exibeListaUsuario(Model model) {
        return "listausuario";
    }

}
