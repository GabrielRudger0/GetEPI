package com.senai.GetEPI.Services;

import com.senai.GetEPI.DTOs.EmprestimoDTO;
import com.senai.GetEPI.DTOs.EpiDto;
import com.senai.GetEPI.Models.EmprestimoModel;
import com.senai.GetEPI.OutrosObjetos.DashboardEpis;
import com.senai.GetEPI.OutrosObjetos.DashboardMesEpis;
import com.senai.GetEPI.OutrosObjetos.DashboardMeses;
import com.senai.GetEPI.OutrosObjetos.DashboardSemana;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

@Service
public class DashboardService {

    @Autowired
    EmprestimoService emprestimoService;

    @Autowired
    ColaboradorService colaboradorService;

    @Autowired
    EpiService epiService;

    public List<DashboardEpis> retornarRankEPIs() {

        // Busca todos os epis cadastrados no sistema
        List<EpiDto> epis = epiService.retornaListaEpiDTO();
        List<DashboardEpis> listaEpiQtdEmprestimo = new ArrayList<>();

        // Coleta os epis com sua quantidade de emprestimo e adiciona a uma lista
        for (EpiDto epiDto : epis) {
            int qtdEmprestimosParaEPI = emprestimoService.retornaQuantidadeEmprestimoEPI(epiDto.getId());
            listaEpiQtdEmprestimo.add(new DashboardEpis(epiDto.getId(), epiDto.getNomeEpi().toUpperCase(),qtdEmprestimosParaEPI));

        }

        // Ordena a lista por quantidade de emprestimos do maior para o menor
        Stream<DashboardEpis> listaOrdenadaPorQtdEmprestimo = listaEpiQtdEmprestimo.stream().sorted(Comparator.comparing(DashboardEpis::getQuantidadeEmprestimos).reversed());

        // Filtra os três que tem mais empréstimos
        int controleRank = 0;
        List<DashboardEpis> dashboardEpis = new ArrayList<>();
        for (DashboardEpis epi : listaOrdenadaPorQtdEmprestimo.toList()) {
            if(controleRank == 3) {
                break;
            }
            controleRank += 1;
            dashboardEpis.add(epi);
        }

        return dashboardEpis;
    }

