package com.senai.GetEPI.Controllers.Usuario;

import com.senai.GetEPI.DTOs.UsuarioDTO;
import com.senai.GetEPI.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/atualizausuario")
public class AtualizarUsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/{id}")
    public String exibeAtualizaUsuario(Model model, @PathVariable Long id) {
        UsuarioDTO usuario = usuarioService.retornaUsuarioDTO(id);
        model.addAttribute("usuarioDTO", usuario);
        return "atualizausuario";
    }

    @PostMapping()
    public String botaoSalvar(@ModelAttribute("usuarioDTO") UsuarioDTO usuario, Model model) {
        String mensagemErro = usuarioService.atualizaUsuario(usuario);

        if (!mensagemErro.isEmpty()) {
            model.addAttribute("erro", true);
            model.addAttribute("mensagemErro", mensagemErro);
            return "atualizausuario";
        }
        return "redirect:/listausuario";

    }
}
