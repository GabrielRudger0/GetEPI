package com.senai.GetEPI.Controllers.Colaborador;


import com.senai.GetEPI.DTOs.ColaboradorDto;
import com.senai.GetEPI.DTOs.UsuarioDTO;
import com.senai.GetEPI.Services.ColaboradorService;
import com.senai.GetEPI.Services.FuncaoService;
import com.senai.GetEPI.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/{id}")
    public String exibeAtualizaColaborador(Model model, @PathVariable Long id) {
        ColaboradorDto colaborador = colaboradorService.buscaColaboradorDTO(id);
        model.addAttribute("colaboradorDTO", colaborador);
        model.addAttribute("funcaoColaborador",colaborador.getFuncao().getId());
        System.out.println("funcaoColaborador :"+ colaborador.getFuncao().getId());
        model.addAttribute("funcoes",  funcaoService.obterListaFuncao());
        model.addAttribute("dataNascimentoColaborador", stringToDate(colaborador.getDataNascimento()));
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

    private Date stringToDate(String stringData) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date data = new Date();
        try {
            // Convertendo a string para Date
            data = formato.parse(stringData);
            System.out.println("Data em formato Date: " + data);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return data;
    }

}
