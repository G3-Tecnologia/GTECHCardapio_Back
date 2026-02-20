package com.example.cardapio.repository;

import com.example.cardapio.model.VendaCabecalho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendaCabecalhoRepository extends JpaRepository<VendaCabecalho, Long> {
}
