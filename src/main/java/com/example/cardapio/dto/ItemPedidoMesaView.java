package com.example.cardapio.dto;

import java.time.LocalDateTime;

public interface ItemPedidoMesaView {
    Long getVendaDetalheId();
    Long getGcVendaCabecalhoId();
    Long getProdutoId();
    String getProdutoDescricao();
    Double getQuantidade();
    Double getValorProduto();
    String getObservacao();
    LocalDateTime getDataHoraInsercao();
    Boolean getEntregue();
    Integer getCodigoStatus();
    Long getIdGcLinkMesaComanda();
    String getNomeCliente();
    String getTelefoneCliente();
}
