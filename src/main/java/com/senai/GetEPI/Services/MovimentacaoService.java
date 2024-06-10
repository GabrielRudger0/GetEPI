package com.senai.GetEPI.Services;

import com.senai.GetEPI.DTOs.EpiDto;
import com.senai.GetEPI.DTOs.GerarMovimentacaoEntradaDTO;
import com.senai.GetEPI.DTOs.ViewMovimentacaoDto;
import com.senai.GetEPI.Dominios.TipoMovimentacao;
import com.senai.GetEPI.Models.EmprestimoModel;
import com.senai.GetEPI.Models.MovimentacaoModel;
import com.senai.GetEPI.Repositories.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        Stream<MovimentacaoModel> listaStream = movimentacaoModels.stream().sorted(Comparator.comparing(MovimentacaoModel::getDataMovimentacao).reversed());

        return listaStream.map(ViewMovimentacaoDto::new).collect(Collectors.toList());
    }

    public List<ViewMovimentacaoDto> paginarListaEmprestimo(List<ViewMovimentacaoDto> movimentacoes, int pagina) {
        int tamanhoPagina = 7;
        int inicio = pagina - 1;
        inicio = inicio * tamanhoPagina;

        List<ViewMovimentacaoDto> list = movimentacoes.stream().skip(inicio).limit(tamanhoPagina).collect(Collectors.toList());
        return list;

    }

    public int calcularNumeroMaximoPaginas(List<ViewMovimentacaoDto> lista, int tamanhoPagina) {
        int tamanhoLista = lista.size();
        int numeroMaximoPaginas = (int) Math.ceil((double) tamanhoLista / tamanhoPagina);

        return numeroMaximoPaginas;
    }


    public void excluirMovimentacaoPorEmprestimo(Long emprestimoId) {
        movimentacaoRepository.deleteMovimentacaoPorEmprestimo(emprestimoId);
    }

    public ViewMovimentacaoDto buscaMovimentacaoPorId(Long id) {
        return new ViewMovimentacaoDto(movimentacaoRepository.findById(id).get(), true);
    }



}


