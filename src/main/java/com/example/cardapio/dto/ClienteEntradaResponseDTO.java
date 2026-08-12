package com.example.cardapio.dto;

public record ClienteEntradaResponseDTO(
        Long idGcLinkMesaComanda,
        Long idMesa,
        Long idGcVendaCabecalho,
        String nomeCliente,
        String telefoneCliente
) {}
