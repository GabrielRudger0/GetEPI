package com.senai.GetEPI.Controllers.Home;

import com.senai.GetEPI.OutrosObjetos.DashboardEpis;
import com.senai.GetEPI.OutrosObjetos.DashboardMesEpis;
import com.senai.GetEPI.OutrosObjetos.DashboardMeses;
import com.senai.GetEPI.Services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Calendar;
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

        if (dashboardEpis.isEmpty()) {
            dashboardEpis.add(new DashboardEpis(999l, "SEM EPI", 0));
            dashboardEpis.add(new DashboardEpis(9999l, "SEM EPI", 0));
            dashboardEpis.add(new DashboardEpis(99999l, "SEM EPI", 0));
        }

        model.addAttribute("worldwide_epi_1", dashboardEpis.get(0).getEpiDescricao());
        model.addAttribute("worldwide_epi_2", dashboardEpis.get(1).getEpiDescricao());
        model.addAttribute("worldwide_epi_3", dashboardEpis.get(2).getEpiDescricao());

        model.addAttribute("worldwide_mes_1", dashboardMeses.get(0).getMesReferente());
        model.addAttribute("worldwide_mes_2", dashboardMeses.get(1).getMesReferente());
        model.addAttribute("worldwide_mes_3", dashboardMeses.get(2).getMesReferente());
        model.addAttribute("worldwide_mes_4", dashboardMeses.get(3).getMesReferente());
        model.addAttribute("worldwide_mes_5", dashboardMeses.get(4).getMesReferente());
        model.addAttribute("worldwide_mes_6", dashboardMeses.get(5).getMesReferente());
        model.addAttribute("worldwide_mes_7", dashboardMeses.get(6).getMesReferente());

        model.addAttribute("worldwide_mes1_epi1", dashboardMesEpis.get(0).getEpis().get(0).getQuantidadeEmprestimos());
        model.addAttribute("worldwide_mes2_epi1", dashboardMesEpis.get(1).getEpis().get(0).getQuantidadeEmprestimos());
        model.addAttribute("worldwide_mes3_epi1", dashboardMesEpis.get(2).getEpis().get(0).getQuantidadeEmprestimos());
        model.addAttribute("worldwide_mes4_epi1", dashboardMesEpis.get(3).getEpis().get(0).getQuantidadeEmprestimos());
        model.addAttribute("worldwide_mes5_epi1", dashboardMesEpis.get(4).getEpis().get(0).getQuantidadeEmprestimos());
        model.addAttribute("worldwide_mes6_epi1", dashboardMesEpis.get(5).getEpis().get(0).getQuantidadeEmprestimos());
        model.addAttribute("worldwide_mes7_epi1", dashboardMesEpis.get(6).getEpis().get(0).getQuantidadeEmprestimos());

        model.addAttribute("worldwide_mes1_epi2", dashboardMesEpis.get(0).getEpis().get(1).getQuantidadeEmprestimos());
        model.addAttribute("worldwide_mes2_epi2", dashboardMesEpis.get(1).getEpis().get(1).getQuantidadeEmprestimos());
        model.addAttribute("worldwide_mes3_epi2", dashboardMesEpis.get(2).getEpis().get(1).getQuantidadeEmprestimos());
        model.addAttribute("worldwide_mes4_epi2", dashboardMesEpis.get(3).getEpis().get(1).getQuantidadeEmprestimos());
        model.addAttribute("worldwide_mes5_epi2", dashboardMesEpis.get(4).getEpis().get(1).getQuantidadeEmprestimos());
        model.addAttribute("worldwide_mes6_epi2", dashboardMesEpis.get(5).getEpis().get(1).getQuantidadeEmprestimos());
        model.addAttribute("worldwide_mes7_epi2", dashboardMesEpis.get(6).getEpis().get(1).getQuantidadeEmprestimos());

        model.addAttribute("worldwide_mes1_epi3", dashboardMesEpis.get(0).getEpis().get(2).getQuantidadeEmprestimos());
        model.addAttribute("worldwide_mes2_epi3", dashboardMesEpis.get(1).getEpis().get(2).getQuantidadeEmprestimos());
        model.addAttribute("worldwide_mes3_epi3", dashboardMesEpis.get(2).getEpis().get(2).getQuantidadeEmprestimos());
        model.addAttribute("worldwide_mes4_epi3", dashboardMesEpis.get(3).getEpis().get(2).getQuantidadeEmprestimos());
        model.addAttribute("worldwide_mes5_epi3", dashboardMesEpis.get(4).getEpis().get(2).getQuantidadeEmprestimos());
        model.addAttribute("worldwide_mes6_epi3", dashboardMesEpis.get(5).getEpis().get(2).getQuantidadeEmprestimos());
        model.addAttribute("worldwide_mes7_epi3", dashboardMesEpis.get(6).getEpis().get(2).getQuantidadeEmprestimos());


        return "home";
    }

}
