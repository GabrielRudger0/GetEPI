package com.senai.GetEPI.Controllers.Devolucao;

import com.senai.GetEPI.DTOs.BuscaDTO;
import com.senai.GetEPI.DTOs.EmprestimoDTO;
import com.senai.GetEPI.DTOs.ViewEmprestimoDTO;
import com.senai.GetEPI.OutrosObjetos.ApocalipseGetEPI;
import com.senai.GetEPI.OutrosObjetos.ErroGetEPI;
import com.senai.GetEPI.Services.EmprestimoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/listadevolucao")
public class ListarDevolucaoController {

    @Autowired
    EmprestimoService emprestimoService;

    @Autowired
    ApocalipseGetEPI apocalipseGetEPI;


    @GetMapping()
    public String exibeListaDevolucao(Model model, HttpServletRequest request) {

        try {

            ErroGetEPI erro = apocalipseGetEPI.retornarErro(request);
            if (erro.getExibeErro()) {
                model.addAttribute("erro", true);
                model.addAttribute("tituloMensagemErro", erro.getMensagemErro());
                model.addAttribute("stacktraceMensagem", erro.getStackTrace());
            }

            List<ViewEmprestimoDTO> emprestimos = emprestimoService.retornaListaEmprestimosNaoDevolvidos();

            boolean nenhumRegistro = false;
            if(emprestimos.isEmpty()) {
                nenhumRegistro = true;
            }
            model.addAttribute("nenhumRegistro", nenhumRegistro);
            model.addAttribute("emprestimosNaoDevolvidos", emprestimos);
            model.addAttribute("buscaDTO", new BuscaDTO());

        } catch (Exception e) {
            model.addAttribute("erro", true);
            model.addAttribute("tituloMensagemErro", apocalipseGetEPI.refatoraMensagem(e.getClass().getName(), e.toString()));
            model.addAttribute("stacktraceMensagem", e.toString());

            model.addAttribute("buscaDTO", new BuscaDTO());
            model.addAttribute("emprestimosNaoDevolvidos", new ArrayList<ViewEmprestimoDTO>());
            model.addAttribute("nenhumRegistro", true);

        }

        return "listadevolucao";
    }

    @PostMapping
    public String buscarRegistros(@ModelAttribute("buscaDTO") BuscaDTO registroBuscado, Model model) {

        try {
            List<ViewEmprestimoDTO> listaRegistrosEncontrados = emprestimoService.buscarPendentesDevolucaoPorColaborador(registroBuscado.getNomeColaborador());

            boolean nenhumRegistro = false;
            if(listaRegistrosEncontrados.isEmpty()) {
                nenhumRegistro = true;
            }

            model.addAttribute("emprestimosNaoDevolvidos", listaRegistrosEncontrados);
            model.addAttribute("nenhumRegistro", nenhumRegistro);
            return "listadevolucao";

        } catch (Exception e) {
            model.addAttribute("erro", true);
            model.addAttribute("tituloMensagemErro", apocalipseGetEPI.refatoraMensagem(e.getClass().getName(), e.toString()));
            model.addAttribute("stacktraceMensagem", e.toString());

            model.addAttribute("buscaDTO", new BuscaDTO());
            model.addAttribute("emprestimosNaoDevolvidos", new ArrayList<ViewEmprestimoDTO>());
            model.addAttribute("nenhumRegistro", true);
            return "listadevolucao";
        }

    }
}
