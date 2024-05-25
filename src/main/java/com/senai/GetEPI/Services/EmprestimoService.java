package com.senai.GetEPI.Services;

import com.senai.GetEPI.DTOs.ColaboradorDto;
import com.senai.GetEPI.DTOs.EmprestimoDTO;
import com.senai.GetEPI.DTOs.ViewEmprestimoDTO;
import com.senai.GetEPI.Models.ColaboradorModel;
import com.senai.GetEPI.Models.EmprestimoModel;
import com.senai.GetEPI.Repositories.EmprestimoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        return emprestimoModels.stream().map(ViewEmprestimoDTO::new).collect(Collectors.toList());
    }

}
