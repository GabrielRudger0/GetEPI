package com.senai.GetEPI.Controllers.TipoEquipamento;

import com.senai.GetEPI.DTOs.TipoEquipamentoDTO;
import com.senai.GetEPI.Services.AlocacaoService;
import com.senai.GetEPI.Services.TipoEquipamentoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/atualizartipoequipamento")
public class AtualizarTipoEquipamentoController {

    @Autowired
    AlocacaoService alocacaoService;

    @Autowired
    TipoEquipamentoService tipoEquipamentoService;

    @GetMapping("/{id}")
    public String exibeAtualizarTipoEquipamento(Model model, @PathVariable Long id, HttpServletRequest request) {

        try {
            if (!alocacaoService.validaSessao(request).isEmpty()) {
                return alocacaoService.validaSessao(request);
            }

            TipoEquipamentoDTO tipoEquipamento = tipoEquipamentoService.retonaTipoEquipamentoDTO(id);

            model.addAttribute("tipoEquipamentoDTO", tipoEquipamento);

            return "atualizartipoequipamento";
        } catch (Exception e) {
            request.getSession().setAttribute("retornaErro", e);
            request.getSession().setAttribute("stacktrace", e);
            return "redirect:/listatipoequipamento";
        }



    }

    @PostMapping()
    public String atualizaTipoEquipamento(@ModelAttribute("tipoEquipamentoDTO") TipoEquipamentoDTO tipoEquipamento, Model model, HttpServletRequest request) {
        try {
            String mensagemErro = tipoEquipamentoService.atualizarTipoEquipamento(tipoEquipamento);

            if (!mensagemErro.isEmpty()) {
                model.addAttribute("erro", true);
                model.addAttribute("mensagemErro", mensagemErro);
                return "atualizartipoequipamento";
            }
            return "redirect:/listatipoequipamento";
        } catch (Exception e) {
            request.getSession().setAttribute("retornaErro", e);
            request.getSession().setAttribute("stacktrace", e);
            return "redirect:/listatipoequipamento";
        }
    }
}
