package com.senai.GetEPI.Controllers.ParametroGeral;

import com.senai.GetEPI.DTOs.FuncaoDto;
import com.senai.GetEPI.DTOs.ParametroGeralDTO;
import com.senai.GetEPI.DTOs.TipoEquipamentoDTO;
import com.senai.GetEPI.OutrosObjetos.ApocalipseGetEPI;
import com.senai.GetEPI.OutrosObjetos.ErroGetEPI;
import com.senai.GetEPI.Services.ParametroGeralService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/parametrogeral")
public class ParametroGeralController {

    @Autowired
    ParametroGeralService parametroGeralService;

    @Autowired
    ApocalipseGetEPI apocalipseGetEPI;

    @GetMapping()
    public String exibeParametroGeral(Model model, HttpServletRequest request) {

        try {
            ErroGetEPI erro = apocalipseGetEPI.retornarErro(request);
            if (erro.getExibeErro()) {
                model.addAttribute("erro", true);
                model.addAttribute("tituloMensagemErro", erro.getMensagemErro());
                model.addAttribute("stacktraceMensagem", erro.getStackTrace());
            }

            ParametroGeralDTO parametros = parametroGeralService.retornaParametrosGerais();
            List<FuncaoDto> funcoes = parametroGeralService.listarFuncoes();
            model.addAttribute("funcoes", funcoes);
            model.addAttribute("parametroGeralDTO", parametros);


        } catch (Exception e) {
            model.addAttribute("erro", true);
            model.addAttribute("tituloMensagemErro", apocalipseGetEPI.refatoraMensagem(e.getClass().getName(), e.toString()));
            model.addAttribute("stacktraceMensagem", e.toString());

            model.addAttribute("funcoes", new ArrayList<FuncaoDto>());
            model.addAttribute("parametroGeralDTO", new ParametroGeralDTO());

        }

        return "parametrogeral";

    }

    @PostMapping()
    public String salvarParametros(@ModelAttribute("parametroGeralDTO") ParametroGeralDTO parametros, Model model) {

        try {
            parametroGeralService.salvarParametros(parametros);

            model.addAttribute("salvo", true);
            ParametroGeralDTO parametrosTela = parametroGeralService.retornaParametrosGerais();
            List<FuncaoDto> funcoes = parametroGeralService.listarFuncoes();
            model.addAttribute("funcoes", funcoes);
            model.addAttribute("parametroGeralDTO", parametrosTela);

        } catch (Exception e) {
            model.addAttribute("erro", true);
            model.addAttribute("tituloMensagemErro", apocalipseGetEPI.refatoraMensagem(e.getClass().getName(), e.toString()));
            model.addAttribute("stacktraceMensagem", e.toString());

            model.addAttribute("funcoes", new ArrayList<FuncaoDto>());
            model.addAttribute("parametroGeralDTO", new ParametroGeralDTO());

        }

        return "parametrogeral";
    }
}
