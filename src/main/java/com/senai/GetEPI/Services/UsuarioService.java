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

    private List<UsuarioDTO> converterListaUsuarioDTO(List<UsuarioModel> listaUsuarioModel) {
        return listaUsuarioModel.stream().map(UsuarioDTO::new).collect(Collectors.toList());
    }

}
