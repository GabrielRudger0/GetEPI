package com.senai.GetEPI.Controllers.Emprestimo;

import com.senai.GetEPI.DTOs.*;
import com.senai.GetEPI.Models.EmprestimoModel;
import com.senai.GetEPI.OutrosObjetos.ApocalipseGetEPI;
import com.senai.GetEPI.OutrosObjetos.ErroGetEPI;
import com.senai.GetEPI.Services.AlocacaoService;
import com.senai.GetEPI.Services.EmprestimoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.View;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/listaemprestimo")
public class ListaEmprestimoController {

    @Autowired
    EmprestimoService emprestimoService;

    @Autowired
    ApocalipseGetEPI apocalipseGetEPI;

    @Autowired
    AlocacaoService alocacaoService;

    @GetMapping()
    public String exibeListaEmprestimo(Model model, HttpServletRequest request, HttpServletResponse response) {

        try {
            if (!alocacaoService.validaSessao(request).isEmpty()) {
                return alocacaoService.validaSessao(request);
            }
            CacheControl nocache = CacheControl.noStore().mustRevalidate();
            response.setHeader("Cache-Control", nocache.getHeaderValue());

            ErroGetEPI erro = apocalipseGetEPI.retornarErro(request);
            if (erro.getExibeErro()) {
                model.addAttribute("erro", true);
                model.addAttribute("tituloMensagemErro", erro.getMensagemErro());
                model.addAttribute("stacktraceMensagem", erro.getStackTrace());
            }

            List<ViewEmprestimoDTO> listaEmprestimosFormatada = emprestimoService.retornaListaEmprestimosView() ;
            boolean nenhumRegistro = false;
            if(listaEmprestimosFormatada.isEmpty()) {
                nenhumRegistro = true;
            }

            model.addAttribute("buscaDTO", new BuscaDTO());
            model.addAttribute("emprestimos", listaEmprestimosFormatada);
            model.addAttribute("nenhumRegistro", nenhumRegistro);



        } catch (Exception e) {
            model.addAttribute("erro", true);
            model.addAttribute("tituloMensagemErro", apocalipseGetEPI.refatoraMensagem(e.getClass().getName(), e.toString()));
            model.addAttribute("stacktraceMensagem", e.toString());

            model.addAttribute("buscaDTO", new BuscaDTO());
            model.addAttribute("emprestimos", new ArrayList<ViewEmprestimoDTO>());
            model.addAttribute("nenhumRegistro", true);

        }

        return "listaemprestimo";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirEmprestimo(@PathVariable Long id, HttpServletRequest request){

        String mensagemErro = emprestimoService.excluirEmprestimo(id, request);
        if (mensagemErro.isEmpty()){
            return ResponseEntity.ok("Empréstimo excluído com sucesso.");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensagemErro);
    }

    @PostMapping
    public String buscarRegistros(@ModelAttribute("buscaDTO") BuscaDTO registroBuscado, Model model) {

        try {
            List<ViewEmprestimoDTO> listaRegistrosEncontrados = emprestimoService.buscarEmprestimoPorColaborador(registroBuscado.getNomeColaborador());

            boolean nenhumRegistro = false;
            if(listaRegistrosEncontrados.isEmpty()) {
                nenhumRegistro = true;
            }

            model.addAttribute("emprestimos", listaRegistrosEncontrados);
            model.addAttribute("nenhumRegistro", nenhumRegistro);

        } catch (Exception e) {
            model.addAttribute("erro", true);
            model.addAttribute("tituloMensagemErro", apocalipseGetEPI.refatoraMensagem(e.getClass().getName(), e.toString()));
            model.addAttribute("stacktraceMensagem", e.toString());

            model.addAttribute("buscaDTO", new BuscaDTO());
            model.addAttribute("emprestimos", new ArrayList<ViewEmprestimoDTO>());
            model.addAttribute("nenhumRegistro", true);

        }
        return "listaemprestimo";
    }

}
