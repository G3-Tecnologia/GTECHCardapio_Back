package com.example.cardapio.controller;

import com.example.cardapio.model.Produto;
import com.example.cardapio.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cardapio/produtos")
@CrossOrigin(origins = "*")
public class ProductController {

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

    /*
    @GetMapping("/promocionais")
    public List<Produto> listarProdutosPromocionais() {
        return service.listarProdutosPromocionais();
    }

    @PostMapping
    public Produto adicionarProduto(@RequestBody Produto produto) {
        return service.salvarProduto(produto);
    }
    */

    @GetMapping("/busca")
    public List<Produto> buscarProdutos(@RequestParam String descricao) {
        System.out.println("Buscando por: " + descricao);
        return service.pesquisarProdutos(descricao);
    }
}
