package com.senai.GetEPI.Controllers.Login;

import com.senai.GetEPI.DTOs.LoginDTO;
import com.senai.GetEPI.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping
    public String mostrarLogin(Model model) {

        LoginDTO login = new LoginDTO();
        model.addAttribute("loginDto", login);
        return "login";
    }

    @PostMapping
    public String efetuarLogin(@ModelAttribute("loginDto") LoginDTO login, Model model) {

        if (usuarioService.loginValido(login)) {

            return "redirect:/home";
        }
        model.addAttribute("erro", true);
        return "login";
    }

}
