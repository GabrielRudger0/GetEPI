package com.senai.GetEPI.Repositories;

import com.senai.GetEPI.Models.ColaboradorModel;
import com.senai.GetEPI.Models.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ColaboradorRepository extends JpaRepository<ColaboradorModel, Long> {

    public boolean existsByEmail(String email);

    public List<ColaboradorModel> findByNomeContaining(String nome);

    public Optional<ColaboradorModel> findByEmail(String email);

    public Optional<ColaboradorModel> findByUsuario(UsuarioModel usuarioModel);


}
