package com.senai.GetEPI.Controllers.Usuario;

import com.senai.GetEPI.DTOs.UsuarioDTO;
import com.senai.GetEPI.OutrosObjetos.ApocalipseGetEPI;
import com.senai.GetEPI.OutrosObjetos.ErroGetEPI;
import com.senai.GetEPI.Services.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/listausuario")
public class ListarUsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    ApocalipseGetEPI apocalipseGetEPI;

    @GetMapping
    public String exibeListaUsuario(Model model, HttpServletRequest request) {
        List<UsuarioDTO> listaUsuario = usuarioService.retornaListaUsuarioDTO();

        ErroGetEPI erro = apocalipseGetEPI.retornarErro(request);
        if (erro.getExibeErro()) {
            model.addAttribute("erro", true);
            model.addAttribute("mensagemErro", erro.getMensagemErro());
        }

        boolean nenhumUsuario = false;
        if(listaUsuario.isEmpty()) {
            nenhumUsuario = true;
        }

        model.addAttribute("usuarios", listaUsuario);
        model.addAttribute("nenhumUsuario", nenhumUsuario);
        model.addAttribute("buscaUsuarioDTO", new UsuarioDTO());
        return "listausuario";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirUsuario(@PathVariable Long id, HttpServletRequest request, Model model){

        String mensagemErro = usuarioService.excluirUsuario(id);
        if (mensagemErro.isEmpty()){
            return ResponseEntity.ok("Usuário excluído com sucesso.");
        }

        request.getSession().setAttribute("retornaErro", mensagemErro);
        ErroGetEPI erro = apocalipseGetEPI.retornarErro(request);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro.getMensagemErro());

    }

    @PostMapping
    public String buscarUsuario(@ModelAttribute("buscaUsuarioDTO") UsuarioDTO usuarioBuscado, Model model) {
        List<UsuarioDTO> listaUsuariosEncontrados = usuarioService.buscarUsuarioPorNome(usuarioBuscado);

        boolean nenhumUsuario = false;
        if(listaUsuariosEncontrados.isEmpty()) {
            nenhumUsuario = true;
        }



        model.addAttribute("usuarios", listaUsuariosEncontrados);
        model.addAttribute("nenhumUsuario", nenhumUsuario);
        return "listausuario";
    }

}
