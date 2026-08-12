package com.example.cardapio.dto;

import java.util.List;

public record SolicitacaoContaParcialDTO(
        Long idMesa,
        Long idGcLinkMesaComanda,
        List<Long> itemIds,
        Boolean solicitarTodos
) {}
