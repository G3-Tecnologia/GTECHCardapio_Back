package com.example.cardapio.repository;

import com.example.cardapio.model.VendaCabecalho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VendaCabecalhoRepository extends JpaRepository<VendaCabecalho, Long> {
    Optional<VendaCabecalho> findFirstByIdMesaAndEncerradaFalseAndCanceladaFalseOrderByIdDesc(Long idMesa);

    @org.springframework.data.jpa.repository.Query(value = "SELECT * FROM gc_venda_cabecalho WHERE id_mesa = :idMesa AND NOT ENCERRADA AND NOT CANCELADA ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Optional<VendaCabecalho> findMesaAberta(@org.springframework.data.repository.query.Param("idMesa") Long idMesa);

    @org.springframework.data.jpa.repository.Query(value = "SELECT cd_cheff_porc_taxa_servico FROM configuracao_default LIMIT 1", nativeQuery = true)
    Object findTaxaServicoDefault();
}
