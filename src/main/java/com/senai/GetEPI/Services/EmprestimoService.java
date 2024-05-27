package com.senai.GetEPI.Services;

import com.senai.GetEPI.DTOs.ColaboradorDto;
import com.senai.GetEPI.DTOs.EmprestimoDTO;
import com.senai.GetEPI.DTOs.ViewEmprestimoDTO;
import com.senai.GetEPI.Models.ColaboradorModel;
import com.senai.GetEPI.Models.EmprestimoModel;
import com.senai.GetEPI.Repositories.EmprestimoRepository;
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
        emprestimoRepository.delete(emprestimo.get());
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

    }

    public Integer retornaQuantidadeEmprestimoEPI(Long epiId) {
        return emprestimoRepository.quantidadeEmprestimoEPI(epiId);
    }

    public Integer retornaQuantidadeEmprestimoEPIPorMes(Long epiId, Date dataInicio, Date dataFim) {
        System.out.println(formatarData(dataInicio));
        return emprestimoRepository.quantidadeEmprestimoEPIPorMes(epiId, formatarData(dataInicio), formatarData(dataFim));
    }

    private String formatarData(Date data) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(data);
    }

}
