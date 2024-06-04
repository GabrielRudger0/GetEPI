package com.senai.GetEPI.DTOs;

import com.senai.GetEPI.Models.EmprestimoModel;
import lombok.Data;

import java.util.Date;

@Data
public class RegistroDto {


    private Long Id;

    private Date dataMovimentacao;

    private Long quantidade;

    private EmprestimoModel emprestimoModel;



}
