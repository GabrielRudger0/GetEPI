package com.senai.GetEPI.Controllers.Emprestimo;

import com.senai.GetEPI.DTOs.ColaboradorDto;
import com.senai.GetEPI.DTOs.EmprestimoDTO;
import com.senai.GetEPI.Models.ColaboradorModel;
import com.senai.GetEPI.Models.EpiModel;
import com.senai.GetEPI.Services.ColaboradorService;
import com.senai.GetEPI.Services.EmprestimoService;
import com.senai.GetEPI.Services.EpiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/cadastraremprestimo")
public class CadastrarEmprestimoController {

    @Autowired
    EmprestimoService emprestimoService;

    @Autowired
    ColaboradorService colaboradorService;

    @Autowired
    EpiService epiService;

    @GetMapping()
    public String exibeCadastrarEmprestimo(Model model) {

        List<ColaboradorModel> colaboradores = colaboradorService.obterListaColaboradores();
        List<EpiModel> epis = epiService.retornaEPIModel();

        model.addAttribute("emprestimoDTO", new EmprestimoDTO());
        model.addAttribute("colaboradores", colaboradores);
        model.addAttribute("epis", epis);

        return "cadastraremprestimo";
    }

    @PostMapping()
    public String cadastrarEmprestimo(@ModelAttribute("emprestimoDTO") EmprestimoDTO emprestimo, Model model) {
        String mensagemErro = emprestimoService.cadastrarEmprestimo(emprestimo);

        if (!mensagemErro.isEmpty()) {
            return "cadastraremprestimo";
        }

        return "redirect:/listaemprestimo";
    }
}
