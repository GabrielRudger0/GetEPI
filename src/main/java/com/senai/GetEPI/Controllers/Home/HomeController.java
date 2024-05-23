package com.senai.GetEPI.Controllers.Home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    @GetMapping()
    public String exibirHome(Model model) {
        model.addAttribute("valor1teste", 250);
        return "home";
    }

}
