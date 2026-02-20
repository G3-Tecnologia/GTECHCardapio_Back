package com.example.cardapio.model;

import jakarta.persistence.*;

@Entity
@Table(name = "gc_venda_detalhe")
public class VendaDetalhe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "produto_descricao")
    private String produtoDescricao;

    private String observacao;

    private Integer quantidade;

    @ManyToOne
    @JoinColumn(name = "gc_venda_cabecalho_id")
    @com.fasterxml.jackson.annotation.JsonBackReference
    private VendaCabecalho vendaCabecalho;

    @Column(name = "valor_unitario")
    private Double valorUnitario;

    @Column(name = "VALORPRODUTO")
    private Double valorProduto;

    @Column(name = "ID_ATENDENTE")
    private Long idAtendente;

    @Column(name = "produto_id")
    private Long produtoId;

    public VendaCabecalho getVendaCabecalho() {
        return vendaCabecalho;
    }

    public void setVendaCabecalho(VendaCabecalho vendaCabecalho) {
        this.vendaCabecalho = vendaCabecalho;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProdutoDescricao() {
        return produtoDescricao;
    }

    public void setProdutoDescricao(String produtoDescricao) {
        this.produtoDescricao = produtoDescricao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public Double getValorProduto() {
        return valorProduto;
    }

    public void setValorProduto(Double valorProduto) {
        this.valorProduto = valorProduto;
    }

    public Long getIdAtendente() {
        return idAtendente;
    }

    public void setIdAtendente(Long idAtendente) {
        this.idAtendente = idAtendente;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }
}