    public List<DashboardMeses> retornaSeisMesesAnteriores() {

        Calendar seisMesesAnteriores = Calendar.getInstance();
        seisMesesAnteriores.add(Calendar.MONTH, -6);

        List<DashboardMeses> listaMeses = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            Calendar calendarDataInicio = Calendar.getInstance();
            Calendar calendarDataFim = Calendar.getInstance();

            calendarDataInicio.set(seisMesesAnteriores.get(Calendar.YEAR),
                    seisMesesAnteriores.get(Calendar.MONTH),
                    seisMesesAnteriores.getActualMinimum(Calendar.DAY_OF_MONTH) );

            calendarDataFim.set(seisMesesAnteriores.get(Calendar.YEAR),
                    seisMesesAnteriores.get(Calendar.MONTH),
                    seisMesesAnteriores.getActualMaximum(Calendar.DAY_OF_MONTH));

            Date dataInicial = calendarDataInicio.getTime();
            Date dataFinal = calendarDataFim.getTime();

            listaMeses.add(new DashboardMeses(retonarNomeMes(seisMesesAnteriores), dataInicial, dataFinal, seisMesesAnteriores));
            seisMesesAnteriores.add(Calendar.MONTH, 1);
        }
        return listaMeses;
    }

    public List<DashboardSemana> retornaSemanaEmprestimoDevolucao() {
        List<DashboardSemana> listaInformacoesSemana = new ArrayList<>();

        Calendar periodoSemana = Calendar.getInstance();
        periodoSemana.add(Calendar.WEEK_OF_YEAR, -1);

        for (int i = 0; i <= 7; i++) {

            Calendar dataInicio = Calendar.getInstance();
            Calendar dataFim = Calendar.getInstance();

            dataInicio.set(periodoSemana.get(Calendar.YEAR),
                    periodoSemana.get(Calendar.MONTH),
                    periodoSemana.get(Calendar.DATE), 0, 0,0);

            dataFim.set(periodoSemana.get(Calendar.YEAR),
                    periodoSemana.get(Calendar.MONTH),
                    periodoSemana.get(Calendar.DATE) + 1, 0, 0,0);

            Integer qtdEmprestimos = emprestimoService.retornaQuantidadeEmprestimoPorDia(dataInicio.getTime(), dataFim.getTime());
            Integer qtdDevolucao   = emprestimoService.retornaQuantidadeDevolucaoPorDia(dataInicio.getTime(), dataFim.getTime());

            DashboardSemana informacoesDia = new DashboardSemana();
            informacoesDia.setDiaSemana(retornaStringDia(periodoSemana.get(Calendar.DAY_OF_WEEK)));
            informacoesDia.setQuantidadeEmprestimo(qtdEmprestimos);
            informacoesDia.setQuantidadeDevolucao(qtdDevolucao);

            listaInformacoesSemana.add(informacoesDia);

            periodoSemana.add(Calendar.DAY_OF_YEAR, 1);
        }

        return listaInformacoesSemana;
    }

    public List<DashboardMesEpis> retornarRelacaoEpiEmprestimoMes() {
        List<DashboardMesEpis> listaRelacaoMesEpi = new ArrayList<>();
        List<DashboardEpis> dashboardEpis = retornarRankEPIs();


        List<DashboardMeses> meses = retornaSeisMesesAnteriores();

        for (DashboardMeses mes : meses) {
            DashboardMesEpis informacoesDoMes = new DashboardMesEpis();
            informacoesDoMes.setMes(mes.getMesReferente());

            List<DashboardEpis> epiDadosMes = new ArrayList<>();
            for (DashboardEpis epi : dashboardEpis) {
                DashboardEpis newEPI = new DashboardEpis();

                Integer qtdEpiMes = emprestimoService.retornaQuantidadeEmprestimoEPIPorMes(epi.getEpiId(), mes.getDataInicio(), mes.getDataFim());

                newEPI.setEpiId(epi.getEpiId());
                newEPI.setEpiDescricao(epi.getEpiDescricao());
                newEPI.setQuantidadeEmprestimos(qtdEpiMes);
                epiDadosMes.add(newEPI);
            }

            informacoesDoMes.setEpis(epiDadosMes);
            listaRelacaoMesEpi.add(informacoesDoMes);
        }

        return listaRelacaoMesEpi;
    }

    public Integer retornaQuantidadeEmprestimosPendentes() {
        return emprestimoService.retornaListaEmprestimosNaoDevolvidos().size();
    }

    public Integer retornaQuantidadeColaboradores() {
        return colaboradorService.retornaListaColaboradorDTO().size();
    }

    public Integer retornaQuantidadeEPIs() {
        return epiService.retornaEPIModel().size();
    }

    public EpiDto retornaInformacoesEPI(Long epiId) {
        return epiService.buscaEpiDTO(epiId);
    }

    private String retonarNomeMes(Calendar mes) {
        SimpleDateFormat formatador = new SimpleDateFormat("MMMM", new Locale("pt", "BR"));
        String mesFormatado = formatador.format(mes.getTime());

        mesFormatado = mesFormatado.substring(0, 1).toUpperCase() + mesFormatado.substring(1);
        return mesFormatado;
    }

    private String retornaStringDia(Integer diaInteiro) {
        String dia;
        switch (diaInteiro) {
            case 1:
                dia = "Domingo";
                break;
            case 2:
                dia = "Segunda";
                break;
            case 3:
                dia = "Terça";
                break;
            case 4:
                dia = "Quarta";
                break;
            case 5:
                dia = "Quinta";
                break;
            case 6:
                dia = "Sexta";
                break;
            case 7:
                dia = "Sábado";
                break;
            default:
                dia = "Número inválido";
                break;
        }
        return dia;
    }


}
