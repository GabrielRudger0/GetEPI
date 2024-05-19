package com.senai.GetEPI.Controllers.Login;

import com.senai.GetEPI.DTOs.LoginDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class LoginController {


    @GetMapping("/login")
    public String mostrarLogin(Model model) {

        LoginDTO login = new LoginDTO();
        model.addAttribute("loginDto", login);
        return "login";
    }

}
