package com.example.cardapio.service;

import com.example.cardapio.model.Produto;
import com.example.cardapio.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {

    private final ProdutoRepository produtoRepository;

    @Autowired
    public ProductService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Transactional(readOnly = true)
    public List<Produto> listarProdutosPorGrupo(Long grupoId) {
        return produtoRepository.findByGrupoId(grupoId);
    }

    @Transactional(readOnly = true)
    public List<Produto> listarTodosProdutos() {
        return produtoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Produto> listarProdutosPromocionais() {
        return produtoRepository.findPromocionais(LocalDateTime.now());
    }

    @Transactional(readOnly = true)
    public List<Produto> pesquisarProdutos(String descricao) {
        return produtoRepository.findByDescricaoPrioritized(descricao);
    }
}
