package com.senai.GetEPI.Services;

import com.senai.GetEPI.DTOs.EmprestimoDTO;
import com.senai.GetEPI.Models.ColaboradorModel;
import com.senai.GetEPI.Models.EmprestimoModel;
import com.senai.GetEPI.Models.EpiModel;
import com.senai.GetEPI.Models.FuncaoModel;
import com.senai.GetEPI.Repositories.ColaboradorRepository;
import com.senai.GetEPI.Repositories.EmprestimoRepository;
import com.senai.GetEPI.Repositories.EpiRepository;
import com.senai.GetEPI.Repositories.FuncaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Random;

@Service
public class GeradorAutomaticoRegistros {

    @Autowired
    ColaboradorRepository colaboradorRepository;

    @Autowired
    FuncaoRepository funcaoRepository;

    @Autowired
    EpiRepository epiRepository;

    @Autowired
    EmprestimoRepository emprestimoRepository;

    public void gerarRegistros() {
        gerarColaboradoresAleatorios();
        gerarEmprestimosAleatorios();
    }

    private void gerarColaboradoresAleatorios() {

        for (int i = 0; i < 50; i++) {
            Random random = new Random();
            Long funcaoid = random.nextLong(6);
            Optional<FuncaoModel> funcao = funcaoRepository.findById(funcaoid);
            if (funcao.isPresent()) {
                String[] nomeEmail = gerarDadosColaboradorAleatorio();

                ColaboradorModel colaborador = new ColaboradorModel();
                colaborador.setNome(nomeEmail[0].toUpperCase());
                colaborador.setEmail(nomeEmail[1]);
                colaborador.setDataNascimento(gerarDataNascimentoAleatoria());

                colaborador.setFuncao(funcao.get());
                colaboradorRepository.save(colaborador);
            }

        }

    }

    private void gerarEmprestimosAleatorios() {
        for (int i = 0; i < 500; i++) {
            EmprestimoDTO emprestimo = new EmprestimoDTO();

            Date[] datas = retornaDataEmissaoDevolucao();

            Random random = new Random();
            Long colaboradorid = random.nextLong(13);
            Optional<ColaboradorModel> colaborador = colaboradorRepository.findById(colaboradorid);
            if (colaborador.isPresent()) {
                Long epiid = random.nextLong(5);
                Optional<EpiModel> epi = epiRepository.findById(epiid);
                if (epi.isPresent()) {
                    emprestimo.setColaborador(colaborador.get());
                    emprestimo.setEpi(epi.get());
                    emprestimo.setRegistroInterno(false);
                    emprestimo.setEmissaoData(datas[0]);
                    emprestimo.setDevolucaoData(datas[1]);
                    emprestimoRepository.save(new EmprestimoModel(emprestimo));
                }
            }
        }

    }

    private Date[] retornaDataEmissaoDevolucao() {
        long dezembro2023 = new Date(123, 11, 1).getTime();
        long junho2024 = new Date(124, 5, 30).getTime();

        Random random = new Random();

        long timestamp = dezembro2023 + (long) (random.nextDouble() * (junho2024 - dezembro2023));


        Date dataEmissao = new Date(timestamp);
        Date[] emissaoDevolucao = {new Date(), new Date()};

        emissaoDevolucao[0] = dataEmissao;
        boolean executarDevolucao = random.nextBoolean();
        if (executarDevolucao) {
            emissaoDevolucao[1] = gerarDataDevolucao(dataEmissao);
        } else {
            emissaoDevolucao[1] = null;
        }
        return emissaoDevolucao;
    }

    private Date gerarDataDevolucao(Date dataInicial) {
            long timestampInicial = dataInicial.getTime();

            long junho2024 = new Date(124, 5, 30).getTime(); // Junho de 2024

            Random random = new Random();

            long timestamp = timestampInicial + (long) (random.nextDouble() * (junho2024 - timestampInicial));

            Date segundaDataAleatoria = new Date(timestamp);

            return segundaDataAleatoria;

    }

