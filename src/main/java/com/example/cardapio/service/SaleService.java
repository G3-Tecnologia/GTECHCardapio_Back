package com.example.cardapio.service;

import com.example.cardapio.dto.*;
import com.example.cardapio.model.LinkMesaComanda;
import com.example.cardapio.model.Produto;
import com.example.cardapio.model.VendaCabecalho;
import com.example.cardapio.model.VendaDetalhe;
import com.example.cardapio.repository.LinkMesaComandaRepository;
import com.example.cardapio.repository.ProdutoRepository;
import com.example.cardapio.repository.VendaCabecalhoRepository;
import com.example.cardapio.repository.VendaDetalheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SaleService {

    private final VendaDetalheRepository vendaDetalheRepository;
    private final ProdutoRepository produtoRepository;
    private final LinkMesaComandaRepository linkMesaComandaRepository;

    @Autowired
    private VendaCabecalhoRepository vendaCabecalhoRepository;

    @Autowired
    public SaleService(VendaDetalheRepository vendaDetalheRepository,
                       ProdutoRepository produtoRepository,
                       LinkMesaComandaRepository linkMesaComandaRepository) {
        this.vendaDetalheRepository = vendaDetalheRepository;
        this.produtoRepository = produtoRepository;
        this.linkMesaComandaRepository = linkMesaComandaRepository;
    }

    /**
     * Retorna todos os pedidos da mesa agrupados por cliente/comanda,
     * incluindo o nome de quem fez o pedido.
     */
    public List<PedidoMesaGroupDTO> listarPedidosMesa(Long idMesa) {
        List<ItemPedidoMesaView> itens = vendaDetalheRepository.findItensPedidosPorMesa(idMesa);
        List<ItemPedidoMesaDTO> dtos = itens.stream().map(ItemPedidoMesaDTO::from).toList();

        Map<Long, List<ItemPedidoMesaDTO>> agrupado = dtos.stream()
                .collect(Collectors.groupingBy(
                        item -> item.idGcLinkMesaComanda() != null ? item.idGcLinkMesaComanda() : 0L,
                        LinkedHashMap::new,
                        Collectors.toList()
                ));

        return agrupado.entrySet().stream().map(entry -> {
            List<ItemPedidoMesaDTO> list = entry.getValue();
            ItemPedidoMesaDTO first = list.get(0);
            double total = list.stream()
                    .mapToDouble(i -> (i.valorProduto() != null ? i.valorProduto() : 0.0) * (i.quantidade() != null ? i.quantidade() : 1.0))
                    .sum();

            return new PedidoMesaGroupDTO(
                    entry.getKey(),
                    first.nomeCliente(),
                    first.telefoneCliente(),
                    total,
                    list
            );
        }).toList();
    }

    /**
     * Verifica se a mesa possui uma comanda em aberto usando a query exata:
     * SELECT * FROM gc_venda_cabecalho WHERE id_mesa = :idMesa AND NOT ENCERRADA AND NOT CANCELADA;
     */
    public MesaStatusResponseDTO verificarStatusMesa(Long idMesa) {
        Optional<VendaCabecalho> comandaOpt = vendaCabecalhoRepository.findMesaAberta(idMesa);
        if (comandaOpt.isPresent()) {
            VendaCabecalho cabecalho = comandaOpt.get();
            return new MesaStatusResponseDTO(idMesa, true, cabecalho.getId(), "Mesa já se encontra em aberto.");
        } else {
            return new MesaStatusResponseDTO(idMesa, false, null, "Mesa livre.");
        }
    }

    /**
     * Registra o cliente na mesa (abre a mesa se fechada, ou vincula à mesa aberta via token).
     */
    @Transactional
    public ClienteEntradaResponseDTO entrarNaMesa(ClienteEntradaDTO dto) {
        Optional<VendaCabecalho> comandaOpt = vendaCabecalhoRepository.findMesaAberta(dto.idMesa());

        VendaCabecalho cabecalho;

        if (comandaOpt.isEmpty()) {
            // Mesa está livre: cria novo VendaCabecalho
            VendaCabecalho novo = new VendaCabecalho();
            novo.setIdMesa(dto.idMesa());
            novo.setAtendenteId(1L);
            novo.setEncerrada(false);
            novo.setCancelada(false);
            novo.setSolicitadoConta(false);

            try {
                Object taxaServicoObj = vendaCabecalhoRepository.findTaxaServicoDefault();
                if (taxaServicoObj != null) {
                    novo.setTaxaGarcom(Double.valueOf(taxaServicoObj.toString()));
                }
            } catch (Exception e) {
                // Ignore tax error if missing
            }

            cabecalho = vendaCabecalhoRepository.save(novo);
        } else {
            // Mesa já está aberta
            cabecalho = comandaOpt.get();

            // Tratamento 1: Caso o usuário leia o QR Code direto (sem o token/comandaId) e a mesa já está em aberto
            boolean veioporLink = dto.idGcVendaCabecalhoToken() != null && dto.idGcVendaCabecalhoToken().equals(cabecalho.getId());

            if (!veioporLink) {
                throw new IllegalStateException("A mesa já está em aberto. Não é possível abrir o cardápio via QR Code direto. Solicite o link de compartilhamento a quem abriu a mesa.");
            }
        }

        // Criar o vínculo do cliente em gc_link_mesa_comanda
        LinkMesaComanda link = new LinkMesaComanda();
        link.setIdMesa(dto.idMesa());
        link.setIdGcVendaCabecalho(cabecalho.getId());
        link.setDataHoraVinculo(LocalDateTime.now());
        link.setNomeCliente(dto.nomeCliente());
        link.setTelefoneCliente(dto.telefoneCliente());

        LinkMesaComanda linkSalvo = linkMesaComandaRepository.save(link);

        return new ClienteEntradaResponseDTO(
                linkSalvo.getId(),
                linkSalvo.getIdMesa(),
                linkSalvo.getIdGcVendaCabecalho(),
                linkSalvo.getNomeCliente(),
                linkSalvo.getTelefoneCliente()
        );
    }

    @Transactional
    public VendaCabecalho registrarVenda(Long idMesa, List<VendaItemDTO> itens) {
        if (idMesa == null) {
            throw new IllegalArgumentException("O ID da mesa é obrigatório.");
        }
        if (itens == null || itens.isEmpty()) {
            throw new IllegalArgumentException("O pedido deve conter pelo menos um item.");
        }

        VendaCabecalho cabecalho = vendaCabecalhoRepository
                .findMesaAberta(idMesa)
                .orElseGet(() -> {
                    VendaCabecalho novo = new VendaCabecalho();
                    novo.setIdMesa(idMesa);
                    novo.setAtendenteId(1L);
                    novo.setEncerrada(false);
                    novo.setCancelada(false);

                    try {
                        Object taxaServicoObj = vendaCabecalhoRepository.findTaxaServicoDefault();
                        if (taxaServicoObj != null) {
                            novo.setTaxaGarcom(Double.valueOf(taxaServicoObj.toString()));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return vendaCabecalhoRepository.save(novo);
                });

        List<VendaDetalhe> detalhes = itens.stream().map(item -> {
            VendaDetalhe detalhe = new VendaDetalhe();

            String nomeProduto = item.produtoDescricao();
            if (item.produtoId() != null) {
                nomeProduto = produtoRepository.findById(item.produtoId())
                        .map(Produto::getName)
                        .orElse(item.produtoDescricao());
            }

            if (nomeProduto == null || nomeProduto.isBlank()) {
                nomeProduto = "Produto " + (item.produtoId() != null ? item.produtoId() : "");
            }

            detalhe.setProdutoDescricao(nomeProduto);
            detalhe.setObservacao(item.observacao() != null ? item.observacao() : "");
            detalhe.setQuantidade(item.quantidade() != null && item.quantidade() > 0 ? item.quantidade() : 1);
            detalhe.setValorProduto(item.valorUnitario() != null ? item.valorUnitario() : 0.0);
            detalhe.setIdAtendente(1L);
            detalhe.setProdutoId(item.produtoId() != null ? item.produtoId() : 0L);
            detalhe.setCancelado(false);
            detalhe.setVendaCabecalho(cabecalho);
            detalhe.setIdGcLinkMesaComanda(item.idGcLinkMesaComanda());

            return detalhe;
        }).toList();

        List<VendaDetalhe> detalhesSalvos = vendaDetalheRepository.saveAll(detalhes);
        cabecalho.setDetalhes(detalhesSalvos);

        return cabecalho;
    }

    public List<StatusItemPedidoDTO> listarPedidosAtivos() {
        List<PedidoStatusView> resultado = vendaDetalheRepository.findAllComNomeProduto();
        return resultado.stream()
                .map(StatusItemPedidoDTO::from)
                .toList();
    }

    public List<StatusItemPedidoDTO> getStatusPedido(Long vendaCabecalhoId) {
        List<PedidoStatusView> resultado = vendaDetalheRepository.findByVendaCabecalhoId(vendaCabecalhoId);
        return resultado.stream()
                .map(StatusItemPedidoDTO::from)
                .toList();
    }

    @Transactional
    public VendaCabecalho solicitarConta(Long idMesa) {
        VendaCabecalho cabecalho = vendaCabecalhoRepository
                .findMesaAberta(idMesa)
                .orElseThrow(() -> new RuntimeException("Nenhuma venda em aberto encontrada para esta mesa"));

        cabecalho.setSolicitadoConta(true);
        return vendaCabecalhoRepository.save(cabecalho);
    }

    @Transactional
    public VendaCabecalho solicitarContaParcial(SolicitacaoContaParcialDTO dto) {
        VendaCabecalho cabecalho = vendaCabecalhoRepository
                .findMesaAberta(dto.idMesa())
                .orElseThrow(() -> new RuntimeException("Nenhuma venda em aberto encontrada para esta mesa"));

        if (Boolean.TRUE.equals(dto.solicitarTodos())) {
            cabecalho.setSolicitadoConta(true);
        } else {
            // Em solicitações parciais por cliente/itens, marca a flag de solicitado conta
            cabecalho.setSolicitadoConta(true);
        }

        return vendaCabecalhoRepository.save(cabecalho);
    }
}
