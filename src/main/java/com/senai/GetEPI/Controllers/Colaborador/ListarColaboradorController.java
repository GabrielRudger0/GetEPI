package com.senai.GetEPI.Controllers.Colaborador;

import com.senai.GetEPI.DTOs.ColaboradorDto;
import com.senai.GetEPI.DTOs.UsuarioDTO;
import com.senai.GetEPI.Services.ColaboradorService;
import com.senai.GetEPI.Services.FuncaoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/listacolaboradores")
public class ListarColaboradorController {

    @Autowired
    ColaboradorService colaboradorService;

    @Autowired
    FuncaoService funcaoService;

    @GetMapping()
    public String exibirListaColaboradores(Model model, HttpServletRequest request, ColaboradorDto colaboladorDto) {

        List<ColaboradorDto> listaColaborador = colaboradorService.retornaListaColaboradorDTO();

        boolean nenhumRegistro = false;
        if(listaColaborador.isEmpty()) {
            nenhumRegistro = true;
        }
        model.addAttribute("nenhumColaborador", nenhumRegistro);

        model.addAttribute("colaboradores", listaColaborador);
        model.addAttribute("funcao", funcaoService.obterListaFuncao());
        model.addAttribute("buscaColaboradorDTO", new ColaboradorDto());

        return "listacolaborador";

    }
        @DeleteMapping("/{id}")
        public ResponseEntity<String> excluirColaborador(@PathVariable Long id){

            boolean sucesso = colaboradorService.excluirColaborador(id);
            if (sucesso){
                return ResponseEntity.ok("Colaborador excluído com sucesso.");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao excluir colaborador.");
        }


    }

