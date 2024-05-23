package com.senai.GetEPI.Controllers.Colaborador;


import com.senai.GetEPI.DTOs.ColaboradorDto;
import com.senai.GetEPI.DTOs.UsuarioDTO;
import com.senai.GetEPI.Services.ColaboradorService;
import com.senai.GetEPI.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/atualizarcolaborador")
public class AtualizarColaboradorController {

    @Autowired
    ColaboradorService colaboradorService;

    @GetMapping("/{id}")
    public String exibeAtualizaColaborador(Model model, @PathVariable Long id) {
        ColaboradorDto colaborador = colaboradorService.buscaColaboradorDTO(id);
        model.addAttribute("colaboradorDTO", colaborador);
        return "atualizarcolaborador";
    }

    @PostMapping()
    public String botaoSalvar(@ModelAttribute("colaboradorDTO") ColaboradorDto colaborador, Model model) {
        String mensagemErro = colaboradorService.atualizaColaborador(colaborador);

        if (!mensagemErro.isEmpty()) {
            model.addAttribute("erro", true);
            model.addAttribute("mensagemErro", mensagemErro);
            return "atualizarcolaborador";
        }
        return "redirect:/listacolaboradores";

    }

}
