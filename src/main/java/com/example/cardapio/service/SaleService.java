package com.example.cardapio.service;

import com.example.cardapio.dto.PedidoStatusView;
import com.example.cardapio.dto.StatusItemPedidoDTO;
import com.example.cardapio.dto.VendaItemDTO;
import com.example.cardapio.model.Produto;
import com.example.cardapio.model.VendaDetalhe;
import com.example.cardapio.repository.ProdutoRepository;
import com.example.cardapio.repository.VendaDetalheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SaleService {

    private final VendaDetalheRepository vendaDetalheRepository;
    private final ProdutoRepository produtoRepository;

    @Autowired
    private com.example.cardapio.repository.VendaCabecalhoRepository vendaCabecalhoRepository;

    @Autowired
    public SaleService(VendaDetalheRepository vendaDetalheRepository,
            ProdutoRepository produtoRepository) {
        this.vendaDetalheRepository = vendaDetalheRepository;
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public com.example.cardapio.model.VendaCabecalho registrarVenda(Long idMesa, List<VendaItemDTO> itens) {
        // Tentar encontrar um cabeçalho em aberto para a mesa
        com.example.cardapio.model.VendaCabecalho cabecalho = vendaCabecalhoRepository
                .findFirstByIdMesaAndEncerradaFalseAndCanceladaFalseOrderByIdDesc(idMesa)
                .orElseGet(() -> {
                    com.example.cardapio.model.VendaCabecalho novo = new com.example.cardapio.model.VendaCabecalho();
                    novo.setIdMesa(idMesa);
                    novo.setAtendenteId(1L); // Default attendant ID
                    novo.setEncerrada(false);
                    novo.setCancelada(false);
                    return vendaCabecalhoRepository.save(novo);
                });

        List<VendaDetalhe> detalhes = itens.stream().map(item -> {
            VendaDetalhe detalhe = new VendaDetalhe();

            // Busca o nome do produto diretamente do banco (coluna DESCRICAOPDV)
            // para garantir que gc_venda_detalhe.produto_descricao seja preenchido
            // corretamente.
            String nomeProduto = item.produtoDescricao(); // fallback: usa o que veio do front
            if (item.produtoId() != null) {
                nomeProduto = produtoRepository.findById(item.produtoId())
                        .map(Produto::getName)
                        .orElse(item.produtoDescricao());
            }

            detalhe.setProdutoDescricao(nomeProduto);
            detalhe.setObservacao(item.observacao());
            detalhe.setQuantidade(item.quantidade());

            detalhe.setValorProduto(item.valorUnitario());
            detalhe.setIdAtendente(1L); // Default attendant ID
            detalhe.setProdutoId(item.produtoId());
            detalhe.setCancelado(false);
            detalhe.setVendaCabecalho(cabecalho); // Associar ao cabeçalho
            return detalhe;
        }).toList();

        List<VendaDetalhe> detalhesSalvos = vendaDetalheRepository.saveAll(detalhes);
        cabecalho.setDetalhes(detalhesSalvos);

        return cabecalho;
    }

    /**
     * Retorna os itens ativos com nome do produto buscado diretamente de
     * produto.DESCRICAOPDV.
     * Transmitido via SSE para atualização em tempo real.
     */
    public List<StatusItemPedidoDTO> listarPedidosAtivos() {
        List<PedidoStatusView> resultado = vendaDetalheRepository.findAllComNomeProduto();
        return resultado.stream()
                .map(StatusItemPedidoDTO::from)
                .toList();
    }

    /**
     * Retorna o status atual dos itens de um pedido específico (por ID do
     * cabeçalho).
     * Nome do produto vem de produto.DESCRICAOPDV via JOIN nativo.
     */
    public List<StatusItemPedidoDTO> getStatusPedido(Long vendaCabecalhoId) {
        List<PedidoStatusView> resultado = vendaDetalheRepository.findByVendaCabecalhoId(vendaCabecalhoId);
        return resultado.stream()
                .map(StatusItemPedidoDTO::from)
                .toList();
    }
}
