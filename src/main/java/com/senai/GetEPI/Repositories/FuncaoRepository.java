package com.senai.GetEPI.Repositories;

import com.senai.GetEPI.Models.FuncaoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FuncaoRepository extends JpaRepository<FuncaoModel, Long> {

    Optional<FuncaoModel> findByFuncao(String funcao);
}
