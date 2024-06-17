package com.senai.GetEPI.Controllers.Colaborador;

import com.senai.GetEPI.DTOs.ColaboradorDto;
import com.senai.GetEPI.OutrosObjetos.ApocalipseGetEPI;
import com.senai.GetEPI.Services.AlocacaoService;
import com.senai.GetEPI.Services.ColaboradorService;
import com.senai.GetEPI.Services.FuncaoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cadastrarcolaborador")
public class CadastrarColaboradorController {

    @Autowired
    ColaboradorService colaboradorService;
    @Autowired
    private FuncaoService funcaoService;
    @Autowired
    ApocalipseGetEPI apocalipseGetEPI;

    @Autowired
    AlocacaoService alocacaoService;

    @GetMapping()
    public String cadastrarColaborador(Model model, HttpServletRequest request, HttpServletResponse response){

        try {
            if (!alocacaoService.validaSessao(request).isEmpty()) {
                return alocacaoService.validaSessao(request);
            }
            CacheControl nocache = CacheControl.noStore().mustRevalidate();
            response.setHeader("Cache-Control", nocache.getHeaderValue());

            ColaboradorDto colaboradorDto = new ColaboradorDto();

            model.addAttribute("colaboradorDto",colaboradorDto);
            model.addAttribute("funcoes", funcaoService.obterListaFuncao());

        }catch (Exception e) {
            HttpSession sessao = request.getSession();
            sessao.setAttribute("retornaErro", e);
            sessao.setAttribute("stacktrace", e);
            return "redirect:/listacolaboradores";
        }

        return "cadastrarcolaborador";

    }

    @PostMapping()
    public String enviarDadosColaborador(@ModelAttribute("colaboradorDto")ColaboradorDto colaboradorDto,Model model){
        try {
            String mensagemErro = colaboradorService.cadastrarColaborador(colaboradorDto);
            if (!mensagemErro.isEmpty()) {
                model.addAttribute("erro", true);
                model.addAttribute("mensagemErro", mensagemErro);
                model.addAttribute("funcoes", funcaoService.obterListaFuncao());

                return "cadastrarcolaborador";
            }

            return "redirect:/listacolaboradores";

        } catch (Exception e) {
            model.addAttribute("erro", true);
            model.addAttribute("tituloMensagemErro", apocalipseGetEPI.refatoraMensagem(e.getClass().getName(), e.toString()));
            model.addAttribute("stacktraceMensagem", e.toString());

            model.addAttribute("funcoes", funcaoService.obterListaFuncao());

            return "cadastrarcolaborador";

        }

    }
}
