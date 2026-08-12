package com.example.cardapio.dto;

import java.time.LocalDateTime;

public record ItemPedidoMesaDTO(
        Long vendaDetalheId,
        Long gcVendaCabecalhoId,
        Long produtoId,
        String produtoDescricao,
        Double quantidade,
        Double valorProduto,
        String observacao,
        Integer codigoStatus,
        String statusLabel,
        Boolean entregue,
        LocalDateTime dataHoraInsercao,
        Long idGcLinkMesaComanda,
        String nomeCliente,
        String telefoneCliente
) {
    public static ItemPedidoMesaDTO from(ItemPedidoMesaView v) {
        return new ItemPedidoMesaDTO(
                v.getVendaDetalheId(),
                v.getGcVendaCabecalhoId(),
                v.getProdutoId(),
                v.getProdutoDescricao(),
                v.getQuantidade(),
                v.getValorProduto(),
                v.getObservacao(),
                v.getCodigoStatus(),
                resolverLabel(v.getCodigoStatus(), v.getEntregue()),
                v.getEntregue(),
                v.getDataHoraInsercao(),
                v.getIdGcLinkMesaComanda(),
                v.getNomeCliente() != null ? v.getNomeCliente() : "Cliente da Mesa",
                v.getTelefoneCliente()
        );
    }

    private static String resolverLabel(Integer codigoStatus, Boolean entregue) {
        if (Boolean.TRUE.equals(entregue))
            return "Entregue";
        if (codigoStatus == null)
            return "Aguardando";

        return switch (codigoStatus) {
            case 0 -> "Aguardando";
            case 1 -> "Preparando";
            case 2 -> "Finalizado";
            case 3 -> "Entregue";
            default -> "Status " + codigoStatus;
        };
    }
}
