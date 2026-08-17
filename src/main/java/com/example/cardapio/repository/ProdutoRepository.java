package com.example.cardapio.repository;

import com.example.cardapio.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByGrupoId(Long grupoId);

    @Query("SELECT p FROM Produto p WHERE (p.promocao = '1' OR p.promocao = 'S') AND p.promocao <> '0' " +
            "AND (p.dataInicioPromocao IS NULL OR :agora >= p.dataInicioPromocao) " +
            "AND (p.dataFimPromocao IS NULL OR CAST(:agora AS date) <= CAST(p.dataFimPromocao AS date))")
    List<Produto> findPromocionais(@Param("agora") LocalDateTime agora);

    @Query("SELECT p FROM Produto p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :descricao, '%')) " +
            "ORDER BY CASE WHEN LOWER(p.name) LIKE LOWER(CONCAT(:descricao, '%')) THEN 0 ELSE 1 END, p.name")
    List<Produto> findByDescricaoPrioritized(@Param("descricao") String descricao);
}
