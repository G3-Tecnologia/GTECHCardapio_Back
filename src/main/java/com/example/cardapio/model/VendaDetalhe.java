package com.example.cardapio.model;

import jakarta.persistence.*;

@Entity
@Table(name = "gc_venda_detalhe")
public class VendaDetalhe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantidade;

    @ManyToOne
    @JoinColumn(name = "gc_venda_cabecalho_id")
    @com.fasterxml.jackson.annotation.JsonBackReference
    private VendaCabecalho vendaCabecalho;

    @Column(name = "ID_ATENDENTE")
    private Long idAtendente;

    @Column(name = "PRODUTO_ID")
    private Long produtoId;

    @Column(name = "CANCELADO")
    private Boolean cancelado;

    @Column(name = "PRODUTO_DESCRICAO")
    private String produtoDescricao;

    @Column(name = "OBSERVACAO")
    private String observacao;

    @Column(name = "VALORPRODUTO")
    private Double valorProduto;

    @Column(name = "id_gc_link_mesa_comanda")
    private Long idGcLinkMesaComanda;

    public Long getIdGcLinkMesaComanda() {
        return idGcLinkMesaComanda;
    }

    public void setIdGcLinkMesaComanda(Long idGcLinkMesaComanda) {
        this.idGcLinkMesaComanda = idGcLinkMesaComanda;
    }

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

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
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

    public Boolean getCancelado() {
        return cancelado;
    }

    public void setCancelado(Boolean cancelado) {
        this.cancelado = cancelado;
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

    public Double getValorProduto() {
        return valorProduto;
    }

    public void setValorProduto(Double valorProduto) {
        this.valorProduto = valorProduto;
    }
}
