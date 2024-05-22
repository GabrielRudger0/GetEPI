package com.senai.GetEPI.Controllers.Usuario;

import com.senai.GetEPI.DTOs.UsuarioDTO;
import com.senai.GetEPI.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/listausuario")
public class ListarUsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping
    public String exibeListaUsuario(Model model) {
        List<UsuarioDTO> listaUsuario = usuarioService.retornaListaUsuarioDTO();

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
    public ResponseEntity<String> excluirUsuario(@PathVariable Long id){

        boolean sucesso = usuarioService.excluirUsuario(id);
        if (sucesso){
            return ResponseEntity.ok("Usuário excluído com sucesso.");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao excluir usuário.");
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
