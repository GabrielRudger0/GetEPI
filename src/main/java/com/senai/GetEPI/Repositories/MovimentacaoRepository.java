package com.senai.GetEPI.Repositories;

import com.senai.GetEPI.Models.FuncaoModel;
import com.senai.GetEPI.Models.MovimentacaoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimentacaoRepository extends JpaRepository<MovimentacaoModel, Long> {
}
