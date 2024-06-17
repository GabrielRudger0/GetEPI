package com.senai.GetEPI.Controllers.Usuario;

import com.senai.GetEPI.DTOs.UsuarioDTO;
import com.senai.GetEPI.Services.AlocacaoService;
import com.senai.GetEPI.Services.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/atualizausuario")
public class AtualizarUsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    AlocacaoService alocacaoService;

    @GetMapping("/{id}")
    public String exibeAtualizaUsuario(Model model, @PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
        try{
            if (!alocacaoService.validaSessao(request).isEmpty()) {
                return alocacaoService.validaSessao(request);
            }
            CacheControl nocache = CacheControl.noStore().mustRevalidate();
            response.setHeader("Cache-Control", nocache.getHeaderValue());

            UsuarioDTO usuario = usuarioService.retornaUsuarioDTO(id);
            model.addAttribute("usuarioDTO", usuario);
            return "atualizausuario";

        } catch (Exception e) {
            HttpSession sessao = request.getSession();
            sessao.setAttribute("retornaErro", e);
            sessao.setAttribute("stacktrace", e);
            return "redirect:/listausuario";
        }

    }

    @PostMapping()
    public String botaoSalvar(@ModelAttribute("usuarioDTO") UsuarioDTO usuario, Model model) {
        String mensagemErro = usuarioService.atualizaUsuario(usuario);

        if (!mensagemErro.isEmpty()) {
            model.addAttribute("erro", true);
            model.addAttribute("mensagemErro", mensagemErro);
            return "atualizausuario";
        }
        return "redirect:/listausuario";

    }
}
