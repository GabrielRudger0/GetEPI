package com.senai.GetEPI.Controllers.Emprestimo;

import com.senai.GetEPI.DTOs.ColaboradorDto;
import com.senai.GetEPI.DTOs.EmprestimoDTO;
import com.senai.GetEPI.Models.ColaboradorModel;
import com.senai.GetEPI.Services.ColaboradorService;
import com.senai.GetEPI.Services.EmprestimoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/cadastraremprestimo")
public class CadastrarEmprestimoController {

    @Autowired
    EmprestimoService emprestimoService;

    @GetMapping()
    public String exibeCadastrarEmprestimo(Model model) {

        model.addAttribute("emprestimoDTO", new EmprestimoDTO());;

        return "cadastraremprestimo";
    }
}
