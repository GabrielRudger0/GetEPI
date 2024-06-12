package com.senai.GetEPI.Controllers.Usuario;

import com.senai.GetEPI.DTOs.UsuarioDTO;
import com.senai.GetEPI.OutrosObjetos.ApocalipseGetEPI;
import com.senai.GetEPI.OutrosObjetos.ErroGetEPI;
import com.senai.GetEPI.Services.AlocacaoService;
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

    @Autowired
    AlocacaoService alocacaoService;

    @GetMapping
    public String exibeListaUsuario(Model model, HttpServletRequest request) {
        try {
            if (!alocacaoService.validaSessao(request).isEmpty()) {
                return alocacaoService.validaSessao(request);
            }

            List<UsuarioDTO> listaUsuario = usuarioService.retornaListaUsuarioDTO();

            ErroGetEPI erro = apocalipseGetEPI.retornarErro(request);
            if (erro.getExibeErro()) {
                model.addAttribute("erro", true);
                model.addAttribute("tituloMensagemErro", erro.getMensagemErro());
                model.addAttribute("stacktraceMensagem", erro.getStackTrace());
            }

            boolean nenhumUsuario = false;
            if(listaUsuario.isEmpty()) {
                nenhumUsuario = true;
            }

            model.addAttribute("usuarios", listaUsuario);
            model.addAttribute("nenhumUsuario", nenhumUsuario);
            model.addAttribute("buscaUsuarioDTO", new UsuarioDTO());
            return "listausuario";
        } catch (Exception e) {
            model.addAttribute("erro", true);
            model.addAttribute("tituloMensagemErro", apocalipseGetEPI.refatoraMensagem(e.getClass().getName(), e.toString()));
            model.addAttribute("stacktraceMensagem", e.toString());

            model.addAttribute("usuarios", new ArrayList<UsuarioDTO>());
            model.addAttribute("nenhumUsuario", true);
            model.addAttribute("buscaUsuarioDTO", new UsuarioDTO());
            return "listausuario";
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirUsuario(@PathVariable Long id, HttpServletRequest request){

        String mensagemErro = usuarioService.excluirUsuario(id,request);
        if (mensagemErro.isEmpty()){
            return ResponseEntity.ok("Usuário excluído com sucesso.");
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensagemErro);

    }

    @PostMapping
    public String buscarUsuario(@ModelAttribute("buscaUsuarioDTO") UsuarioDTO usuarioBuscado, Model model) {
        try {
            List<UsuarioDTO> listaUsuariosEncontrados = usuarioService.buscarUsuarioPorNome(usuarioBuscado);
            boolean nenhumUsuario = false;
            if(listaUsuariosEncontrados.isEmpty()) {
                nenhumUsuario = true;
            }

            model.addAttribute("usuarios", listaUsuariosEncontrados);
            model.addAttribute("nenhumUsuario", nenhumUsuario);
            return "listausuario";

        } catch (Exception e) {
            model.addAttribute("erro", true);
            model.addAttribute("tituloMensagemErro", apocalipseGetEPI.refatoraMensagem(e.getClass().getName(), e.toString()));
            model.addAttribute("stacktraceMensagem", e.toString());

            model.addAttribute("usuarios", new ArrayList<UsuarioDTO>());
            model.addAttribute("nenhumUsuario", true);
            model.addAttribute("buscaUsuarioDTO", new UsuarioDTO());
            return "listausuario";
        }
    }

}
