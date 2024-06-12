package com.senai.GetEPI.Controllers.Emprestimo;

import com.senai.GetEPI.DTOs.EmprestimoDTO;
import com.senai.GetEPI.DTOs.ViewEmprestimoDTO;
import com.senai.GetEPI.Services.AlocacaoService;
import com.senai.GetEPI.Services.EmprestimoService;
import jakarta.servlet.http.HttpServletRequest;
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

    @Autowired
    AlocacaoService alocacaoService;

    @GetMapping("/{id}")
    public String exibeVisualizarEmprestimo(Model model, @PathVariable Long id, HttpServletRequest request) {

        try {
            if (!alocacaoService.validaSessao(request).isEmpty()) {
                return alocacaoService.validaSessao(request);
            }

            ViewEmprestimoDTO emprestimo = emprestimoService.retonarViewEmprestimoDTO(id);

            model.addAttribute("emprestimoDTO", emprestimo);
            return "visualizaremprestimo";
        } catch (Exception e) {
            request.getSession().setAttribute("retornaErro", e);
            request.getSession().setAttribute("stacktrace", e);
            return "redirect:/listaemprestimo";
        }

    }
}
