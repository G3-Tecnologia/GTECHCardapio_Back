package com.example.cardapio.repository;

import com.example.cardapio.model.VendaCabecalho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VendaCabecalhoRepository extends JpaRepository<VendaCabecalho, Long> {
    Optional<VendaCabecalho> findFirstByIdMesaAndEncerradaFalseAndCanceladaFalseOrderByIdDesc(Long idMesa);
}
