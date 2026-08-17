package com.example.cardapio.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record SolicitacaoContaParcialDTO(
        @NotNull(message = "O ID da mesa é obrigatório") Long idMesa,
        Long idGcLinkMesaComanda,
        List<Long> itemIds,
        Boolean solicitarTodos
) {}
