package com.example.cardapio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClienteEntradaDTO(
        @NotNull(message = "O ID da mesa é obrigatório") Long idMesa,
        @NotBlank(message = "O nome do cliente é obrigatório") String nomeCliente,
        @NotBlank(message = "O telefone do cliente é obrigatório") String telefoneCliente,
        Long idGcVendaCabecalhoToken // Presente quando o cliente entra via link compartilhado
) {}
