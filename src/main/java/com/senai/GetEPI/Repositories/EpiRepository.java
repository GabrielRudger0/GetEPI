package com.senai.GetEPI.Repositories;

import com.senai.GetEPI.Models.ColaboradorModel;
import com.senai.GetEPI.Models.EpiModel;
import com.senai.GetEPI.Models.FuncaoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EpiRepository extends JpaRepository<EpiModel, Long> {

    public boolean existsByNomeEpi(String nomeEpi);

    public List<EpiModel> findByNomeEpiContaining(String nomeEPI);
}
