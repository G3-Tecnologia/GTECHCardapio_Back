package com.example.cardapio.dto;

import java.time.LocalDateTime;

/**
 * Projection para leitura nativa da view_cheff_detalhe_por_status
 * com JOIN na tabela produto para buscar o nome pelo campo DESCRICAOPDV.
 */
public interface PedidoStatusView {
    Long getGcVendaCabecalhoId();

    Long getProdutoId();

    String getProdutoDescricao();

    Double getQuantidade();

    String getObservacao();

    LocalDateTime getDataHoraInsercao();

    Boolean getEntregue();

    Integer getCodigoStatus();
}
