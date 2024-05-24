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
        model.addAttribute("worldwide_epi_1", "EPI1");
        model.addAttribute("worldwide_epi_2", "EPI2");
        model.addAttribute("worldwide_epi_3", "EPI3");

        return "home";
    }

}
