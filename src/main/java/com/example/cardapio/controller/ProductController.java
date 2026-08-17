package com.example.cardapio.controller;

import com.example.cardapio.model.Produto;
import com.example.cardapio.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cardapio/produtos")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    private final ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/grupo/{id}")
    public List<Produto> listarProdutosPorGrupo(@PathVariable Long id) {
        return service.listarProdutosPorGrupo(id);
    }

    @GetMapping
    public List<Produto> listarTodosProdutos() {
        return service.listarTodosProdutos();
    }

    @GetMapping("/promocionais")
    public List<Produto> listarProdutosPromocionais() {
        return service.listarProdutosPromocionais();
    }

    @GetMapping("/busca")
    public List<Produto> buscarProdutos(@RequestParam String descricao) {
        log.debug("Buscando produto por descrição: {}", descricao);
        return service.pesquisarProdutos(descricao);
    }
}
