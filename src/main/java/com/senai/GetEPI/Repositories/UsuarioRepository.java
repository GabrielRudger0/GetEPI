package com.senai.GetEPI.Repositories;

import com.senai.GetEPI.Models.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {
    public boolean existsByEmail(String email);
}
