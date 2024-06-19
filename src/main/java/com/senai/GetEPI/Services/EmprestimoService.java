package com.senai.GetEPI.Services;

import com.senai.GetEPI.DTOs.ColaboradorDto;
import com.senai.GetEPI.DTOs.EmprestimoDTO;
import com.senai.GetEPI.DTOs.EpiDto;
import com.senai.GetEPI.DTOs.ViewEmprestimoDTO;
import com.senai.GetEPI.Dominios.OrigemMovimentacao;
import com.senai.GetEPI.Dominios.TipoMovimentacao;
import com.senai.GetEPI.Models.ColaboradorModel;
import com.senai.GetEPI.Models.EmprestimoModel;
import com.senai.GetEPI.Models.MovimentacaoModel;
import com.senai.GetEPI.Repositories.ColaboradorRepository;
import com.senai.GetEPI.Repositories.EmprestimoRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmprestimoService {

    @Autowired
    EmprestimoRepository emprestimoRepository;

    @Autowired
    MovimentacaoService movimentacaoService;

    //Adicionado para evitar "circular references" -- GR;
    @Autowired
    ColaboradorRepository colaboradorRepository;

    @Autowired
    AlocacaoService alocacaoService;

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

        Optional<EmprestimoModel> existeEmprestimo = emprestimoRepository.existeEmprestimoVigente(dadosEmprestimo.getColaborador().getId(), dadosEmprestimo.getEpi().getId());
        if(existeEmprestimo.isPresent()) {
            return "Colaborador já possuí um empréstimo para este equipamento!";
        }

        EmprestimoModel novoEmprestimo = new EmprestimoModel();
        novoEmprestimo.setColaborador(dadosEmprestimo.getColaborador());
        novoEmprestimo.setEpi(dadosEmprestimo.getEpi());
        novoEmprestimo.setEmissaoData(new Date());
        novoEmprestimo.setDevolucaoData(null);
        novoEmprestimo.setRegistroInterno(false);

        emprestimoRepository.save(novoEmprestimo);

        String mensagemMovimentacao = movimentacaoService.gerarMovimentacao(novoEmprestimo, -1l, TipoMovimentacao.SAIDA, OrigemMovimentacao.EMPRESTIMO);
        if (!mensagemMovimentacao.isEmpty()) {
            emprestimoRepository.delete(novoEmprestimo);
            return mensagemMovimentacao;
        }


        return "";
    }

    public ViewEmprestimoDTO retonarViewEmprestimoDTO(Long id) {
        return new ViewEmprestimoDTO(emprestimoRepository.findById(id).get());
    }

    public String excluirEmprestimo(Long id, HttpServletRequest request){

        try {
            Optional<EmprestimoModel> emprestimo = emprestimoRepository.findById(id);

            movimentacaoService.excluirMovimentacaoPorEmprestimo(id);

            emprestimoRepository.delete(emprestimo.get());

            if (emprestimo.get().getDevolucaoData() == null) {
                EmprestimoModel emprestimoInterno = alocacaoService.gerarEmprestimoInterno(request, emprestimo.get().getEpi());
                movimentacaoService.gerarMovimentacaoInterna(emprestimoInterno, 1l, TipoMovimentacao.ENTRADA, OrigemMovimentacao.EXCLUSAO_REGISTRO);
            }

            return "";
        } catch (Exception e) {
            return e.toString();
        }

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
        movimentacaoService.gerarMovimentacao(emprestimoBD, 1l, TipoMovimentacao.ENTRADA, OrigemMovimentacao.DEVOLUCAO);

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

    public List<ViewEmprestimoDTO> buscarEmprestimoPorColaborador(String nomeBuscado) {
//        List<ColaboradorDto> colaboradoresComONomeDigitado = colaboradorService.buscarColaboradorPorNome(nomeBuscado);
        //Refeita lógica para aceitar colaboradorRepository
        List<ColaboradorModel> colaboradoresEncontrados = colaboradorRepository.findByNomeContaining(nomeBuscado);
        List<ColaboradorDto> colaboradoresComONomeDigitado = converterListaColaboradorDTO(colaboradoresEncontrados);


        List<EmprestimoModel> emprestimosEncontrados = new ArrayList<>();
        for(ColaboradorDto colaborador : colaboradoresComONomeDigitado) {

            List<EmprestimoModel> emprestimosDesteColaborador = emprestimoRepository.findAllByColaboradorId(colaborador.getId());
            emprestimosEncontrados.addAll(emprestimosDesteColaborador);
        }

        return converterListaEmprestimoView(emprestimosEncontrados);
    }

    public List<ViewEmprestimoDTO> buscarPendentesDevolucaoPorColaborador(String nomeBuscado) {
        List<ColaboradorModel> colaboradoresEncontrados = colaboradorRepository.findByNomeContaining(nomeBuscado);
        List<ColaboradorDto> colaboradoresComONomeDigitado = converterListaColaboradorDTO(colaboradoresEncontrados);

        List<EmprestimoModel> emprestimosEncontrados = new ArrayList<>();
        for(ColaboradorDto colaborador : colaboradoresComONomeDigitado) {

            List<EmprestimoModel> emprestimosDesteColaborador = emprestimoRepository.findAllDevolucoesByColaboradorId(colaborador.getId());
            emprestimosEncontrados.addAll(emprestimosDesteColaborador);
        }

        return converterListaEmprestimoView(emprestimosEncontrados);
    }

    public List<EmprestimoModel> buscarEmprestimosPorColaboradorId(Long colaboradorId) {
        List<EmprestimoModel> emprestimosEncontrados = emprestimoRepository.findAllByColaboradorIdIncluirInternos(colaboradorId);
        if (emprestimosEncontrados.isEmpty()) {
            return new ArrayList<EmprestimoModel>();
        }

        return emprestimosEncontrados;
    }

    public List<EmprestimoModel> buscarEmprestimosPorEpiId(Long epiId) {
        List<EmprestimoModel> emprestimosEncontrados = emprestimoRepository.findAllByEpiId(epiId);
        if (emprestimosEncontrados.isEmpty()) {
            return new ArrayList<EmprestimoModel>();
        }

        return emprestimosEncontrados;
    }

    private List<ColaboradorDto> converterListaColaboradorDTO(List<ColaboradorModel> listaColaboradorModel) {
        return listaColaboradorModel.stream().map(ColaboradorDto::new).collect(Collectors.toList());
    }

}
