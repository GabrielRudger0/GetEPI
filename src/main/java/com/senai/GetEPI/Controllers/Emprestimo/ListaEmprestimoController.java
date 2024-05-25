package com.senai.GetEPI.Controllers.Emprestimo;

import com.senai.GetEPI.DTOs.EmprestimoDTO;
import com.senai.GetEPI.DTOs.ViewEmprestimoDTO;
import com.senai.GetEPI.Services.EmprestimoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.swing.text.View;
import java.util.List;

@Controller
@RequestMapping("/listaemprestimo")
public class ListaEmprestimoController {

    @Autowired
    EmprestimoService emprestimoService;

    @GetMapping()
    public String exibeListaEmprestimo(Model model) {

        //List<EmprestimoDTO> listaEmprestimos = emprestimoService.retornaListaEmprestimos();

        List<ViewEmprestimoDTO> listaEmprestimosFormatada = emprestimoService.retornaListaEmprestimosView() ;
        boolean nenhumRegistro = false;
        if(listaEmprestimosFormatada.isEmpty()) {
            nenhumRegistro = true;
        }

        model.addAttribute("buscaEmprestimoDTO", new ViewEmprestimoDTO());
        model.addAttribute("emprestimos", listaEmprestimosFormatada);
        model.addAttribute("nenhumRegistro", nenhumRegistro);

        return "listaemprestimo";
    }

}
