package com.senai.GetEPI.Repositories;

import com.senai.GetEPI.Models.EpiModel;
import com.senai.GetEPI.Models.FuncaoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpiRepository extends JpaRepository<EpiModel, Long> {

}
