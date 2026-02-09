package com.example.cardapio.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private BigDecimal preco;

    @Column(name = "valor_venda")
    private BigDecimal valorVenda;

    @Column(length = 1)
    private String promocao;

    @Column(name = "preco_promocional")
    private BigDecimal precoPromocional;

    @Column(name = "data_inicio_promocao")
    private LocalDateTime dataInicioPromocao;

    @Column(name = "data_fim_promocao")
    private LocalDateTime dataFimPromocao;

    private String imagem;

    @ManyToOne
    @JoinColumn(name = "id_grupo", nullable = false)
    private Grupo grupo;

    public Produto() {
    }

    public Produto(String nome, String descricao, BigDecimal preco, String imagem, Grupo grupo) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.imagem = imagem;
        this.grupo = grupo;
        this.promocao = "N";
    }

    public Produto(String nome, String descricao, BigDecimal preco, BigDecimal valorVenda, String promocao,
            BigDecimal precoPromocional, String imagem, Grupo grupo) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.valorVenda = valorVenda;
        this.promocao = promocao;
        this.precoPromocional = precoPromocional;
        this.imagem = imagem;
        this.grupo = grupo;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public BigDecimal getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(BigDecimal valorVenda) {
        this.valorVenda = valorVenda;
    }

    public String getPromocao() {
        return promocao;
    }

    public void setPromocao(String promocao) {
        this.promocao = promocao;
    }

    public BigDecimal getPrecoPromocional() {
        return precoPromocional;
    }

    public void setPrecoPromocional(BigDecimal precoPromocional) {
        this.precoPromocional = precoPromocional;
    }

    public LocalDateTime getDataInicioPromocao() {
        return dataInicioPromocao;
    }

    public void setDataInicioPromocao(LocalDateTime dataInicioPromocao) {
        this.dataInicioPromocao = dataInicioPromocao;
    }

    public LocalDateTime getDataFimPromocao() {
        return dataFimPromocao;
    }

    public void setDataFimPromocao(LocalDateTime dataFimPromocao) {
        this.dataFimPromocao = dataFimPromocao;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }
}
