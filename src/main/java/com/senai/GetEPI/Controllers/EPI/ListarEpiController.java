package com.senai.GetEPI.Controllers.EPI;

import com.senai.GetEPI.DTOs.ColaboradorDto;
import com.senai.GetEPI.DTOs.EpiDto;
import com.senai.GetEPI.Services.EpiService;
import com.senai.GetEPI.Services.TipoEquipamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/listaEPI")
public class ListarEpiController {

    @Autowired
    EpiService epiService;
    @Autowired
    private TipoEquipamentoService tipoEquipamentoService;

    @GetMapping()
    public String exibirListaEpi(Model model,EpiDto epiDto) {

        List<EpiDto> listaEpi = epiService.retornaListaEpiDTO();

        boolean nenhumRegistro = false;
        if(listaEpi.isEmpty()) {
            nenhumRegistro = true;
        }
        model.addAttribute("nenhumRegistro", nenhumRegistro);

        model.addAttribute("epis",epiService.obterListaEpi());
        model.addAttribute("tipoepi", tipoEquipamentoService.obterListaTipoEquipamento());
        model.addAttribute("buscaEPIDto", new EpiDto());

        return "listaEPI";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirEpi(@PathVariable Long id){

        boolean sucesso = epiService.excluirEpi(id);
        if (sucesso){
            return ResponseEntity.ok("EPI excluído com sucesso.");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao excluir EPI.");
    }

    @PostMapping
    public String buscarRegistros(@ModelAttribute("buscaEPIDto") EpiDto registroBuscado, Model model) {
        List<EpiDto> listaRegistrosEncontrados = epiService.buscarEPIPorNome(registroBuscado);

        boolean nenhumRegistro = false;
        if(listaRegistrosEncontrados.isEmpty()) {
            nenhumRegistro = true;
        }

        model.addAttribute("epis", listaRegistrosEncontrados);
        model.addAttribute("nenhumRegistro", nenhumRegistro);
        return "listaEPI";
    }
}
