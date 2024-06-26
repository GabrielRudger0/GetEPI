package com.senai.GetEPI.Services;

import com.senai.GetEPI.DTOs.ColaboradorDto;
import com.senai.GetEPI.DTOs.UpdColaboradorDTO;
import com.senai.GetEPI.DTOs.UsuarioDTO;
import com.senai.GetEPI.Models.ColaboradorModel;
import com.senai.GetEPI.Models.EmprestimoModel;
import com.senai.GetEPI.Models.FuncaoModel;
import com.senai.GetEPI.Models.UsuarioModel;
import com.senai.GetEPI.Repositories.ColaboradorRepository;
import com.senai.GetEPI.Repositories.EmprestimoRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ColaboradorService {

    @Autowired
    ColaboradorRepository colaboradorRepository;

    @Autowired
    ParametroGeralService parametroGeralService;

    @Autowired
    EmprestimoService emprestimoService;

    public String cadastrarColaborador (ColaboradorDto colaboradorDto){
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
       boolean existeColaborador = colaboradorRepository.existsByEmail(colaboradorDto.getEmail());
        Date dataNascimento = new Date();
        try {
             dataNascimento = formato.parse(colaboradorDto.getDataNascimento());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(existeColaborador){
            return "Colaborador já registrado!";
        }

        // Calcula a idade do colaborador
        Calendar dataNascimentoCal = Calendar.getInstance();
        dataNascimentoCal.setTime(dataNascimento);
        Calendar dataAtual = Calendar.getInstance();
        int idade = dataAtual.get(Calendar.YEAR) - dataNascimentoCal.get(Calendar.YEAR);
        if (dataAtual.get(Calendar.MONTH) < dataNascimentoCal.get(Calendar.MONTH) ||
                (dataAtual.get(Calendar.MONTH) == dataNascimentoCal.get(Calendar.MONTH) &&
                        dataAtual.get(Calendar.DAY_OF_MONTH) < dataNascimentoCal.get(Calendar.DAY_OF_MONTH))) {
            idade--;
        }


        if ((idade >= 100)) {
            return "A data de nascimento não pode ser maior/igual que 100 anos!";

        }
        if (dataNascimento.after(dataAtual.getTime())) {
            return "A data de nascimento não pode ser maior que hoje!";
        }

        ColaboradorModel colaborador = new ColaboradorModel();
        colaborador.setNome(colaboradorDto.getNome().trim().toUpperCase());
        colaborador.setEmail(colaboradorDto.getEmail());
        colaborador.setFuncao(colaboradorDto.getFuncao());
        colaborador.setDataNascimento(dataNascimento);

        colaboradorRepository.save(colaborador);

        return "";

    }

    public List<ColaboradorModel> obterListaColaboradores(){
        return colaboradorRepository.findAll();
    }

    public List<ColaboradorDto> retornaListaColaboradorDTO() {
        return converterListaColaboradorDTO(colaboradorRepository.findAll());
    }

    public List<ColaboradorDto> converterListaColaboradorDTO(List<ColaboradorModel> listaColaboradorModel) {
        return listaColaboradorModel.stream().map(ColaboradorDto::new).collect(Collectors.toList());
    }

    public ColaboradorDto buscaColaboradorDTO(Long id){
        ColaboradorModel colaborador = colaboradorRepository.findById(id).get();

        return new ColaboradorDto(colaborador,colaborador.getFuncao());
    }

    public UpdColaboradorDTO buscaColaboradorDTOupd(Long id){
        ColaboradorModel colaborador = colaboradorRepository.findById(id).get();

        return new UpdColaboradorDTO(colaborador);
    }

    public String excluirColaborador(Long id, HttpServletRequest request, boolean exclusaoUsuario){
        try {
            Optional<ColaboradorModel> optionalColaborador = colaboradorRepository.findById(id);

            if ((optionalColaborador.get().getUsuario() != null) && (!exclusaoUsuario)) {
                return "Este registro de colaborador possuí um usuário vinculado. Exclua o usuário correspondente para também excluír o colaborador!";
            }

            List<EmprestimoModel> emprestimosDoColaborador = emprestimoService.buscarEmprestimosPorColaboradorId(id);
            if (!emprestimosDoColaborador.isEmpty()) {
                for (EmprestimoModel emprestimo : emprestimosDoColaborador) {
                    if (emprestimo.getDevolucaoData() == null) {
                        return "Colaborador possuí empréstimos PENDENTES PARA DEVOLUÇÃO! Conclua os empréstimos para realizar a exclusão.";
                    }
                }

                for (EmprestimoModel emprestimo : emprestimosDoColaborador) {
                    emprestimoService.excluirEmprestimo(emprestimo.getId(), request);
                }
            }

            colaboradorRepository.delete(optionalColaborador.get());
            return "";

        } catch (Exception e) {
            return e.toString();
        }

    }

    public String atualizaColaborador(ColaboradorDto colaborador) {
        Optional<ColaboradorModel> colaboradorBD = colaboradorRepository.findById(colaborador.getId());

        ColaboradorModel atualizar = new ColaboradorModel();

        if (!colaboradorBD.get().getEmail().equals(colaborador.getEmail())) {
            if (colaboradorRepository.existsByEmail(colaborador.getEmail())) {
                return "Já existe cadastro com estas credenciais!";
            }
        }
        atualizar.setId(colaborador.getId());
        atualizar.setNome(colaborador.getNome().trim().toUpperCase());
        atualizar.setEmail(colaborador.getEmail());
        atualizar.setFuncao(colaborador.getFuncao());
        atualizar.setDataNascimento(converteStringToDate(colaborador.getDataNascimento()));
        atualizar.setUsuario(colaborador.getUsuarioVinculado());

        colaboradorRepository.save(atualizar);

        return "";

    }

    private Date converteStringToDate(String dataString){
        dataString += " 00:00:00.0";
        System.out.println(dataString);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        try {

            Date data = dateFormat.parse(dataString);
            return data;
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    public List<ColaboradorDto> buscarColaboradorPorNome(String nome) {
        List<ColaboradorModel> colaboradoresEncontrados = colaboradorRepository.findByNomeContaining(nome);
        return converterListaColaboradorDTO(colaboradoresEncontrados);
    }

    public ColaboradorDto buscarColaboradorPorEmail(String email) {
        return new ColaboradorDto(colaboradorRepository.findByEmail(email).get());
    }

    public ColaboradorDto buscarColaboradorPorUsuario(UsuarioModel usuario) {
        Optional<ColaboradorModel> colaborador = colaboradorRepository.findByUsuario(usuario);
        if (colaborador.isPresent()) {
            return new ColaboradorDto(colaborador.get());
        }
        return null;
    }

    public void criarColaboradorUsuario(UsuarioModel usuario) {
        colaboradorRepository.save(new ColaboradorModel(usuario, parametroGeralService.obterParametroFuncaoUsuario()));
    }

}
