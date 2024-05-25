package com.senai.GetEPI.Controllers.TipoEquipamento;

import com.senai.GetEPI.DTOs.TipoEquipamentoDTO;
import com.senai.GetEPI.Services.TipoEquipamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/listatipoequipamento")
public class ListaTipoEquipamento {

    @Autowired
    TipoEquipamentoService tipoEquipamentoService;

    @GetMapping()
    public String exibeListaTipoEquipamento(Model model) {

        List<TipoEquipamentoDTO> listaTiposEquipamento = tipoEquipamentoService.retornaListaTipoEquipamento();

        boolean nenhumRegistro = false;
        if(listaTiposEquipamento.isEmpty()) {
            nenhumRegistro = true;
        }

        model.addAttribute("tiposEquipamento", listaTiposEquipamento);
        model.addAttribute("nenhumRegistro", nenhumRegistro);
        return "listatipoequipamento";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirTipoEquipamento(@PathVariable Long id){

        boolean sucesso = tipoEquipamentoService.excluirColaborador(id);
        if (sucesso){
            return ResponseEntity.ok("Tipo de equipamento excluído com sucesso.");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao excluir tipo de equipamento.");
    }
}