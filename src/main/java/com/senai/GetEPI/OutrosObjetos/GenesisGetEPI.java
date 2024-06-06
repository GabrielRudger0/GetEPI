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



        System.out.println(logPrefixo + "Verificando parâmetro de função padrão...");
        System.out.println(ANSI_RED);
        FuncaoModel parametroFuncaoUsuario = parametroGeralService.obterParametroFuncaoUsuario();
        if (parametroFuncaoUsuario == null) {
            System.out.println(logPrefixo + "Função padrão não encontrada, criado uma...");
            System.out.println(ANSI_RED);
            FuncaoModel funcaoCriada = parametroGeralService.criarFuncaoPadrao();
            System.out.println(ANSI_RESET);
            System.out.println(logPrefixo + "Função" + " " + "criado.");

            parametroGeralService.salvarParametros(new ParametroGeralDTO(0l, TipoParametroGeral.FuncaoPadraoUsuario, funcaoCriada));

        } else {
            System.out.println(ANSI_RESET);
            System.out.println(logPrefixo + "Função padrão já existe.");
        }

        System.out.println(logPrefixo + "★ GenesisGetEPI finalizado ★");


    }
}


