package com.senai.GetEPI.Controllers.TipoEquipamento;

import com.senai.GetEPI.DTOs.TipoEquipamentoDTO;
import com.senai.GetEPI.Services.AlocacaoService;
import com.senai.GetEPI.Services.TipoEquipamentoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cadastrartipoequipamento")
public class CadastrarTipoEquipamento {

    @Autowired
    TipoEquipamentoService tipoEquipamentoService;

    @Autowired
    AlocacaoService alocacaoService;

    @GetMapping()
    public String exibeCadastrarTipoEquipamento(Model model, HttpServletRequest request) {
        try {
            if (!alocacaoService.validaSessao(request).isEmpty()) {
                return alocacaoService.validaSessao(request);
            }

            model.addAttribute("tipoEquipamentoDTO", new TipoEquipamentoDTO());
            return "cadastrartipoequipamento";
        } catch (Exception e) {
            request.getSession().setAttribute("retornaErro", e);
            request.getSession().setAttribute("stacktrace", e);
            return "redirect:/listatipoequipamento";
        }
    }

    @PostMapping()
    public String cadastrarTipoEquipamento(@ModelAttribute("tipoEquipamentoDTO") TipoEquipamentoDTO tipoEquipamento, Model model, HttpServletRequest request) {
        try {
            String mensagemErro = tipoEquipamentoService.cadastraTipoEquipamento(tipoEquipamento);

            if (!mensagemErro.isEmpty()) {
                model.addAttribute("erro", true);
                model.addAttribute("mensagemErro", mensagemErro);
                return "cadastrartipoequipamento";
            }

            return "redirect:/listatipoequipamento";

        } catch (Exception e) {
            request.getSession().setAttribute("retornaErro", e);
            request.getSession().setAttribute("stacktrace", e);
            return "redirect:/listatipoequipamento";
        }

    }
}
