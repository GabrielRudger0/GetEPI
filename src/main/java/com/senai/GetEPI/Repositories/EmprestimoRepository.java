package com.senai.GetEPI.Repositories;

import com.senai.GetEPI.Models.EmprestimoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EmprestimoRepository extends JpaRepository<EmprestimoModel, Long> {

    @Query(value = "SELECT * FROM emprestimo WHERE emprestimo_devolucao IS NULL", nativeQuery = true)
    public List<EmprestimoModel> findAllWhereEmprestimoDevolucaoIsNull();

    @Query(value = "SELECT COUNT(*) FROM emprestimo WHERE epi_id_epi = ?", nativeQuery = true)
    public Integer quantidadeEmprestimoEPI(Long id);

    @Query(value = "SELECT COUNT(*) FROM emprestimo WHERE epi_id_epi = ? AND emprestimo_emissao_data > ? AND emprestimo_emissao_data < ?", nativeQuery = true)
    public Integer quantidadeEmprestimoEPIPorMes(Long id, String dataInicio, String dataFim);

}
