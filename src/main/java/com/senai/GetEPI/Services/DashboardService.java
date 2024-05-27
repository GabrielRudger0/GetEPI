package com.senai.GetEPI.Services;

import com.senai.GetEPI.DTOs.EmprestimoDTO;
import com.senai.GetEPI.DTOs.EpiDto;
import com.senai.GetEPI.Models.EmprestimoModel;
import com.senai.GetEPI.OutrosObjetos.DashboardEpis;
import com.senai.GetEPI.OutrosObjetos.DashboardMesEpis;
import com.senai.GetEPI.OutrosObjetos.DashboardMeses;
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

    public List<DashboardMesEpis> retornarRelacaoEpiEmprestimoMes() {
        List<DashboardMesEpis> listaRelacaoMesEpi = new ArrayList<>();
        List<DashboardEpis> dashboardEpis = retornarRankEPIs();

//        if (dashboardEpis.isEmpty()) {
//            DashboardMesEpis informacoesDoMes = new DashboardMesEpis();
//            informacoesDoMes.setMes("Mes");
//
//            List<DashboardEpis> epiDadosMes = new ArrayList<>();
//            DashboardEpis newEPI = new DashboardEpis();
//            newEPI.setEpiId(1l);
//            newEPI.setEpiDescricao("SEM EPI");
//            newEPI.setQuantidadeEmprestimos(0);
//            epiDadosMes.add(newEPI);
//            epiDadosMes.add(newEPI);
//            epiDadosMes.add(newEPI);
//
//            informacoesDoMes.setEpis(epiDadosMes);
//
//            listaRelacaoMesEpi.add(informacoesDoMes);
//            listaRelacaoMesEpi.add(informacoesDoMes);
//            listaRelacaoMesEpi.add(informacoesDoMes);
//            listaRelacaoMesEpi.add(informacoesDoMes);
//            listaRelacaoMesEpi.add(informacoesDoMes);
//            listaRelacaoMesEpi.add(informacoesDoMes);
//            listaRelacaoMesEpi.add(informacoesDoMes);
//
//            listaRelacaoMesEpi.add(informacoesDoMes);
//            listaRelacaoMesEpi.add(informacoesDoMes);
//            listaRelacaoMesEpi.add(informacoesDoMes);
//            listaRelacaoMesEpi.add(informacoesDoMes);
//            listaRelacaoMesEpi.add(informacoesDoMes);
//            listaRelacaoMesEpi.add(informacoesDoMes);
//            listaRelacaoMesEpi.add(informacoesDoMes);
//
//            listaRelacaoMesEpi.add(informacoesDoMes);
//            listaRelacaoMesEpi.add(informacoesDoMes);
//            listaRelacaoMesEpi.add(informacoesDoMes);
//            listaRelacaoMesEpi.add(informacoesDoMes);
//            listaRelacaoMesEpi.add(informacoesDoMes);
//            listaRelacaoMesEpi.add(informacoesDoMes);
//            listaRelacaoMesEpi.add(informacoesDoMes);
//
//            return listaRelacaoMesEpi;
//        }

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

        for (DashboardMesEpis informacoes : listaRelacaoMesEpi) {
            System.out.println("-----------------------------------------");
            System.out.println("Informações do mês de " + informacoes.getMes());
            for (DashboardEpis epi : informacoes.getEpis()) {
                System.out.println("EPI " + epi.getEpiDescricao() + ": " + epi.getQuantidadeEmprestimos());
            }
            System.out.println("-----------------------------------------");
        }

        return listaRelacaoMesEpi;
    }

    private String retonarNomeMes(Calendar mes) {
        SimpleDateFormat formatador = new SimpleDateFormat("MMMM", new Locale("pt", "BR"));
        String mesFormatado = formatador.format(mes.getTime());

        mesFormatado = mesFormatado.substring(0, 1).toUpperCase() + mesFormatado.substring(1);
        return mesFormatado;
    }


}
