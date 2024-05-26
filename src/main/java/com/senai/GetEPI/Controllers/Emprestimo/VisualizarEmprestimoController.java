package com.senai.GetEPI.Controllers.Emprestimo;

import com.senai.GetEPI.DTOs.EmprestimoDTO;
import com.senai.GetEPI.DTOs.ViewEmprestimoDTO;
import com.senai.GetEPI.Services.EmprestimoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/visualizaremprestimo")
public class VisualizarEmprestimoController {

    @Autowired
    EmprestimoService emprestimoService;

    @GetMapping("/{id}")
    public String exibeVisualizarEmprestimo(Model model, @PathVariable Long id) {

        ViewEmprestimoDTO emprestimo = emprestimoService.retonarViewEmprestimoDTO(id);

        model.addAttribute("emprestimoDTO", emprestimo);
        return "visualizaremprestimo";
    }
}
