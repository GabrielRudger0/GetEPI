package com.senai.GetEPI.Controllers.Home;

import com.senai.GetEPI.OutrosObjetos.DashboardEpis;
import com.senai.GetEPI.OutrosObjetos.DashboardMesEpis;
import com.senai.GetEPI.OutrosObjetos.DashboardMeses;
import com.senai.GetEPI.OutrosObjetos.DashboardSemana;
import com.senai.GetEPI.Services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    DashboardService dashboardService;

    @GetMapping()
    public String exibirHome(Model model) {

        List<DashboardEpis> dashboardEpis = dashboardService.retornarRankEPIs();
        List<DashboardMeses> dashboardMeses = dashboardService.retornaSeisMesesAnteriores();
        List<DashboardMesEpis> dashboardMesEpis = dashboardService.retornarRelacaoEpiEmprestimoMes();
        Integer quantidadeDevolucoesPendentes = dashboardService.retornaQuantidadeEmprestimosPendentes();
        Integer quantidadeColaboradores = dashboardService.retornaQuantidadeColaboradores();
        Integer quantidadeEPIs = dashboardService.retornaQuantidadeEPIs();

        List<DashboardSemana> dashboardMovimentacoesSemana = dashboardService.retornaSemanaEmprestimoDevolucao();
        for (DashboardSemana informacoes : dashboardMovimentacoesSemana) {
            System.out.println(informacoes.getDiaSemana() + " Emprestimos: " + informacoes.getQuantidadeEmprestimo() + " Devoluções: " + informacoes.getQuantidadeDevolucao());
        }

        model.addAttribute("grafico2_diaSemana1", dashboardMovimentacoesSemana.get(1).getDiaSemana());
        model.addAttribute("grafico2_diaSemana2", dashboardMovimentacoesSemana.get(2).getDiaSemana());
        model.addAttribute("grafico2_diaSemana3", dashboardMovimentacoesSemana.get(3).getDiaSemana());
        model.addAttribute("grafico2_diaSemana4", dashboardMovimentacoesSemana.get(4).getDiaSemana());
        model.addAttribute("grafico2_diaSemana5", dashboardMovimentacoesSemana.get(5).getDiaSemana());
        model.addAttribute("grafico2_diaSemana6", dashboardMovimentacoesSemana.get(6).getDiaSemana());
        model.addAttribute("grafico2_diaSemana7", "Hoje");

        model.addAttribute("grafico2_diaSemana1_devolucoes", dashboardMovimentacoesSemana.get(1).getQuantidadeDevolucao());
        model.addAttribute("grafico2_diaSemana2_devolucoes", dashboardMovimentacoesSemana.get(2).getQuantidadeDevolucao());
        model.addAttribute("grafico2_diaSemana3_devolucoes", dashboardMovimentacoesSemana.get(3).getQuantidadeDevolucao());
        model.addAttribute("grafico2_diaSemana4_devolucoes", dashboardMovimentacoesSemana.get(4).getQuantidadeDevolucao());
        model.addAttribute("grafico2_diaSemana5_devolucoes", dashboardMovimentacoesSemana.get(5).getQuantidadeDevolucao());
        model.addAttribute("grafico2_diaSemana6_devolucoes", dashboardMovimentacoesSemana.get(6).getQuantidadeDevolucao());
        model.addAttribute("grafico2_diaSemana7_devolucoes", dashboardMovimentacoesSemana.get(7).getQuantidadeDevolucao());

        model.addAttribute("grafico2_diaSemana1_emprestimo", dashboardMovimentacoesSemana.get(1).getQuantidadeEmprestimo());
        model.addAttribute("grafico2_diaSemana2_emprestimo", dashboardMovimentacoesSemana.get(2).getQuantidadeEmprestimo());
        model.addAttribute("grafico2_diaSemana3_emprestimo", dashboardMovimentacoesSemana.get(3).getQuantidadeEmprestimo());
        model.addAttribute("grafico2_diaSemana4_emprestimo", dashboardMovimentacoesSemana.get(4).getQuantidadeEmprestimo());
        model.addAttribute("grafico2_diaSemana5_emprestimo", dashboardMovimentacoesSemana.get(5).getQuantidadeEmprestimo());
        model.addAttribute("grafico2_diaSemana6_emprestimo", dashboardMovimentacoesSemana.get(6).getQuantidadeEmprestimo());
        model.addAttribute("grafico2_diaSemana7_emprestimo", dashboardMovimentacoesSemana.get(7).getQuantidadeEmprestimo());



        model.addAttribute("card_superior_1", quantidadeDevolucoesPendentes);
        model.addAttribute("card_superior_2", quantidadeColaboradores);
        model.addAttribute("card_superior_3", quantidadeEPIs);


        if (!dashboardEpis.isEmpty()) {
            model.addAttribute("worldwide_epi_1", dashboardEpis.get(0).getEpiDescricao());

            if (dashboardEpis.size() == 2) {
                model.addAttribute("worldwide_epi_2", dashboardEpis.get(1).getEpiDescricao());
                if (dashboardEpis.size() == 3) {
                    model.addAttribute("worldwide_epi_3", dashboardEpis.get(2).getEpiDescricao());
                }
            }
        }

        model.addAttribute("worldwide_mes_1", dashboardMeses.get(0).getMesReferente());
        model.addAttribute("worldwide_mes_2", dashboardMeses.get(1).getMesReferente());
        model.addAttribute("worldwide_mes_3", dashboardMeses.get(2).getMesReferente());
        model.addAttribute("worldwide_mes_4", dashboardMeses.get(3).getMesReferente());
        model.addAttribute("worldwide_mes_5", dashboardMeses.get(4).getMesReferente());
        model.addAttribute("worldwide_mes_6", dashboardMeses.get(5).getMesReferente());
        model.addAttribute("worldwide_mes_7", dashboardMeses.get(6).getMesReferente());

        if(!dashboardMesEpis.get(0).getEpis().isEmpty()) {
            model.addAttribute("worldwide_mes1_epi1", dashboardMesEpis.get(0).getEpis().get(0).getQuantidadeEmprestimos());
            model.addAttribute("worldwide_mes2_epi1", dashboardMesEpis.get(1).getEpis().get(0).getQuantidadeEmprestimos());
            model.addAttribute("worldwide_mes3_epi1", dashboardMesEpis.get(2).getEpis().get(0).getQuantidadeEmprestimos());
            model.addAttribute("worldwide_mes4_epi1", dashboardMesEpis.get(3).getEpis().get(0).getQuantidadeEmprestimos());
            model.addAttribute("worldwide_mes5_epi1", dashboardMesEpis.get(4).getEpis().get(0).getQuantidadeEmprestimos());
            model.addAttribute("worldwide_mes6_epi1", dashboardMesEpis.get(5).getEpis().get(0).getQuantidadeEmprestimos());
            model.addAttribute("worldwide_mes7_epi1", dashboardMesEpis.get(6).getEpis().get(0).getQuantidadeEmprestimos());

            model.addAttribute("card_superior_4_descricao", dashboardMesEpis.get(6).getEpis().get(0).getEpiDescricao());
            Long maisUtilizadoEpiId = dashboardMesEpis.get(6).getEpis().get(0).getEpiId();
            model.addAttribute("card_superior_4", "Estoque: " + dashboardService.retornaInformacoesEPI(maisUtilizadoEpiId).getQuatidadeEpi());
            model.addAttribute("semEpi", false);


            if (dashboardMesEpis.get(0).getEpis().size() == 2) {
                model.addAttribute("worldwide_mes1_epi2", dashboardMesEpis.get(0).getEpis().get(1).getQuantidadeEmprestimos());
                model.addAttribute("worldwide_mes2_epi2", dashboardMesEpis.get(1).getEpis().get(1).getQuantidadeEmprestimos());
                model.addAttribute("worldwide_mes3_epi2", dashboardMesEpis.get(2).getEpis().get(1).getQuantidadeEmprestimos());
                model.addAttribute("worldwide_mes4_epi2", dashboardMesEpis.get(3).getEpis().get(1).getQuantidadeEmprestimos());
                model.addAttribute("worldwide_mes5_epi2", dashboardMesEpis.get(4).getEpis().get(1).getQuantidadeEmprestimos());
                model.addAttribute("worldwide_mes6_epi2", dashboardMesEpis.get(5).getEpis().get(1).getQuantidadeEmprestimos());
                model.addAttribute("worldwide_mes7_epi2", dashboardMesEpis.get(6).getEpis().get(1).getQuantidadeEmprestimos());

                if (dashboardMesEpis.get(0).getEpis().size() == 3) {
                    model.addAttribute("worldwide_mes1_epi3", dashboardMesEpis.get(0).getEpis().get(2).getQuantidadeEmprestimos());
                    model.addAttribute("worldwide_mes2_epi3", dashboardMesEpis.get(1).getEpis().get(2).getQuantidadeEmprestimos());
                    model.addAttribute("worldwide_mes3_epi3", dashboardMesEpis.get(2).getEpis().get(2).getQuantidadeEmprestimos());
                    model.addAttribute("worldwide_mes4_epi3", dashboardMesEpis.get(3).getEpis().get(2).getQuantidadeEmprestimos());
                    model.addAttribute("worldwide_mes5_epi3", dashboardMesEpis.get(4).getEpis().get(2).getQuantidadeEmprestimos());
                    model.addAttribute("worldwide_mes6_epi3", dashboardMesEpis.get(5).getEpis().get(2).getQuantidadeEmprestimos());
                    model.addAttribute("worldwide_mes7_epi3", dashboardMesEpis.get(6).getEpis().get(2).getQuantidadeEmprestimos());
                }
            }

        } else {
            model.addAttribute("semEpi", true);
        }

        return "home";
    }

}
