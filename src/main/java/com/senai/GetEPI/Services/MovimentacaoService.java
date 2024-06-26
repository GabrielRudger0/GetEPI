package com.senai.GetEPI.Services;

import com.senai.GetEPI.DTOs.EpiDto;
import com.senai.GetEPI.DTOs.GerarMovimentacaoEntradaDTO;
import com.senai.GetEPI.DTOs.UsuarioDTO;
import com.senai.GetEPI.DTOs.ViewMovimentacaoDto;
import com.senai.GetEPI.Dominios.OrigemMovimentacao;
import com.senai.GetEPI.Dominios.TipoMovimentacao;
import com.senai.GetEPI.Models.EmprestimoModel;
import com.senai.GetEPI.Models.EpiModel;
import com.senai.GetEPI.Models.MovimentacaoModel;
import com.senai.GetEPI.Models.UsuarioModel;
import com.senai.GetEPI.Repositories.EpiRepository;
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
    EpiRepository epiRepository;


    public List<ViewMovimentacaoDto> retornaListaEmprestimos() {
        return converterListaEmprestimo(movimentacaoRepository.findAll());
    }

    public String gerarMovimentacao(EmprestimoModel emprestimo,
                                    Long quantidadeMovimentacao, TipoMovimentacao tipoMovimentacao, OrigemMovimentacao origemMovimentacao){

        if(emprestimo.getEpi().getQuatidadeEpi() == 0 && tipoMovimentacao == TipoMovimentacao.SAIDA) {
            return "Equipamento está com o estoque vazio!";
        }

        MovimentacaoModel registro = new MovimentacaoModel();

        registro.setDataMovimentacao(new Date());
        registro.setQuantidade(quantidadeMovimentacao);
        registro.setEmprestimoModel(emprestimo);
        registro.setOrigem(origemMovimentacao);
        registro.setTipoMovimentacao(tipoMovimentacao);
        movimentacaoRepository.save(registro);

        alterarEstoque(quantidadeMovimentacao, new EpiDto(emprestimo.getEpi()));
        return "";
    }

    public String gerarMovimentacaoInterna(EmprestimoModel emprestimoInterno, Long quantidadeMovimentacao,
                                           TipoMovimentacao tipoMovimentacao, OrigemMovimentacao origem){

        MovimentacaoModel registro = new MovimentacaoModel();

        registro.setDataMovimentacao(new Date());
        registro.setQuantidade(quantidadeMovimentacao);
        registro.setEmprestimoModel(emprestimoInterno);
        registro.setTipoMovimentacao(tipoMovimentacao);
        registro.setOrigem(origem);
        movimentacaoRepository.save(registro);

        if (origem != OrigemMovimentacao.REGISTRO_EQUIPAMENTO) {
            alterarEstoque(quantidadeMovimentacao, new EpiDto(emprestimoInterno.getEpi()));
        }

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
        return new ViewMovimentacaoDto(movimentacaoRepository.findById(id).get());
    }

    private void alterarEstoque(Long movimentacaoQuantidade, EpiDto epi) {

        Long estoqueFinal = epi.getQuatidadeEpi() + movimentacaoQuantidade;
        epi.setQuatidadeEpi(estoqueFinal);

        epiRepository.save(new EpiModel(epi, epi.getTipoEquipamento()));
    }

}


