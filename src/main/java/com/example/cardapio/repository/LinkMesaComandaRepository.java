package com.example.cardapio.repository;

import com.example.cardapio.model.LinkMesaComanda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LinkMesaComandaRepository extends JpaRepository<LinkMesaComanda, Long> {
    List<LinkMesaComanda> findByIdGcVendaCabecalho(Long idGcVendaCabecalho);
}
