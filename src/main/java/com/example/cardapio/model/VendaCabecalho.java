package com.example.cardapio.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "gc_venda_cabecalho")
public class VendaCabecalho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_venda")
    private LocalDateTime dataVenda;

    @Column(name = "valor_total")
    private Double valorTotal;

    @Column(name = "atendente_id")
    private Long atendenteId;

    @OneToMany(mappedBy = "vendaCabecalho", cascade = CascadeType.ALL)
    @com.fasterxml.jackson.annotation.JsonManagedReference
    private List<VendaDetalhe> detalhes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDateTime dataVenda) {
        this.dataVenda = dataVenda;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Long getAtendenteId() {
        return atendenteId;
    }

    public void setAtendenteId(Long atendenteId) {
        this.atendenteId = atendenteId;
    }

    public List<VendaDetalhe> getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(List<VendaDetalhe> detalhes) {
        this.detalhes = detalhes;
    }
}
