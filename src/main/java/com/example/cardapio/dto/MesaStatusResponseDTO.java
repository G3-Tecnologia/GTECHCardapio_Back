package com.example.cardapio.dto;

public record MesaStatusResponseDTO(
        Long idMesa,
        boolean aberta,
        Long idGcVendaCabecalho,
        String mensagem
) {}
