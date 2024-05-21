package com.senai.GetEPI.Repositories;

import com.senai.GetEPI.Models.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {
    public boolean existsByEmail(String email);

    public List<UsuarioModel> findByNomeContaining(String nome);

    public Optional<UsuarioModel> findByEmail(String email);
}
