package com.senai.GetEPI.Controllers.Home;

import com.senai.GetEPI.OutrosObjetos.*;
import com.senai.GetEPI.Services.AlocacaoService;
import com.senai.GetEPI.Services.DashboardService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    DashboardService dashboardService;

    @Autowired
    ApocalipseGetEPI apocalipseGetEPI;

    @Autowired
    AlocacaoService alocacaoService;

    @GetMapping()
    public String exibirHome(Model model, HttpServletRequest request) {

        try {
            if (!alocacaoService.validaSessao(request).isEmpty()) {
                return alocacaoService.validaSessao(request);
            }
            List<DashboardEpis> dashboardEpis = dashboardService.retornarRankEPIs();
//            List<DashboardMesEpis> dashboardMesEpis = dashboardService.retornarRelacaoEpiEmprestimoMes();
            Integer quantidadeDevolucoesPendentes = dashboardService.retornaQuantidadeEmprestimosPendentes();
            Integer quantidadeColaboradores = dashboardService.retornaQuantidadeColaboradores();
            Integer quantidadeEPIs = dashboardService.retornaQuantidadeEPIs();

            model.addAttribute("card_superior_1", quantidadeDevolucoesPendentes);
            model.addAttribute("card_superior_2", quantidadeColaboradores);
            model.addAttribute("card_superior_3", quantidadeEPIs);

            if(!dashboardEpis.isEmpty()) {
                model.addAttribute("card_superior_4_descricao", dashboardEpis.get(0).getEpiDescricao());
                Long maisUtilizadoEpiId = dashboardEpis.get(0).getEpiId();
                model.addAttribute("card_superior_4", "Estoque: " + dashboardService.retornaInformacoesEPI(maisUtilizadoEpiId).getQuatidadeEpi());
                model.addAttribute("semEpi", false);

            } else {
                model.addAttribute("semEpi", true);
            }

        } catch (Exception e) {
            model.addAttribute("erro", true);
            model.addAttribute("tituloMensagemErro", apocalipseGetEPI.refatoraMensagem(e.getClass().getName(), e.toString()));
            model.addAttribute("stacktraceMensagem", e.toString());
            model.addAttribute("semEpi", true);

        }

        return "home";

    }

    @GetMapping("/carregargraficoresultadosmes")
    public ResponseEntity<GraficoResultadosMes> carregarGraficoResultadosMes() {

        List<DashboardMeses> dashboardMeses = dashboardService.retornaSeisMesesAnteriores();

        String[] meses = {"", "", "", "", "", "", ""};
        String[] epis = {"", "", ""};
        String[] resultadoMesEPI1 = {"", "", "", "", "", "", ""};
        String[] resultadoMesEPI2 = {"", "", "", "", "", "", ""};
        String[] resultadoMesEPI3 = {"", "", "", "", "", "", ""};

        for (int i = 0; i < 7; i++) {
            meses[i] = dashboardMeses.get(i).getMesReferente();
        }

        List<DashboardEpis> dashboardEpis = dashboardService.retornarRankEPIs();
        if (!dashboardEpis.isEmpty()) {
            epis[0] = dashboardEpis.get(0).getEpiDescricao();
            if (dashboardEpis.size() >= 2) {
                epis[1] = dashboardEpis.get(1).getEpiDescricao();
                if (dashboardEpis.size() >= 3) {
                    epis[2] = dashboardEpis.get(2).getEpiDescricao();
                }
            }
        }

        List<DashboardMesEpis> dashboardMesEpis = dashboardService.retornarRelacaoEpiEmprestimoMes();

        if(!dashboardMesEpis.get(0).getEpis().isEmpty()) {
            for (int i = 0; i < 7; i++) {
                resultadoMesEPI1[i] = dashboardMesEpis.get(i).getEpis().get(0).getQuantidadeEmprestimos().toString();
            }

            if (dashboardMesEpis.get(0).getEpis().size() >= 2) {
                for (int i = 0; i < 7; i++) {
                    resultadoMesEPI2[i] = dashboardMesEpis.get(i).getEpis().get(1).getQuantidadeEmprestimos().toString();
                }

                if (dashboardMesEpis.get(0).getEpis().size() >= 3) {
                    for (int i = 0; i < 7; i++) {
                        resultadoMesEPI3[i] = dashboardMesEpis.get(i).getEpis().get(2).getQuantidadeEmprestimos().toString();
                    }
                }
            }

        }

        return ResponseEntity.ok(new GraficoResultadosMes(meses, epis, resultadoMesEPI1, resultadoMesEPI2, resultadoMesEPI3));
    }

    @GetMapping("/carregargraficomovimentacoessemana")
    public ResponseEntity<GraficoMovimentacoesSemana> carregarGraficoMovimentacoesSemana() {

        String[] diasSemana = {"", "", "", "", "", "", ""};
        String[] devolucoesPorDiaSemana = {"", "", "", "", "", "", ""};
        String[] emprestimosPorDiaSemana = {"", "", "", "", "", "", ""};

        List<DashboardSemana> dashboardMovimentacoesSemana = dashboardService.retornaSemanaEmprestimoDevolucao();

        for (int i = 0; i < 6; i++) {
            diasSemana[i] = dashboardMovimentacoesSemana.get(i+1).getDiaSemana();
        }
        diasSemana[6] = "Hoje";

        for (int i = 0; i < 7; i++) {
            devolucoesPorDiaSemana[i] = dashboardMovimentacoesSemana.get(i+1).getQuantidadeDevolucao().toString();
            emprestimosPorDiaSemana[i] = dashboardMovimentacoesSemana.get(i+1).getQuantidadeEmprestimo().toString();
        }

        return ResponseEntity.ok(new GraficoMovimentacoesSemana(diasSemana, devolucoesPorDiaSemana, emprestimosPorDiaSemana));
    }

}
