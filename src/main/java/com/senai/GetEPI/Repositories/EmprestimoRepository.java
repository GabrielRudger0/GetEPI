package com.senai.GetEPI.Repositories;

import com.senai.GetEPI.Models.ColaboradorModel;
import com.senai.GetEPI.Models.EmprestimoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmprestimoRepository extends JpaRepository<EmprestimoModel, Long> {

    @Query(value = "SELECT * FROM emprestimo WHERE emprestimo_devolucao IS NULL", nativeQuery = true)
    public List<EmprestimoModel> findAllWhereEmprestimoDevolucaoIsNull();

    @Override
    @Query(value = "SELECT * FROM emprestimo WHERE registro_interno = 0", nativeQuery = true)
    public List<EmprestimoModel> findAll();

    @Query(value = "SELECT COUNT(*) FROM emprestimo WHERE epi_id_epi = ?", nativeQuery = true)
    public Integer quantidadeEmprestimoEPI(Long id);

    @Query(value = "SELECT COUNT(*) FROM emprestimo WHERE epi_id_epi = ? AND emprestimo_emissao_data > ? AND emprestimo_emissao_data < ?", nativeQuery = true)
    public Integer quantidadeEmprestimoEPIPorMes(Long id, String dataInicio, String dataFim);

    @Query(value = "SELECT COUNT(*) FROM emprestimo WHERE emprestimo_emissao_data > ? AND emprestimo_emissao_data < ?", nativeQuery = true)
    public Integer quantidadeEmprestimoPorDia(String dataInicio, String dataFim);

    @Query(value = "SELECT COUNT(*) FROM emprestimo WHERE emprestimo_devolucao > ? AND emprestimo_devolucao < ?", nativeQuery = true)
    public Integer quantidadeDevolucaoPorDia(String dataInicio, String dataFim);

    @Query(value = "SELECT * FROM emprestimo WHERE colaborador_id_colaborador = ? AND epi_id_epi = ? AND emprestimo_devolucao IS NULL LIMIT 1", nativeQuery = true)
    public Optional<EmprestimoModel> existeEmprestimoVigente(Long colaboradorId, Long epiId);

    @Query(value = "SELECT * FROM emprestimo WHERE colaborador_id_colaborador = ?", nativeQuery = true)
    public List<EmprestimoModel> findAllByColaboradorId(Long colaboradorId);

    @Query(value = "SELECT * FROM emprestimo WHERE colaborador_id_colaborador = ? AND emprestimo_devolucao IS NULL", nativeQuery = true)
    public List<EmprestimoModel> findAllDevolucoesByColaboradorId(Long colaboradorId);

    @Query(value = "SELECT * FROM emprestimo WHERE epi_id_epi = ?", nativeQuery = true)
    public List<EmprestimoModel> findAllByEpiId(Long epiId);

}
