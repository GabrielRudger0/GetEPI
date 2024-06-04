package com.senai.GetEPI.Services;

import com.senai.GetEPI.DTOs.GerarMovimentacaoEntradaDTO;
import com.senai.GetEPI.DTOs.ViewMovimentacaoDto;
import com.senai.GetEPI.Dominios.TipoMovimentacao;
import com.senai.GetEPI.Models.EmprestimoModel;
import com.senai.GetEPI.Models.MovimentacaoModel;
import com.senai.GetEPI.Repositories.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovimentacaoService {

    @Autowired
    MovimentacaoRepository movimentacaoRepository;


    public List<ViewMovimentacaoDto> retornaListaEmprestimos() {
        return converterListaEmprestimo(movimentacaoRepository.findAll());
    }

    public String gerarMovimentacaoEntrada(GerarMovimentacaoEntradaDTO movimentacao){


        MovimentacaoModel registro = new MovimentacaoModel();

        registro.setDataMovimentacao(new Date());
        registro.setQuantidade(movimentacao.getQuantidade());
        registro.setEmprestimoModel(null);
        registro.setTipoMovimentacao(TipoMovimentacao.ENTRADA);
        movimentacaoRepository.save(registro);

        return "";
    }

    private List<ViewMovimentacaoDto> converterListaEmprestimo(List<MovimentacaoModel> movimentacaoModels) {
        return movimentacaoModels.stream().map(ViewMovimentacaoDto::new).collect(Collectors.toList());
    }



}


