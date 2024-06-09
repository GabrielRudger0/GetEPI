package com.senai.GetEPI.Services;

import com.senai.GetEPI.DTOs.EpiDto;
import com.senai.GetEPI.DTOs.GerarMovimentacaoEntradaDTO;
import com.senai.GetEPI.DTOs.UsuarioDTO;
import com.senai.GetEPI.DTOs.ViewMovimentacaoDto;
import com.senai.GetEPI.Dominios.TipoMovimentacao;
import com.senai.GetEPI.Models.EmprestimoModel;
import com.senai.GetEPI.Models.MovimentacaoModel;
import com.senai.GetEPI.Models.UsuarioModel;
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

    @Autowired
    EpiService epiService;


    public List<ViewMovimentacaoDto> retornaListaEmprestimos() {
        return converterListaEmprestimo(movimentacaoRepository.findAll());
    }

    public String gerarMovimentacao(EmprestimoModel emprestimo,
                                    Long quantidadeMovimentacao, TipoMovimentacao tipoMovimentacao){

        if(emprestimo.getEpi().getQuatidadeEpi() == 0 && tipoMovimentacao == TipoMovimentacao.SAIDA) {
            return "Equipamento está com o estoque vazio!";
        }

        MovimentacaoModel registro = new MovimentacaoModel();

        registro.setDataMovimentacao(new Date());
        registro.setQuantidade(quantidadeMovimentacao);
        registro.setEmprestimoModel(emprestimo);
        registro.setTipoMovimentacao(tipoMovimentacao);
        movimentacaoRepository.save(registro);

        epiService.alterarEstoque(quantidadeMovimentacao, new EpiDto(emprestimo.getEpi()));
        return "";
    }

    public String gerarMovimentacaoInterna(EpiDto epi,
                                           Long quantidadeMovimentacao, TipoMovimentacao tipoMovimentacao){

        MovimentacaoModel registro = new MovimentacaoModel();

        registro.setDataMovimentacao(new Date());
        registro.setQuantidade(quantidadeMovimentacao);
        registro.setEmprestimoModel(null);
        registro.setTipoMovimentacao(tipoMovimentacao);
        movimentacaoRepository.save(registro);

        epiService.alterarEstoque(quantidadeMovimentacao, epi);
        return "";
    }

    private List<ViewMovimentacaoDto> converterListaEmprestimo(List<MovimentacaoModel> movimentacaoModels) {
        return movimentacaoModels.stream().map(ViewMovimentacaoDto::new).collect(Collectors.toList());
    }


    public void excluirMovimentacaoPorEmprestimo(Long emprestimoId) {
        movimentacaoRepository.deleteMovimentacaoPorEmprestimo(emprestimoId);
    }

    public ViewMovimentacaoDto retornaMovimentacao(Long id) {
        MovimentacaoModel movimentacao = movimentacaoRepository.findById(id).get();

        return new ViewMovimentacaoDto(movimentacao);

    }


}


