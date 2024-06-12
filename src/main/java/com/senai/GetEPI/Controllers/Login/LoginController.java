package com.senai.GetEPI.Controllers.Login;

import com.senai.GetEPI.DTOs.FuncaoDto;
import com.senai.GetEPI.DTOs.LoginDTO;
import com.senai.GetEPI.DTOs.ParametroGeralDTO;
import com.senai.GetEPI.OutrosObjetos.ApocalipseGetEPI;
import com.senai.GetEPI.OutrosObjetos.ErroGetEPI;
import com.senai.GetEPI.Services.AlocacaoService;
import com.senai.GetEPI.Services.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    ApocalipseGetEPI apocalipseGetEPI;

    @GetMapping
    public String mostrarLogin(Model model, HttpServletRequest request) {

        try {
            ErroGetEPI erro = apocalipseGetEPI.retornarErro(request);
            if (erro.getExibeErro()) {
                model.addAttribute("erro", true);
                model.addAttribute("tituloMensagemErro", erro.getMensagemErro());
                model.addAttribute("stacktraceMensagem", erro.getStackTrace());
            }

            LoginDTO login = new LoginDTO();
            model.addAttribute("loginDto", login);
            request.getSession().setAttribute("telaOrigem", "login");
            request.getSession().removeAttribute("alocacaoEmail");

        } catch (Exception e) {
            model.addAttribute("erroInterno", true);
            model.addAttribute("tituloMensagemErro", apocalipseGetEPI.refatoraMensagem(e.getClass().getName(), e.toString()));
            model.addAttribute("stacktraceMensagem", e.toString());

            LoginDTO login = new LoginDTO();
            model.addAttribute("loginDto", login);

        }

        return "login";
    }

    @PostMapping
    public String efetuarLogin(@ModelAttribute("loginDto") LoginDTO login, Model model, HttpServletRequest request) {

        try {
            if (usuarioService.loginValido(login)) {
                request.getSession().removeAttribute("telaOrigem");
                request.getSession().setAttribute("alocacaoEmail", login.getEmail());
                return "redirect:/home";
            }
            model.addAttribute("erro", true);
        } catch (Exception e) {
            model.addAttribute("erroInterno", true);
            model.addAttribute("tituloMensagemErro", apocalipseGetEPI.refatoraMensagem(e.getClass().getName(), e.toString()));
            model.addAttribute("stacktraceMensagem", e.toString());

            LoginDTO login2 = new LoginDTO();
            model.addAttribute("loginDto", login2);

        }

        return "login";
    }

}
