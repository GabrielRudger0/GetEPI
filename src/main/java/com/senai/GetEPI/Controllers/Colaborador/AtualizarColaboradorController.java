package com.senai.GetEPI.Controllers.Colaborador;


import com.senai.GetEPI.DTOs.ColaboradorDto;
import com.senai.GetEPI.DTOs.UpdColaboradorDTO;
import com.senai.GetEPI.DTOs.UsuarioDTO;
import com.senai.GetEPI.Services.AlocacaoService;
import com.senai.GetEPI.Services.ColaboradorService;
import com.senai.GetEPI.Services.FuncaoService;
import com.senai.GetEPI.Services.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/atualizarcolaborador")
public class AtualizarColaboradorController {

    @Autowired
    ColaboradorService colaboradorService;

    @Autowired
    private FuncaoService funcaoService;

    @Autowired
    AlocacaoService alocacaoService;

    @GetMapping("/{id}")
    public String exibeAtualizaColaborador(Model model, @PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (!alocacaoService.validaSessao(request).isEmpty()) {
                return alocacaoService.validaSessao(request);
            }
            CacheControl nocache = CacheControl.noStore().mustRevalidate();
            response.setHeader("Cache-Control", nocache.getHeaderValue());

            UpdColaboradorDTO colaborador = colaboradorService.buscaColaboradorDTOupd(id);
            model.addAttribute("UpdColaboradorDTO", colaborador);
            model.addAttribute("funcaoColaborador",colaborador.getFuncao().getId());
            model.addAttribute("funcoes",  funcaoService.obterListaFuncao());
            return "atualizarcolaborador";

        } catch (Exception e) {
            request.getSession().setAttribute("retornaErro", e);
            request.getSession().setAttribute("stacktrace", e);
            return "redirect:/listacolaboradores";
        }

    }

    @PostMapping()
    public String botaoSalvar(@ModelAttribute("colaboradorDTO") ColaboradorDto colaborador, Model model, HttpServletRequest request) {

        try {
            String mensagemErro = colaboradorService.atualizaColaborador(colaborador);

            if (!mensagemErro.isEmpty()) {
                model.addAttribute("erro", true);
                model.addAttribute("mensagemErro", mensagemErro);
                return "atualizarcolaborador";
            }
            return "redirect:/listacolaboradores";

        } catch (Exception e) {
            request.getSession().setAttribute("retornaErro", e);
            request.getSession().setAttribute("stacktrace", e);
            return "redirect:/listacolaboradores";
        }

    }

}
