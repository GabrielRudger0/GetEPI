package com.senai.GetEPI.OutrosObjetos;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ApocalipseGetEPI {

    final String ANSI_YELLOW = "\u001B[33m";
    final String ANSI_RESET = "\u001B[0m";
    final String ANSI_BRANCO = "\u001B[0;37m";
    final String ANSI_VERMELHO = "\u001B[31m";

    public ErroGetEPI retornarErro(HttpServletRequest request) {
        HttpSession sessao = request.getSession();
        Object retornaErro = sessao.getAttribute("retornaErro");
        Object retornaStackTrace = sessao.getAttribute("stacktrace");
        sessao.removeAttribute("retornaErro");
        sessao.removeAttribute("stacktrace");

        if (retornaErro != null) {
            if (!retornaErro.toString().isEmpty()) {
                String mensagemErro = refatoraMensagem(retornaErro.toString(), "");
                String stackTrace = retornaStackTrace.toString();
                return new ErroGetEPI(true, mensagemErro, stackTrace);

            }
        }

        return new ErroGetEPI(false, "", "");
    }

    public String refatoraMensagem(String mensagem, String stackTraceCompleta) {
        switch (mensagem) {
            case "java.util.NoSuchElementException: No value present":
                return "Registro não encontrado!";
            case "org.springframework.dao.InvalidDataAccessResourceUsageException":
                return "Tabela não encontrada!";
            default:
                Date dataHoraAtual = new Date();
                SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dataHoraFormatada = formatador.format(dataHoraAtual);
                String logPrefixo = ANSI_YELLOW + dataHoraFormatada + ".MiG-29:00 " + ANSI_VERMELHO + " ERRO  SU-25 --- [GetEPI] [ApocalipseGetEPI] : ";

                System.out.println(logPrefixo + stackTraceCompleta + ANSI_RESET);
                return "Ocorreu um erro não especificado!";
        }
    }

}
