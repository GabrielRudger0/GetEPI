package com.senai.GetEPI.Controllers.Funcao;

import com.senai.GetEPI.DTOs.ColaboradorDto;
import com.senai.GetEPI.DTOs.FuncaoDto;
import com.senai.GetEPI.Services.ColaboradorService;
import com.senai.GetEPI.Services.FuncaoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/listafuncao")
public class ListaFuncaoController {

    @Autowired
    FuncaoService funcaoService;

    @GetMapping()
    public String exibirListaFuncao(Model model, HttpServletRequest request, FuncaoDto funcaoDto) {

        List<FuncaoDto> listaFuncaoDto = funcaoService.retornaListaFuncaoDTO();

        boolean nenhumRegistro = false;
        if(listaFuncaoDto.isEmpty()) {
            nenhumRegistro = true;
        }
        model.addAttribute("nenhumRegistro", nenhumRegistro);

        model.addAttribute("funcoes", funcaoService.retornaListaFuncaoDTO());
        model.addAttribute("buscaFuncaoDTO", new FuncaoDto());

        return "listafuncao";


    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirFuncao(@PathVariable Long id){

        boolean sucesso = funcaoService.excluirFuncao(id);
        if (sucesso){
            return ResponseEntity.ok("Função excluída com sucesso.");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao excluir função.");
    }
}
