package com.senai.GetEPI.Controllers.TipoEquipamento;

import com.senai.GetEPI.DTOs.TipoEquipamentoDTO;
import com.senai.GetEPI.Services.TipoEquipamentoService;
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

    @GetMapping()
    public String exibeCadastrarTipoEquipamento(Model model) {

        model.addAttribute("tipoEquipamentoDTO", new TipoEquipamentoDTO());
        return "cadastrartipoequipamento";
    }

    @PostMapping()
    public String cadastrarTipoEquipamento(@ModelAttribute("tipoEquipamentoDTO") TipoEquipamentoDTO tipoEquipamento, Model model) {
        String mensagemErro = tipoEquipamentoService.cadastraTipoEquipamento(tipoEquipamento);

        if (!mensagemErro.isEmpty()) {
            model.addAttribute("erro", true);
            model.addAttribute("mensagemErro", mensagemErro);
            return "cadastrartipoequipamento";
        }

        return "redirect:/listatipoequipamento";
    }
}
