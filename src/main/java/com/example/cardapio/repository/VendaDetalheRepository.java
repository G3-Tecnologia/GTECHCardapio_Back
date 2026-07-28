package com.example.cardapio.repository;

import com.example.cardapio.dto.PedidoStatusView;
import com.example.cardapio.model.VendaDetalhe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VendaDetalheRepository extends JpaRepository<VendaDetalhe, Long> {

    /**
     * Retorna todos os itens de pedidos ativos:
     * venda não encerrada, não cancelada e item não cancelado.
     */
    @Query(value = """
            SELECT g.* FROM gc_venda_detalhe g
            INNER JOIN gc_venda_cabecalho c ON c.id = g.gc_venda_cabecalho_id
            WHERE NOT c.encerrada AND NOT c.cancelada AND NOT g.cancelado
            """, nativeQuery = true)
    List<VendaDetalhe> findItensPedidosAtivos();

    /**
     * Busca todos os itens ativos da view com JOIN na tabela produto para
     * obter o nome do produto a partir da coluna DESCRICAOPDV.
     */
    @Query(value = """
            SELECT
                v.GC_VENDA_CABECALHO_ID AS gcVendaCabecalhoId,
                v.PRODUTO_ID             AS produtoId,
                p.DESCRICAOPDV           AS produtoDescricao,
                v.QUANTIDADE             AS quantidade,
                v.OBSERVACAO             AS observacao,
                v.DATA_HORA_INSERCAO     AS dataHoraInsercao,
                v.ENTREGUE               AS entregue,
                v.CODIGO_STATUS          AS codigoStatus
            FROM view_cheff_detalhe_por_status v
            LEFT JOIN produto p ON p.id = v.PRODUTO_ID
            ORDER BY v.DATA_HORA_INSERCAO ASC
            """, nativeQuery = true)
    List<PedidoStatusView> findAllComNomeProduto();

    /**
     * Busca os itens de um pedido específico com JOIN na tabela produto
     * para obter DESCRICAOPDV.
     */
    @Query(value = """
            SELECT
                v.GC_VENDA_CABECALHO_ID AS gcVendaCabecalhoId,
                v.PRODUTO_ID             AS produtoId,
                p.DESCRICAOPDV           AS produtoDescricao,
                v.QUANTIDADE             AS quantidade,
                v.OBSERVACAO             AS observacao,
                v.DATA_HORA_INSERCAO     AS dataHoraInsercao,
                v.ENTREGUE               AS entregue,
                v.CODIGO_STATUS          AS codigoStatus
            FROM view_cheff_detalhe_por_status v
            LEFT JOIN produto p ON p.id = v.PRODUTO_ID
            WHERE v.GC_VENDA_CABECALHO_ID = :vendaCabecalhoId
            ORDER BY v.DATA_HORA_INSERCAO ASC
            """, nativeQuery = true)
    List<PedidoStatusView> findByVendaCabecalhoId(@Param("vendaCabecalhoId") Long vendaCabecalhoId);
}
