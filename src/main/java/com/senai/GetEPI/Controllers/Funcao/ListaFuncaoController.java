package com.senai.GetEPI.Controllers.Funcao;

import com.senai.GetEPI.DTOs.ColaboradorDto;
import com.senai.GetEPI.DTOs.FuncaoDto;
import com.senai.GetEPI.OutrosObjetos.ApocalipseGetEPI;
import com.senai.GetEPI.OutrosObjetos.ErroGetEPI;
import com.senai.GetEPI.Services.ColaboradorService;
import com.senai.GetEPI.Services.FuncaoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/listafuncao")
public class ListaFuncaoController {

    @Autowired
    FuncaoService funcaoService;

    @Autowired
    ApocalipseGetEPI apocalipseGetEPI;

    @GetMapping()
    public String exibirListaFuncao(Model model, HttpServletRequest request, FuncaoDto funcaoDto) {
        try{

            ErroGetEPI erro = apocalipseGetEPI.retornarErro(request);
            if (erro.getExibeErro()) {
                model.addAttribute("erro", true);
                model.addAttribute("tituloMensagemErro", erro.getMensagemErro());
                model.addAttribute("stacktraceMensagem", erro.getStackTrace());
            }

            List<FuncaoDto> listaFuncaoDto = funcaoService.retornaListaFuncaoDTO();

            boolean nenhumRegistro = false;
            if(listaFuncaoDto.isEmpty()) {
                nenhumRegistro = true;
            }
            model.addAttribute("nenhumRegistro", nenhumRegistro);

            model.addAttribute("funcoes", funcaoService.retornaListaFuncaoDTO());
            model.addAttribute("buscaFuncaoDTO", new FuncaoDto());


        } catch (Exception e) {
            model.addAttribute("erro", true);
            model.addAttribute("tituloMensagemErro", apocalipseGetEPI.refatoraMensagem(e.getClass().getName(), e.toString()));
            model.addAttribute("stacktraceMensagem", e.toString());

            model.addAttribute("funcoes", new ArrayList<FuncaoDto>());
            model.addAttribute("buscaFuncaoDTO", new ColaboradorDto());
            model.addAttribute("nenhumRegistro", true);

        }
        return "listafuncao";
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirFuncao(@PathVariable Long id){

        String mensagemErro = funcaoService.excluirFuncao(id);
        if (mensagemErro.isEmpty()){
            return ResponseEntity.ok("Função excluída com sucesso.");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensagemErro);
    }

    @PostMapping
    public String buscarRegistros(@ModelAttribute("buscaFuncaoDTO") FuncaoDto registroBuscado, Model model) {

        try {
            List<FuncaoDto> listaRegistrosEncontrados = funcaoService.buscarFuncaoPorDescricao(registroBuscado);

            boolean nenhumRegistro = false;
            if(listaRegistrosEncontrados.isEmpty()) {
                nenhumRegistro = true;
            }

            model.addAttribute("funcoes", listaRegistrosEncontrados);
            model.addAttribute("nenhumRegistro", nenhumRegistro);

        } catch (Exception e) {
            model.addAttribute("erro", true);
            model.addAttribute("tituloMensagemErro", apocalipseGetEPI.refatoraMensagem(e.getClass().getName(), e.toString()));
            model.addAttribute("stacktraceMensagem", e.toString());

            model.addAttribute("funcoes", new ArrayList<FuncaoDto>());
            model.addAttribute("buscaFuncaoDTO", new ColaboradorDto());
            model.addAttribute("nenhumRegistro", true);

        }

        return "listafuncao";
    }
}