    private String[] gerarDadosColaboradorAleatorio() {
        String[] nomes = {
                "Ana", "Pedro", "Mariana", "Joao", "Carolina", "Lucas", "Beatriz", "Gabriel", "Juliana", "Mateus",
                "Larissa", "Andre", "Isabela", "Rafael", "Camila", "Guilherme", "Amanda", "Fernando", "Manuela", "Marcos",
                "Isadora", "Diego", "Valentina", "Vinicius", "Laura", "Marcelo", "Bianca", "Felipe", "Thais", "Leonardo",
                "Clara", "Rodrigo", "Natalia", "Daniel", "Vanessa", "Renan", "Helena", "Luciano", "Luiza", "Gustavo",
                "Leticia", "Bruno", "Livia", "Roberto", "Eduarda", "Jose", "Carla", "Pablo", "Ana Clara", "Fabio",
                "Vitoria", "Ricardo", "Raquel", "Alexandre", "Isabel", "Matheus", "Sofia", "Leandro", "Tatiane", "Arthur",
                "Debora", "Cristiano", "Caroline", "Wagner", "Marcia", "Marcelo", "Julia", "Paulo", "Ana Luiza", "Renata",
                "Thiago", "Gabriela", "Hugo", "Natalia", "Vinicio", "Patricia", "Carlos", "Fernanda", "Felipe", "Larissa",
                "Antonio", "Evelyn", "Lucas", "Mariana", "Josue", "Juliana", "Diego", "Ana Paula", "Renato", "Elisa"
        };

        String[] sobrenomes0 = {
                "Silva", "Santos", "Oliveira", "Souza", "Rodrigues", "Almeida", "Lima", "Costa", "Fernandes", "Pereira",
                "Ribeiro", "Carvalho", "Gomes", "Martins", "Araújo", "Melo", "Barbosa", "Cardoso", "Rocha", "Ramos",
                "Alves", "Monteiro", "Mendes", "Sousa", "Freitas", "Castro", "Moura", "Martinez", "Nunes", "Marques",
                "Goncalves", "Vieira", "Medeiros", "Ferreira", "Cunha", "Dias", "Correa", "Moreira", "Nascimento", "Sales",
                "Azevedo", "Lopes", "Machado", "Correia", "Pinto", "Aragao", "Campos", "Barros", "Teixeira", "Morais",
                "Franco", "Pacheco", "Godoy", "Cavalcanti", "Borges", "Henrique", "Leal", "Farias", "Siqueira", "Batista",
                "Bezerra", "Amaral", "Cabrera", "Lemos", "Schmidt", "Luna", "Peixoto", "Vargas", "Garcia", "Leite",
                "Lara", "Fonseca", "Lacerda", "Miranda", "Freire", "Brito", "Macedo", "Santana", "Tavares", "Bueno",
                "Rocha", "Navarro", "Fogaça", "Moraes", "Caldeira", "Pereira", "Assis", "Motta", "Soares", "Matos"
        };

        String[] sobrenomes1 = {
                "Muller", "Schmidt", "Schneider", "Fischer", "Weber", "Meyer", "Wagner", "Becker", "Schulz", "Hoffmann",
                "Schafer", "Koch", "Bauer", "Richter", "Klein", "Wolf", "Neumann", "Schwarz", "Zimmermann", "Braun",
                "Kruger", "Hofmann", "Lehmann", "Schmitt", "Hartmann", "Schmid", "Schulze", "Maier", "Kohler", "Werner",
                "Lange", "Schreiber", "Voigt", "Gunther", "Krause", "Huber", "Bergmann", "Thomas", "Vob", "Sauer",
                "Arnold", "Pohl", "Brandt", "Franke", "Schuster", "Kaiser", "Winkler", "Seidel", "Fuchs", "Lorenz",
                "Schubert", "Ebert", "Schulte", "Simon", "Albrecht", "Ludwig", "Martin", "Kuhn", "Grob", "Kraus",
                "Roth", "Graf", "Beck", "Keller", "Hahn", "Vogel", "Schroder", "Stein", "Jager", "Schroder"
        };

        String[] sobrenomes2 = {
                "Rossi", "Russo", "Ferrari", "Esposito", "Bianchi", "Romano", "Colombo", "Ricci", "Marino", "Greco",
                "Bruno", "Gallo", "Conti", "De Luca", "Mancini", "Costa", "Giordano", "Rizzo", "Lombardi", "Barbieri",
                "Moretti", "Fontana", "Santoro", "Mariani", "Rinaldi", "Caruso", "Ferrara", "Galli", "Martini", "Leone",
                "Longo", "Gentile", "Martinelli", "Villa", "Serra", "Fabbri", "Coppola", "De Santis", "Neri", "Parisi",
                "Basile", "Amato", "Ferri", "Riva", "Monti", "Sanna", "Donati", "Pellegrini", "Palumbo", "D'Angelo",
                "Gatti", "Palmieri", "Farina", "Bellini", "Mazza", "Giuliani", "Caputo", "Rossetti", "Montanari", "Fiore",
                "Cattaneo", "Marini", "Longo", "Piras", "Grassi", "Orlando", "Carbone", "Ruggiero", "Sorrentino", "Damico"
        };

        String[] colaboradorAleatorio = {"", ""};

        Random random = new Random();
        int nome = random.nextInt(50);
        int sobrenome0 = random.nextInt(50);
        int sobrenome1 = random.nextInt(50);
        int sobrenome2 = random.nextInt(50);

        boolean temSobrenome1 = random.nextBoolean();
        boolean temSobrenome2 = random.nextBoolean();

        String nomeCompletamenteAleatorio = nomes[nome] + " " + sobrenomes0[sobrenome0];
        if (temSobrenome1) {
            nomeCompletamenteAleatorio += " " + sobrenomes1[sobrenome1];
            if (temSobrenome2) {
                nomeCompletamenteAleatorio += " " + sobrenomes2[sobrenome2];
            }
        }
        colaboradorAleatorio[0] = nomeCompletamenteAleatorio;
        colaboradorAleatorio[1] = nomes[nome].toLowerCase() + "." + sobrenomes0[sobrenome0].toLowerCase() + "@amalgamated.com.br";
        return colaboradorAleatorio;

    }

    private Date gerarDataNascimentoAleatoria() {
        Random random = new Random();

        long minTimestamp = new Date(60, 0, 1).getTime(); // Janeiro de 1960
        long maxTimestamp = new Date(100, 0, 1).getTime(); // Janeiro de 2000

        long timestamp = minTimestamp + (long) (random.nextDouble() * (maxTimestamp - minTimestamp));

        Date dataAleatoria = new Date(timestamp);
        return dataAleatoria;
    }

}
