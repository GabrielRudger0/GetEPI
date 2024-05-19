package com.senai.GetEPI.Controllers.Usuario;

import com.senai.GetEPI.DTOs.UsuarioDTO;
import com.senai.GetEPI.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cadastrarusuario")
public class CadastrarUsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping()
    public String exibeCadastrarUsuario(Model model){

        UsuarioDTO usuario = new UsuarioDTO();

        model.addAttribute("erro", false);
        model.addAttribute("mensagemErro", "");
        model.addAttribute("usuarioDTO", usuario);
        return "cadastrousuario";
    }

    @PostMapping()
    public String botaoCadastrarUsuario(@ModelAttribute("usuarioDTO") UsuarioDTO usuario, Model model) {
        String mensagemErro = usuarioService.inserirUsuario(usuario);

        if (!mensagemErro.isEmpty()) {
            model.addAttribute("erro", true);
            model.addAttribute("mensagemErro", mensagemErro);
            return "atualizausuario";
        }
        return "redirect:/listausuario";
    }
}
