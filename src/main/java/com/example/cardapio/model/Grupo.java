package com.example.cardapio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "grupo")
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricao;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] imagem;

    @JsonIgnore
    @OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL)
    private List<Produto> produtos;

    public Grupo() {
    }

    public Grupo(String descricao, String imagem) {
        this.descricao = descricao;
        this.imagem = imagem.getBytes();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem.getBytes();
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }
}
