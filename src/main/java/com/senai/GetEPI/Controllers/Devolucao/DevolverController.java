package com.senai.GetEPI.Controllers.Devolucao;

import com.senai.GetEPI.DTOs.EmprestimoDTO;
import com.senai.GetEPI.DTOs.ViewEmprestimoDTO;
import com.senai.GetEPI.Services.EmprestimoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/devolver")
public class DevolverController {

    @Autowired
    EmprestimoService emprestimoService;

    @GetMapping("/{id}")
    public String exibeDevolver(Model model, @PathVariable Long id) {

        ViewEmprestimoDTO emprestimo = emprestimoService.retonarViewEmprestimoDTO(id);
        model.addAttribute("emprestimoDTO", emprestimo);

        return "devolver";
    }

    @PostMapping("/{id}")
    public String realizaDevolucao(@PathVariable Long id, Model model) {

        emprestimoService.registrarDevolucao(id);

        return "redirect:/listadevolucao";
    }

}
