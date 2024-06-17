package com.senai.GetEPI.Controllers.EPI;

import com.senai.GetEPI.DTOs.ColaboradorDto;
import com.senai.GetEPI.DTOs.EpiDto;
import com.senai.GetEPI.DTOs.FuncaoDto;
import com.senai.GetEPI.DTOs.TipoEquipamentoDTO;
import com.senai.GetEPI.Models.EpiModel;
import com.senai.GetEPI.OutrosObjetos.ApocalipseGetEPI;
import com.senai.GetEPI.OutrosObjetos.ErroGetEPI;
import com.senai.GetEPI.Services.AlocacaoService;
import com.senai.GetEPI.Services.EpiService;
import com.senai.GetEPI.Services.TipoEquipamentoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/listaEPI")
public class ListarEpiController {

    @Autowired
    EpiService epiService;

    @Autowired
    private TipoEquipamentoService tipoEquipamentoService;

    @Autowired
    ApocalipseGetEPI apocalipseGetEPI;

    @Autowired
    AlocacaoService alocacaoService;

    @GetMapping()
    public String exibirListaEpi(Model model,EpiDto epiDto, HttpServletRequest request, HttpServletResponse response) {

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

            List<EpiDto> listaEpi = epiService.retornaListaEpiDTO();

            boolean nenhumRegistro = false;
            if(listaEpi.isEmpty()) {
                nenhumRegistro = true;
            }
            model.addAttribute("nenhumRegistro", nenhumRegistro);

            model.addAttribute("epis",epiService.obterListaEpi());
            model.addAttribute("tipoepi", tipoEquipamentoService.obterListaTipoEquipamento());
            model.addAttribute("buscaEPIDto", new EpiDto());

        } catch (Exception e) {
            model.addAttribute("erro", true);
            model.addAttribute("tituloMensagemErro", apocalipseGetEPI.refatoraMensagem(e.getClass().getName(), e.toString()));
            model.addAttribute("stacktraceMensagem", e.toString());

            model.addAttribute("epis",new ArrayList<EpiModel>());
            model.addAttribute("tipoepi", new ArrayList<TipoEquipamentoDTO>());
            model.addAttribute("buscaEPIDto", new EpiDto());
            model.addAttribute("nenhumRegistro", true);

        }
        return "listaEPI";

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirEpi(@PathVariable Long id, HttpServletRequest request){

        String mensagemErro = epiService.excluirEpi(id, request);
        if (mensagemErro.isEmpty()){
            return ResponseEntity.ok("EPI excluído com sucesso.");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensagemErro);
    }

    @PostMapping
    public String buscarRegistros(@ModelAttribute("buscaEPIDto") EpiDto registroBuscado, Model model) {

        try {
            List<EpiDto> listaRegistrosEncontrados = epiService.buscarEPIPorNome(registroBuscado);

            boolean nenhumRegistro = false;
            if(listaRegistrosEncontrados.isEmpty()) {
                nenhumRegistro = true;
            }

            model.addAttribute("epis", listaRegistrosEncontrados);
            model.addAttribute("nenhumRegistro", nenhumRegistro);

        } catch (Exception e) {
            model.addAttribute("erro", true);
            model.addAttribute("tituloMensagemErro", apocalipseGetEPI.refatoraMensagem(e.getClass().getName(), e.toString()));
            model.addAttribute("stacktraceMensagem", e.toString());

            model.addAttribute("epis",new ArrayList<EpiModel>());
            model.addAttribute("tipoepi", new ArrayList<TipoEquipamentoDTO>());
            model.addAttribute("buscaEPIDto", new EpiDto());
            model.addAttribute("nenhumRegistro", true);

        }
        return "listaEPI";
    }
}
