package com.example.cardapio.repository;

import com.example.cardapio.model.VendaCabecalho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VendaCabecalhoRepository extends JpaRepository<VendaCabecalho, Long> {

    @Query("SELECT v FROM VendaCabecalho v WHERE v.idMesa = :idMesa AND (v.encerrada = false OR v.encerrada IS NULL) AND (v.cancelada = false OR v.cancelada IS NULL) ORDER BY v.id DESC")
    List<VendaCabecalho> findMesaAbertaList(@Param("idMesa") Long idMesa);

    default Optional<VendaCabecalho> findMesaAberta(Long idMesa) {
        List<VendaCabecalho> list = findMesaAbertaList(idMesa);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    @Query(value = "SELECT cd_cheff_porc_taxa_servico FROM configuracao_default LIMIT 1", nativeQuery = true)
    Object findTaxaServicoDefault();
}
