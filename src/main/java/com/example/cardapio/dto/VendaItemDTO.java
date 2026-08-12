package com.example.cardapio.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VendaItemDTO(
        @NotBlank(message = "A descrição do produto é obrigatória") String produtoDescricao,

        String observacao,

        @NotNull(message = "A quantidade é obrigatória") @Min(value = 1, message = "A quantidade deve ser pelo menos 1") Integer quantidade,

        @NotNull(message = "O valor unitário é obrigatório") Double valorUnitario,

        Long produtoId,

        Long idGcLinkMesaComanda) {
}
