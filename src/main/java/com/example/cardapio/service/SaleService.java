package com.example.cardapio.service;

import com.example.cardapio.dto.VendaItemDTO;
import com.example.cardapio.model.VendaDetalhe;
import com.example.cardapio.repository.VendaDetalheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SaleService {

    private final VendaDetalheRepository vendaDetalheRepository;

    @Autowired
    private com.example.cardapio.repository.VendaCabecalhoRepository vendaCabecalhoRepository;

    @Autowired
    public SaleService(VendaDetalheRepository vendaDetalheRepository) {
        this.vendaDetalheRepository = vendaDetalheRepository;
    }

    @Transactional
    public com.example.cardapio.model.VendaCabecalho registrarVenda(List<VendaItemDTO> itens) {
        // Criar e salvar o cabeçalho
        com.example.cardapio.model.VendaCabecalho cabecalho = new com.example.cardapio.model.VendaCabecalho();
        cabecalho.setDataVenda(java.time.LocalDateTime.now());

        // Calcular total
        double total = itens.stream()
                .mapToDouble(item -> item.valorUnitario() * item.quantidade())
                .sum();
        cabecalho.setValorTotal(total);
        cabecalho.setAtendenteId(1L); // Default attendant ID

        vendaCabecalhoRepository.save(cabecalho);

        List<VendaDetalhe> detalhes = itens.stream().map(item -> {
            VendaDetalhe detalhe = new VendaDetalhe();
            detalhe.setProdutoDescricao(item.produtoDescricao());
            detalhe.setObservacao(item.observacao());
            detalhe.setQuantidade(item.quantidade());
            detalhe.setValorUnitario(item.valorUnitario());
            detalhe.setValorProduto(item.valorUnitario());
            detalhe.setIdAtendente(1L); // Default attendant ID
            detalhe.setProdutoId(item.produtoId());
            detalhe.setVendaCabecalho(cabecalho); // Associar ao cabeçalho
            return detalhe;
        }).toList();

        List<VendaDetalhe> detalhesSalvos = vendaDetalheRepository.saveAll(detalhes);
        cabecalho.setDetalhes(detalhesSalvos);

        return cabecalho;
    }
}
