package com.senai.GetEPI.OutrosObjetos;

import com.senai.GetEPI.DTOs.ParametroGeralDTO;
import com.senai.GetEPI.Dominios.TipoParametroGeral;
import com.senai.GetEPI.Models.FuncaoModel;
import com.senai.GetEPI.Models.ParametroGeralModel;
import com.senai.GetEPI.Services.FuncaoService;
import com.senai.GetEPI.Services.ParametroGeralService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Component
public class GenesisGetEPI {

    @Autowired
    ParametroGeralService parametroGeralService;


    @PostConstruct
    public void iniciarGenesisGetEPI() {

        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED = "\u001B[0;30m";
        final String ANSI_YELLOW = "\u001B[33m";
        final String ANSI_BRANCO = "\u001B[0;37m";
        final String ANSI_PRETO = "\u001B[0;30m";

        Date dataHoraAtual = new Date();
        SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dataHoraFormatada = formatador.format(dataHoraAtual);

        String logPrefixo = ANSI_YELLOW + dataHoraFormatada + ".MiG-29:00  INFO SU-25 --- [GetEPI] " + ANSI_RESET + ANSI_BRANCO + "[GenesisGetEPI]" + ANSI_RESET + ANSI_YELLOW + " : " + ANSI_RESET;

        System.out.println(logPrefixo + "★ Iniciando GenesisGetEPI ★");



        System.out.println(logPrefixo + "Verificando parâmetro geral '" + TipoParametroGeral.FuncaoPadraoUsuario.getDescricao() + "'");
        System.out.print(ANSI_YELLOW);
        FuncaoModel parametroFuncaoUsuario = parametroGeralService.obterParametroFuncaoUsuario();
        if (parametroFuncaoUsuario == null) {
            System.out.println(logPrefixo + "'" + TipoParametroGeral.FuncaoPadraoUsuario.getDescricao() + "' não encontrado. Gerando registro...");
            System.out.print(ANSI_YELLOW);
            FuncaoModel funcaoCriada = parametroGeralService.criarFuncaoPadrao();
            System.out.print(ANSI_RESET);
            System.out.println(logPrefixo + "Gerando registro de parâmetro geral para '" + TipoParametroGeral.FuncaoPadraoUsuario.getDescricao() + "'");
            System.out.print(ANSI_YELLOW);
            parametroGeralService.salvarParametros(new ParametroGeralDTO(0l, TipoParametroGeral.FuncaoPadraoUsuario, funcaoCriada));
            System.out.print(ANSI_RESET);
        } else {
            System.out.print(ANSI_RESET);
            System.out.println(logPrefixo + "'" + TipoParametroGeral.FuncaoPadraoUsuario.getDescricao() + "' já existe.");
        }
        System.out.println(logPrefixo + "★ GenesisGetEPI finalizado ★");

    }
}


