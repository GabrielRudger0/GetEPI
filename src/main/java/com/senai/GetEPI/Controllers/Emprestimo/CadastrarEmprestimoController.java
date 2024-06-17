package com.senai.GetEPI.Controllers.Emprestimo;

import com.senai.GetEPI.DTOs.ColaboradorDto;
import com.senai.GetEPI.DTOs.EmprestimoDTO;
import com.senai.GetEPI.Models.ColaboradorModel;
import com.senai.GetEPI.Models.EpiModel;
import com.senai.GetEPI.Services.AlocacaoService;
import com.senai.GetEPI.Services.ColaboradorService;
import com.senai.GetEPI.Services.EmprestimoService;
import com.senai.GetEPI.Services.EpiService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
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

    @Autowired
    AlocacaoService alocacaoService;

    @GetMapping()
    public String exibeCadastrarEmprestimo(Model model, HttpServletRequest request, HttpServletResponse response) {

        try {
            if (!alocacaoService.validaSessao(request).isEmpty()) {
                return alocacaoService.validaSessao(request);
            }
            CacheControl nocache = CacheControl.noStore().mustRevalidate();
            response.setHeader("Cache-Control", nocache.getHeaderValue());

            List<ColaboradorModel> colaboradores = colaboradorService.obterListaColaboradores();
            List<EpiModel> epis = epiService.retornaEPIModel();

            model.addAttribute("emprestimoDTO", new EmprestimoDTO());
            model.addAttribute("colaboradores", colaboradores);
            model.addAttribute("epis", epis);

            return "cadastraremprestimo";
        } catch (Exception e) {
            request.getSession().setAttribute("retornaErro", e);
            request.getSession().setAttribute("stacktrace", e);
            return "redirect:/listaemprestimo";
        }

    }

    @PostMapping()
    public String cadastrarEmprestimo(@ModelAttribute("emprestimoDTO") EmprestimoDTO emprestimo, Model model, HttpServletRequest request) {

        try {
            String mensagemErro = emprestimoService.cadastrarEmprestimo(emprestimo);

            if (!mensagemErro.isEmpty()) {
                model.addAttribute("erro", true);
                model.addAttribute("mensagemErro", mensagemErro);

                List<ColaboradorModel> colaboradores = colaboradorService.obterListaColaboradores();
                List<EpiModel> epis = epiService.retornaEPIModel();
                model.addAttribute("colaboradores", colaboradores);
                model.addAttribute("epis", epis);
                return "cadastraremprestimo";
            }

            return "redirect:/listaemprestimo";
        } catch (Exception e) {
            request.getSession().setAttribute("retornaErro", e);
            request.getSession().setAttribute("stacktrace", e);
            return "redirect:/listaemprestimo";
        }
    }
}
