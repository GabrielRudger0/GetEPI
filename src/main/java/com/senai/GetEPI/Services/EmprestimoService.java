package com.senai.GetEPI.Services;

import com.senai.GetEPI.DTOs.ColaboradorDto;
import com.senai.GetEPI.DTOs.EmprestimoDTO;
import com.senai.GetEPI.DTOs.ViewEmprestimoDTO;
import com.senai.GetEPI.Dominios.TipoMovimentacao;
import com.senai.GetEPI.Models.ColaboradorModel;
import com.senai.GetEPI.Models.EmprestimoModel;
import com.senai.GetEPI.Models.MovimentacaoModel;
import com.senai.GetEPI.Repositories.EmprestimoRepository;
import com.senai.GetEPI.Repositories.MovimentacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.View;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmprestimoService {

    @Autowired
    EmprestimoRepository emprestimoRepository;

    @Autowired
    MovimentacaoService movimentacaoService;

    public List<EmprestimoDTO> retornaListaEmprestimos() {
        return converterListaEmprestimo(emprestimoRepository.findAll());
    }

    private List<EmprestimoDTO> converterListaEmprestimo(List<EmprestimoModel> emprestimoModels) {
        return emprestimoModels.stream().map(EmprestimoDTO::new).collect(Collectors.toList());
    }

    public List<ViewEmprestimoDTO> retornaListaEmprestimosView() {
        return converterListaEmprestimoView(emprestimoRepository.findAll());
    }

    private List<ViewEmprestimoDTO> converterListaEmprestimoView(List<EmprestimoModel> emprestimoModels) {
        return emprestimoModels.stream().sorted(Comparator.comparing(EmprestimoModel::getEmissaoData).reversed()).map(ViewEmprestimoDTO::new).collect(Collectors.toList());
    }

    public String cadastrarEmprestimo(EmprestimoDTO dadosEmprestimo) {

       // Date dataPadrao = new Date(1, 0, 1);

        EmprestimoModel novoEmprestimo = new EmprestimoModel();
        novoEmprestimo.setColaborador(dadosEmprestimo.getColaborador());
        novoEmprestimo.setEpi(dadosEmprestimo.getEpi());
        novoEmprestimo.setEmissaoData(new Date());
        novoEmprestimo.setDevolucaoData(null);

        emprestimoRepository.save(novoEmprestimo);

        movimentacaoService.gerarMovimentacao(novoEmprestimo, -1l, TipoMovimentacao.SAIDA);
        return "";
    }

    public ViewEmprestimoDTO retonarViewEmprestimoDTO(Long id) {
        return new ViewEmprestimoDTO(emprestimoRepository.findById(id).get());
    }

    public boolean excluirEmprestimo(Long id){
        Optional<EmprestimoModel> emprestimo = emprestimoRepository.findById(id);
        if (!emprestimo.isPresent()){
            return false;
        }

        movimentacaoService.excluirMovimentacaoPorEmprestimo(id);
        emprestimoRepository.delete(emprestimo.get());
        movimentacaoService.gerarMovimentacao(new EmprestimoModel(), 1l, TipoMovimentacao.ENTRADA);

        return true;

    }

    public List<ViewEmprestimoDTO> retornaListaEmprestimosNaoDevolvidos() {
        return converterListaEmprestimoViewNoDevolucao(emprestimoRepository.findAllWhereEmprestimoDevolucaoIsNull());
    }

    private List<ViewEmprestimoDTO> converterListaEmprestimoViewNoDevolucao(List<EmprestimoModel> emprestimoModels) {
        return emprestimoModels.stream().sorted(Comparator.comparing(EmprestimoModel::getEmissaoData)).map(ViewEmprestimoDTO::new).collect(Collectors.toList());
    }

    public void registrarDevolucao(Long id) {
        EmprestimoModel emprestimoBD = emprestimoRepository.findById(id).get();
        emprestimoBD.setDevolucaoData(new Date());
        emprestimoRepository.save(emprestimoBD);
        movimentacaoService.gerarMovimentacao(emprestimoBD, 1l, TipoMovimentacao.ENTRADA);

    }

    public Integer retornaQuantidadeEmprestimoEPI(Long epiId) {
        return emprestimoRepository.quantidadeEmprestimoEPI(epiId);
    }

    public Integer retornaQuantidadeEmprestimoEPIPorMes(Long epiId, Date dataInicio, Date dataFim) {
        return emprestimoRepository.quantidadeEmprestimoEPIPorMes(epiId, formatarData(dataInicio), formatarData(dataFim));
    }

    public Integer retornaQuantidadeEmprestimoPorDia(Date dataInicio, Date dataFim) {
        return emprestimoRepository.quantidadeEmprestimoPorDia(formatarData(dataInicio), formatarData(dataFim));
    }
    public Integer retornaQuantidadeDevolucaoPorDia(Date dataInicio, Date dataFim) {
        return emprestimoRepository.quantidadeDevolucaoPorDia(formatarData(dataInicio), formatarData(dataFim));
    }

    private String formatarData(Date data) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(data);
    }





}
