package com.example.cardapio.dto;

import java.time.LocalDateTime;

/**
 * DTO de resposta com o status atual de um item do pedido.
 * Inclui o codigoStatus numérico e uma label textual amigável para exibição no
 * front-end.
 */
public record StatusItemPedidoDTO(
        Long vendaCabecalhoId,
        Long produtoId,
        String produtoDescricao,
        Double quantidade,
        String observacao,
        Integer codigoStatus,
        String statusLabel,
        Boolean entregue,
        LocalDateTime dataHoraInsercao) {

    /**
     * Converte a projection nativa (nome vindo de produto.DESCRICAOPDV) para este
     * DTO.
     */
    public static StatusItemPedidoDTO from(PedidoStatusView v) {
        return new StatusItemPedidoDTO(
                v.getGcVendaCabecalhoId(),
                v.getProdutoId(),
                v.getProdutoDescricao(),
                v.getQuantidade(),
                v.getObservacao(),
                v.getCodigoStatus(),
                resolverLabel(v.getCodigoStatus(), v.getEntregue()),
                v.getEntregue(),
                v.getDataHoraInsercao());
    }

    /**
     * Mapeia o código numérico do status para uma label amigável.
     */
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
