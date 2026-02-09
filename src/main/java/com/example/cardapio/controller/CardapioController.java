package com.example.cardapio.controller;

import com.example.cardapio.model.Grupo;
import com.example.cardapio.model.Produto;
import com.example.cardapio.service.CardapioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cardapio")
@CrossOrigin(origins = "*")
public class CardapioController {

    private final CardapioService service;

    @Autowired
    public CardapioController(CardapioService service) {
        this.service = service;
    }

    @GetMapping("/ping")
    public String ping() {
        return "Controller Cardapio está ativo!";
    }

    @GetMapping("/grupos")
    public List<Grupo> listarGrupos() {
        return service.listarGrupos();
    }

    @PostMapping("/grupos")
    public Grupo adicionarGrupo(@RequestBody Grupo grupo) {
        return service.salvarGrupo(grupo);
    }

    @GetMapping("/produtos/grupo/{id}")
    public List<Produto> listarProdutosPorGrupo(@PathVariable Long id) {
        return service.listarProdutosPorGrupo(id);
    }

    @GetMapping("/produtos")
    public List<Produto> listarTodosProdutos() {
        return service.listarTodosProdutos();
    }

    @GetMapping("/produtos/promocionais")
    public List<Produto> listarProdutosPromocionais() {
        return service.listarProdutosPromocionais();
    }

    @PostMapping("/produtos")
    public Produto adicionarProduto(@RequestBody Produto produto) {
        return service.salvarProduto(produto);
    }

    @GetMapping("/busca")
    public List<Produto> buscarProdutos(@RequestParam String descricao) {
        System.out.println("Buscando por: " + descricao);
        return service.pesquisarProdutos(descricao);
    }
}
