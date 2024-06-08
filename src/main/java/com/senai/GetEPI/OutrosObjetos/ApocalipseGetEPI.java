package com.senai.GetEPI.OutrosObjetos;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class ApocalipseGetEPI {

    final String ANSI_YELLOW = "\u001B[33m";
    final String ANSI_RESET = "\u001B[0m";

    public ErroGetEPI retornarErro(HttpServletRequest request) {
        HttpSession sessao = request.getSession();
        Object retornaErro = sessao.getAttribute("retornaErro");
        sessao.removeAttribute("retornaErro");

        if (retornaErro != null) {
            if (!retornaErro.toString().isEmpty()) {
                String  mensagemErro = refatoraMensagem(retornaErro.toString());
                return new ErroGetEPI(true, mensagemErro);

            }
        }

        return new ErroGetEPI(false, "");
    }

    private String refatoraMensagem(String mensagem) {
        switch (mensagem) {
            case "java.util.NoSuchElementException: No value present":
                return "Registro não encontrado!";
            default:
                System.out.println(ANSI_YELLOW + mensagem + ANSI_RESET);
                return "Ocorreu um erro não especificado.";
        }
    }

}
