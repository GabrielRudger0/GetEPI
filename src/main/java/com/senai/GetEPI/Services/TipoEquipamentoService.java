package com.senai.GetEPI.Services;

import com.senai.GetEPI.DTOs.EpiDto;
import com.senai.GetEPI.DTOs.FuncaoDto;
import com.senai.GetEPI.DTOs.TipoEquipamentoDTO;
import com.senai.GetEPI.DTOs.UsuarioDTO;
import com.senai.GetEPI.Models.*;
import com.senai.GetEPI.Repositories.TipoEquipamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TipoEquipamentoService {

    @Autowired
    TipoEquipamentoRepository tipoEquipamentoRepository;

    public List<TipoEquipamentoDTO> retornaListaTipoEquipamento() {
        return converterListaEquipamentoDTO(tipoEquipamentoRepository.findAll());
    }

    private List<TipoEquipamentoDTO> converterListaEquipamentoDTO(List<TipoEquipamentoModel> listaModel) {
        return listaModel.stream().map(TipoEquipamentoDTO::new).collect(Collectors.toList());
    }

    public List<TipoEquipamentoDTO> obterListaTipoEquipamento() {

        List<TipoEquipamentoModel> listaTipoEquipamentoModel = tipoEquipamentoRepository.findAll();

        List<TipoEquipamentoDTO> listaTipoEquipamento = new ArrayList<>();

        for (TipoEquipamentoModel tpEquipamento : listaTipoEquipamentoModel) {

            TipoEquipamentoDTO tipoEquipamentoDTO = new TipoEquipamentoDTO();
            tipoEquipamentoDTO.setId(tpEquipamento.getId());
            tipoEquipamentoDTO.setDescricao(tpEquipamento.getDescricao());

            listaTipoEquipamento.add(tipoEquipamentoDTO);
        }

        return listaTipoEquipamento;

    }

    public String cadastraTipoEquipamento(TipoEquipamentoDTO tipoEquipamento) {

        if (tipoEquipamentoRepository.existsByDescricao(tipoEquipamento.getDescricao().toUpperCase())) {
            return "Já existe um tipo de equipamento cadastrado com essa descrição!";
        }

        tipoEquipamentoRepository.save(new TipoEquipamentoModel(tipoEquipamento));
        return "";
    }

    public TipoEquipamentoDTO retonaTipoEquipamentoDTO(Long id) {
        return converteTipoEquipamentoDTO(tipoEquipamentoRepository.findById(id).get());
    }

    private TipoEquipamentoDTO converteTipoEquipamentoDTO(TipoEquipamentoModel tipoEquipamentoModel) {
        return new TipoEquipamentoDTO(tipoEquipamentoModel);
    }

    public String atualizarTipoEquipamento(TipoEquipamentoDTO tipoEquipamento) {
        Optional<TipoEquipamentoModel> tipoEquipamentoModel = tipoEquipamentoRepository.findByDescricao(tipoEquipamento.getDescricao().trim().toUpperCase());

        if (tipoEquipamentoModel.isPresent()) {
            if (tipoEquipamentoModel.get().getId() != tipoEquipamento.getId()) {
                return "Já existe um tipo de equipamento cadastrado com essa descrição!";
            }
        }

        tipoEquipamentoRepository.save(new TipoEquipamentoModel(tipoEquipamento));
        return "";
    }

    public String excluirColaborador(Long id) {
        try {
            Optional<TipoEquipamentoModel> tipoEquipamentoModel = tipoEquipamentoRepository.findById(id);

            tipoEquipamentoRepository.delete(tipoEquipamentoModel.get());
            return "";
        } catch (Exception e) {
            return e.toString();
        }

    }

    public List<TipoEquipamentoDTO> buscarTipoEquipamentoPorDescricao(TipoEquipamentoDTO tipoEquipamento) {
        List<TipoEquipamentoModel> tiposEquipamentoEncontrados = tipoEquipamentoRepository.findByDescricaoContaining(tipoEquipamento.getDescricao());
        return converterListaEquipamentoDTO(tiposEquipamentoEncontrados);
    }

}
