package com.example.cardapio.service;

import com.example.cardapio.model.Grupo;
import com.example.cardapio.model.Produto;
import com.example.cardapio.repository.GrupoRepository;
import com.example.cardapio.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CardapioService {

    private final ProdutoRepository produtoRepository;
    private final GrupoRepository grupoRepository;

    @Autowired
    public CardapioService(ProdutoRepository produtoRepository, GrupoRepository grupoRepository) {
        this.produtoRepository = produtoRepository;
        this.grupoRepository = grupoRepository;
    }

    public List<Grupo> listarGrupos() {
        return grupoRepository.findAll();
    }

    public Grupo salvarGrupo(Grupo grupo) {
        return grupoRepository.save(grupo);
    }

    public List<Produto> listarProdutosPorGrupo(Long grupoId) {
        return produtoRepository.findByGrupoId(grupoId);
    }

    public Produto salvarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    public List<Produto> listarTodosProdutos() {
        return produtoRepository.findAll();
    }

    public List<Produto> listarProdutosPromocionais() {
        return produtoRepository.findPromocionais(LocalDateTime.now());
    }

    public List<Produto> pesquisarProdutos(String descricao) {
        return produtoRepository.findByDescricaoPrioritized(descricao);
    }
}
