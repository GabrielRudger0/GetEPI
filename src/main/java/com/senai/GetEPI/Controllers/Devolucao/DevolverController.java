package com.senai.GetEPI.Controllers.Devolucao;

import com.senai.GetEPI.DTOs.EmprestimoDTO;
import com.senai.GetEPI.DTOs.ViewEmprestimoDTO;
import com.senai.GetEPI.OutrosObjetos.ApocalipseGetEPI;
import com.senai.GetEPI.Services.AlocacaoService;
import com.senai.GetEPI.Services.EmprestimoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/devolver")
public class DevolverController {

    @Autowired
    EmprestimoService emprestimoService;

    @Autowired
    AlocacaoService alocacaoService;

    @GetMapping("/{id}")
    public String exibeDevolver(Model model, @PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {

        try {
            if (!alocacaoService.validaSessao(request).isEmpty()) {
                return alocacaoService.validaSessao(request);
            }
            CacheControl nocache = CacheControl.noStore().mustRevalidate();
            response.setHeader("Cache-Control", nocache.getHeaderValue());

            ViewEmprestimoDTO emprestimo = emprestimoService.retonarViewEmprestimoDTO(id);
            model.addAttribute("emprestimoDTO", emprestimo);

            return "devolver";
        } catch (Exception e) {
            request.getSession().setAttribute("retornaErro", e);
            request.getSession().setAttribute("stacktrace", e);
            return "redirect:/listadevolucao";
        }

    }

    @PostMapping("/{id}")
    public String realizaDevolucao(@PathVariable Long id, Model model, HttpServletRequest request) {
        try {
            emprestimoService.registrarDevolucao(id);
            return "redirect:/listadevolucao";
        } catch (Exception e) {
            request.getSession().setAttribute("retornaErro", e);
            request.getSession().setAttribute("stacktrace", e);
            return "redirect:/listadevolucao";
        }

    }

}
