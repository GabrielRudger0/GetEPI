package com.senai.GetEPI.Controllers.Emprestimo;

import com.senai.GetEPI.DTOs.BuscaDTO;
import com.senai.GetEPI.DTOs.ColaboradorDto;
import com.senai.GetEPI.DTOs.EmprestimoDTO;
import com.senai.GetEPI.DTOs.ViewEmprestimoDTO;
import com.senai.GetEPI.Services.EmprestimoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.View;
import java.util.List;

@Controller
@RequestMapping("/listaemprestimo")
public class ListaEmprestimoController {

    @Autowired
    EmprestimoService emprestimoService;

    @GetMapping()
    public String exibeListaEmprestimo(Model model) {

        //List<EmprestimoDTO> listaEmprestimos = emprestimoService.retornaListaEmprestimos();

        List<ViewEmprestimoDTO> listaEmprestimosFormatada = emprestimoService.retornaListaEmprestimosView() ;
        boolean nenhumRegistro = false;
        if(listaEmprestimosFormatada.isEmpty()) {
            nenhumRegistro = true;
        }

        model.addAttribute("buscaDTO", new BuscaDTO());
        model.addAttribute("emprestimos", listaEmprestimosFormatada);
        model.addAttribute("nenhumRegistro", nenhumRegistro);

        return "listaemprestimo";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirEmprestimo(@PathVariable Long id){

        boolean sucesso = emprestimoService.excluirEmprestimo(id);
        if (sucesso){
            return ResponseEntity.ok("Empréstimo excluído com sucesso.");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao excluir Empréstimo.");
    }

    @PostMapping
    public String buscarRegistros(@ModelAttribute("buscaDTO") BuscaDTO registroBuscado, Model model) {
        List<ViewEmprestimoDTO> listaRegistrosEncontrados = emprestimoService.buscarEmprestimoPorColaborador(registroBuscado.getNomeColaborador());

        boolean nenhumRegistro = false;
        if(listaRegistrosEncontrados.isEmpty()) {
            nenhumRegistro = true;
        }

        model.addAttribute("emprestimos", listaRegistrosEncontrados);
        model.addAttribute("nenhumRegistro", nenhumRegistro);
        return "listaemprestimo";
    }

}
