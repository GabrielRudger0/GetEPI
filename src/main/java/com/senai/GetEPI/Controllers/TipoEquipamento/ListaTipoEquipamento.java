package com.senai.GetEPI.Controllers.TipoEquipamento;

import com.senai.GetEPI.DTOs.ColaboradorDto;
import com.senai.GetEPI.DTOs.FuncaoDto;
import com.senai.GetEPI.DTOs.TipoEquipamentoDTO;
import com.senai.GetEPI.DTOs.UsuarioDTO;
import com.senai.GetEPI.OutrosObjetos.ApocalipseGetEPI;
import com.senai.GetEPI.OutrosObjetos.ErroGetEPI;
import com.senai.GetEPI.Services.TipoEquipamentoService;
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
@RequestMapping("/listatipoequipamento")
public class ListaTipoEquipamento {

    @Autowired
    TipoEquipamentoService tipoEquipamentoService;

    @Autowired
    ApocalipseGetEPI apocalipseGetEPI;

    @GetMapping()
    public String exibeListaTipoEquipamento(Model model, HttpServletRequest request) {

        try {

            ErroGetEPI erro = apocalipseGetEPI.retornarErro(request);
            if (erro.getExibeErro()) {
                model.addAttribute("erro", true);
                model.addAttribute("tituloMensagemErro", erro.getMensagemErro());
                model.addAttribute("stacktraceMensagem", erro.getStackTrace());
            }

            List<TipoEquipamentoDTO> listaTiposEquipamento = tipoEquipamentoService.retornaListaTipoEquipamento();

            boolean nenhumRegistro = false;
            if(listaTiposEquipamento.isEmpty()) {
                nenhumRegistro = true;
            }

            model.addAttribute("buscaTipoEquipamentoDTO", new TipoEquipamentoDTO());
            model.addAttribute("tiposEquipamento", listaTiposEquipamento);
            model.addAttribute("nenhumRegistro", nenhumRegistro);

        } catch (Exception e) {
            model.addAttribute("erro", true);
            model.addAttribute("tituloMensagemErro", apocalipseGetEPI.refatoraMensagem(e.getClass().getName(), e.toString()));
            model.addAttribute("stacktraceMensagem", e.toString());

            model.addAttribute("buscaTipoEquipamentoDTO", new TipoEquipamentoDTO());
            model.addAttribute("tiposEquipamento", new ArrayList<TipoEquipamentoDTO>());
            model.addAttribute("nenhumRegistro", true);

        }

        return "listatipoequipamento";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirTipoEquipamento(@PathVariable Long id){

        String mensagemErro = tipoEquipamentoService.excluirColaborador(id);
        if (mensagemErro.isEmpty()){
            return ResponseEntity.ok("Tipo de equipamento excluído com sucesso.");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensagemErro);
    }

    @PostMapping()
    public String buscarTipoEquipamento(@ModelAttribute("buscaTipoEquipamentoDTO") TipoEquipamentoDTO tipoEquipamento, Model model) {

        try {
            List<TipoEquipamentoDTO> listaUsuariosEncontrados = tipoEquipamentoService.buscarTipoEquipamentoPorDescricao(tipoEquipamento);

            boolean nenhumRegistro = false;
            if(listaUsuariosEncontrados.isEmpty()) {
                nenhumRegistro = true;
            }

            model.addAttribute("tiposEquipamento", listaUsuariosEncontrados);
            model.addAttribute("nenhumRegistro", nenhumRegistro);

        } catch (Exception e) {
            model.addAttribute("erro", true);
            model.addAttribute("tituloMensagemErro", apocalipseGetEPI.refatoraMensagem(e.getClass().getName(), e.toString()));
            model.addAttribute("stacktraceMensagem", e.toString());

            model.addAttribute("buscaTipoEquipamentoDTO", new TipoEquipamentoDTO());
            model.addAttribute("tiposEquipamento", new ArrayList<TipoEquipamentoDTO>());
            model.addAttribute("nenhumRegistro", true);

        }

        return "listatipoequipamento";
    }
}
