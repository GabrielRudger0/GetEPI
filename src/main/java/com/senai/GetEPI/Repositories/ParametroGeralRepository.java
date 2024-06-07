package com.senai.GetEPI.Repositories;

import com.senai.GetEPI.Dominios.TipoParametroGeral;
import com.senai.GetEPI.Models.ParametroGeralModel;
import com.senai.GetEPI.Models.TipoEquipamentoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParametroGeralRepository extends JpaRepository<ParametroGeralModel, Long> {

    public Optional<ParametroGeralModel> findByTipoParametroGeral(TipoParametroGeral tipo);

}
