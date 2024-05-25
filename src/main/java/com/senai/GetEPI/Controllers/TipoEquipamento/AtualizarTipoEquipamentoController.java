package com.senai.GetEPI.Controllers.TipoEquipamento;

import com.senai.GetEPI.DTOs.TipoEquipamentoDTO;
import com.senai.GetEPI.Services.TipoEquipamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/atualizartipoequipamento")
public class AtualizarTipoEquipamentoController {


    @Autowired
    TipoEquipamentoService tipoEquipamentoService;

    @GetMapping("/{id}")
    public String exibeAtualizarTipoEquipamento(Model model, @PathVariable Long id) {


        TipoEquipamentoDTO tipoEquipamento = tipoEquipamentoService.retonaTipoEquipamentoDTO(id);

        model.addAttribute("tipoEquipamentoDTO", tipoEquipamento);

        return "atualizartipoequipamento";


    }

    @PostMapping()
    public String atualizaTipoEquipamento(@ModelAttribute("tipoEquipamentoDTO") TipoEquipamentoDTO tipoEquipamento, Model model) {
        String mensagemErro = tipoEquipamentoService.atualizarTipoEquipamento(tipoEquipamento);

        if (!mensagemErro.isEmpty()) {
            model.addAttribute("erro", true);
            model.addAttribute("mensagemErro", mensagemErro);
            return "atualizartipoequipamento";
        }

        return "redirect:/listatipoequipamento";
    }

}
