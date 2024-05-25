package com.senai.GetEPI.Repositories;

import com.senai.GetEPI.Models.TipoEquipamentoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoEquipamentoRepository extends JpaRepository<TipoEquipamentoModel, Long> {

    public boolean existsByDescricao(String descricao);

    public Optional<TipoEquipamentoModel> findByDescricao(String descricao);


}
