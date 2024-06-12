package com.senai.GetEPI.Services;

import com.senai.GetEPI.DTOs.ColaboradorDto;
import com.senai.GetEPI.DTOs.LoginDTO;
import com.senai.GetEPI.DTOs.UsuarioDTO;
import com.senai.GetEPI.Models.UsuarioModel;
import com.senai.GetEPI.Repositories.UsuarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ColaboradorService colaboradorService;

    public List<UsuarioDTO> retornaListaUsuarioDTO() {
        return converterListaUsuarioDTO(usuarioRepository.findAll());
    }

    public UsuarioDTO retornaUsuarioDTO(Long id) {
        UsuarioModel usuarioBD = usuarioRepository.findById(id).get();

        return new UsuarioDTO(usuarioBD);

    }

    public String inserirUsuario(UsuarioDTO usuario) {
        if (!mensagemErroUsuario(usuario).isEmpty()) {
            return mensagemErroUsuario(usuario);
        }
        UsuarioModel usuarioNovo = new UsuarioModel(usuario);
        usuarioRepository.save(usuarioNovo);

        colaboradorService.criarColaboradorUsuario(usuarioNovo);
        return "";

    }

    public String atualizaUsuario(UsuarioDTO usuario) {
        Optional<UsuarioModel> usuarioBD = usuarioRepository.findById(usuario.getId());

        if (!usuarioBD.get().getEmail().equals(usuario.getEmail())) {
            if (usuarioRepository.existsByEmail(usuario.getEmail())) {
                return "Já existe cadastro com estas credenciais!";
            }
        }



        usuarioRepository.save(new UsuarioModel(usuario));
        return "";
    }

    public String excluirUsuario(Long id, HttpServletRequest request){
        try {
            Optional<UsuarioModel> optionalUsuario = usuarioRepository.findById(id);

            if (optionalUsuario.isPresent()) {
                ColaboradorDto colaboradorVinculado = colaboradorService.buscarColaboradorPorUsuario(optionalUsuario.get());
                if (colaboradorVinculado != null) {
                    String msgErro = colaboradorService.excluirColaborador(colaboradorVinculado.getId(), request, true);
                    if (!msgErro.isEmpty()) {
                        return "Usuário possuí um colaborador vinculado: " + msgErro;
                    }
                }

            }

            usuarioRepository.delete(optionalUsuario.get());

            return "";

        }catch (Exception e) {
            return e.toString();
        }

    }

    public List<UsuarioDTO> buscarUsuarioPorNome(UsuarioDTO usuario) {
        List<UsuarioModel> usuariosEncontrados = usuarioRepository.findByNomeContaining(usuario.getNome());
        return converterListaUsuarioDTO(usuariosEncontrados);
    }

    public boolean loginValido(LoginDTO login) {
        Optional<UsuarioModel> usuario = usuarioRepository.findByEmail(login.getEmail());

        if (usuario.isPresent()) {
            if (usuario.get().getSenha().equals(login.getSenha())) {
                return true;
            }
        }

        return false;
    }

    private String mensagemErroUsuario(UsuarioDTO usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            return "Já existe cadastro com estas credenciais!";
        }

        if (usuario.getSenha().length() < 5) {
            return "A senha deve ter no mínimo 5 caracteres!";
        }
        return "";
    }

    private List<UsuarioDTO> converterListaUsuarioDTO(List<UsuarioModel> listaUsuarioModel) {
        return listaUsuarioModel.stream().map(UsuarioDTO::new).collect(Collectors.toList());
    }

}
