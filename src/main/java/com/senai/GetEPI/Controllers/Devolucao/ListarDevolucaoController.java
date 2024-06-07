package com.senai.GetEPI.Controllers.Devolucao;

import com.senai.GetEPI.DTOs.BuscaDTO;
import com.senai.GetEPI.DTOs.EmprestimoDTO;
import com.senai.GetEPI.DTOs.ViewEmprestimoDTO;
import com.senai.GetEPI.Services.EmprestimoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/listadevolucao")
public class ListarDevolucaoController {

    @Autowired
    EmprestimoService emprestimoService;

    @GetMapping()
    public String exibeListaDevolucao(Model model) {

        List<ViewEmprestimoDTO> emprestimos = emprestimoService.retornaListaEmprestimosNaoDevolvidos();

        boolean nenhumRegistro = false;
        if(emprestimos.isEmpty()) {
            nenhumRegistro = true;
        }
        model.addAttribute("nenhumRegistro", nenhumRegistro);
        model.addAttribute("emprestimosNaoDevolvidos", emprestimos);
        model.addAttribute("buscaDTO", new BuscaDTO());

        return "listadevolucao";
    }

    @PostMapping
    public String buscarRegistros(@ModelAttribute("buscaDTO") BuscaDTO registroBuscado, Model model) {
        List<ViewEmprestimoDTO> listaRegistrosEncontrados = emprestimoService.buscarPendentesDevolucaoPorColaborador(registroBuscado.getNomeColaborador());

        boolean nenhumRegistro = false;
        if(listaRegistrosEncontrados.isEmpty()) {
            nenhumRegistro = true;
        }

        model.addAttribute("emprestimosNaoDevolvidos", listaRegistrosEncontrados);
        model.addAttribute("nenhumRegistro", nenhumRegistro);
        return "listadevolucao";
    }
}
