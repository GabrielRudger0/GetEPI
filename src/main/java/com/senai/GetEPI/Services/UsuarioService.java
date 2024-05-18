package com.senai.GetEPI.Services;

import com.senai.GetEPI.DTOs.UsuarioDTO;
import com.senai.GetEPI.Models.UsuarioModel;
import com.senai.GetEPI.Repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public List<UsuarioDTO> retornaListaUsuarioDTO() {
        return converterListaUsuarioDTO(usuarioRepository.findAll());
    }

    public UsuarioDTO retornaUsuarioDTO(Long id) {
        UsuarioModel usuarioBD = usuarioRepository.findById(id).get();

        return new UsuarioDTO(usuarioBD);

    }

    public String inserirUsuario(UsuarioDTO usuario) {

        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            return "Já existe cadastro com estas credenciais!";
        }

        if (usuario.getSenha().length() < 5) {
            return "A senha deve ter no mínimo 5 caracteres!";
        }

        usuarioRepository.save(new UsuarioModel(usuario));
        return "";

    }

    private List<UsuarioDTO> converterListaUsuarioDTO(List<UsuarioModel> listaUsuarioModel) {
        return listaUsuarioModel.stream().map(UsuarioDTO::new).collect(Collectors.toList());
    }

}
