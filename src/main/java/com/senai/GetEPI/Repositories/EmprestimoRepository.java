package com.senai.GetEPI.Repositories;

import com.senai.GetEPI.Models.EmprestimoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmprestimoRepository extends JpaRepository<EmprestimoModel, Long> {

    @Query(value = "SELECT * FROM emprestimo WHERE emprestimo_devolucao IS NULL", nativeQuery = true)
    public List<EmprestimoModel> findAllWhereEmprestimoDevolucaoIsNull();

}
