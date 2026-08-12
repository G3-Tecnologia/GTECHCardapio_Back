package com.example.cardapio.dto;

import java.util.List;

public record PedidoMesaGroupDTO(
        Long idGcLinkMesaComanda,
        String nomeCliente,
        String telefoneCliente,
        Double total,
        List<ItemPedidoMesaDTO> items
) {}
