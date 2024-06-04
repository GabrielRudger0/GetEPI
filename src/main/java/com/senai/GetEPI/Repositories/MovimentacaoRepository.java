package com.senai.GetEPI.Repositories;

import com.senai.GetEPI.Models.EmprestimoModel;
import com.senai.GetEPI.Models.FuncaoModel;
import com.senai.GetEPI.Models.MovimentacaoModel;
import com.senai.GetEPI.Models.TipoEquipamentoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MovimentacaoRepository extends JpaRepository<MovimentacaoModel, Long> {
    @Query(value = "DELETE FROM movimentacao WHERE emprestimo_model_emprestimo_id = ?", nativeQuery = true)
    public void deleteMovimentacaoPorEmprestimo(Long id);

}
