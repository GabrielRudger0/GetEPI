package com.senai.GetEPI.Controllers.Usuario;

import com.senai.GetEPI.DTOs.UsuarioDTO;
import com.senai.GetEPI.OutrosObjetos.ApocalipseGetEPI;
import com.senai.GetEPI.OutrosObjetos.ErroGetEPI;
import com.senai.GetEPI.Services.AlocacaoService;
import com.senai.GetEPI.Services.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cadastrarusuario")
public class CadastrarUsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    ApocalipseGetEPI apocalipseGetEPI;

    @Autowired
    AlocacaoService alocacaoService;

    @GetMapping()
    public String exibeCadastrarUsuario(Model model, HttpServletRequest request){

        if (!alocacaoService.validaSessao(request).isEmpty() && !visualizacaoInterna(request)) {
            return alocacaoService.validaSessao(request);
        }

        ErroGetEPI erroGetEPI = apocalipseGetEPI.retornarErro(request);

        if (erroGetEPI.getExibeErro()) {
            model.addAttribute("erro", true);
            model.addAttribute("tituloMensagemErro", erroGetEPI.getMensagemErro());
            model.addAttribute("stacktraceMensagem", erroGetEPI.getStackTrace());
        }
        model.addAttribute("visualizacaoInterna", visualizacaoInterna(request));


        UsuarioDTO usuario = new UsuarioDTO();

        model.addAttribute("usuarioDTO", usuario);
        return "cadastrousuario";

    }

    @PostMapping()
    public String botaoCadastrarUsuario(@ModelAttribute("usuarioDTO") UsuarioDTO usuario, Model model, HttpServletRequest request) {
        try {
            String mensagemErro = usuarioService.inserirUsuario(usuario);

            if (!mensagemErro.isEmpty()) {

                model.addAttribute("erro", true);
                model.addAttribute("tituloMensagemErro", "Erro no cadastro!");
                model.addAttribute("stacktraceMensagem", mensagemErro);
                model.addAttribute("visualizacaoInterna", visualizacaoInterna(request));

                return "cadastrousuario";
            }
        } catch (Exception e) {
            request.getSession().setAttribute("retornaErro", e.getClass().getName());
            request.getSession().setAttribute("stacktrace", e.toString());
            model.addAttribute("visualizacaoInterna", visualizacaoInterna(request));

            return "redirect:/cadastrarusuario";
        }

        if (!visualizacaoInterna(request)) {
            return "redirect:/login";
        }

        return "redirect:/listausuario";
    }
    private boolean visualizacaoInterna(HttpServletRequest request) {
        Object objectTelaOrigem = request.getSession().getAttribute("telaOrigem");
        if (objectTelaOrigem != null) {
            String telaOrigem = objectTelaOrigem.toString();
            if (telaOrigem.equals("login")) {
                return false;
            }
        }
        return true;
    }
}
