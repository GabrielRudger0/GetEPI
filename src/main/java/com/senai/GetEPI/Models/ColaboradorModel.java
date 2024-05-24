package com.senai.GetEPI.Models;


import com.senai.GetEPI.DTOs.ColaboradorDto;
import jakarta.persistence.*;
import lombok.Data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name="COLABORADOR")
@Data
public class ColaboradorModel {

    @Id
    @Column(name = "id_colaborador")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String email;

    @ManyToOne
    private FuncaoModel funcao;

    private Date dataNascimento;

    public ColaboradorModel(){

    }

    public ColaboradorModel(ColaboradorDto colaboradorDto, FuncaoModel funcao){
        this.id = colaboradorDto.getId();
        this.nome = colaboradorDto.getNome();
        this.email = colaboradorDto.getEmail();
        this.funcao = funcao;
        this.dataNascimento = converteStringToDate(colaboradorDto.getDataNascimento());


    }
        private Date converteStringToDate(String dataString){
            dataString += " 00:00:00.0";
            System.out.println(dataString);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
            try {

                Date data = dateFormat.parse(dataString);
                return data;
            } catch (ParseException e) {
                e.printStackTrace();
                return new Date();
            }
        }

    }


