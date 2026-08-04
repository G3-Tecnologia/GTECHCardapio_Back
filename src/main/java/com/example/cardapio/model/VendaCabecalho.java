package com.example.cardapio.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "gc_venda_cabecalho")
public class VendaCabecalho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "vendaCabecalho", cascade = CascadeType.ALL)
    @com.fasterxml.jackson.annotation.JsonManagedReference
    private List<VendaDetalhe> detalhes;

    @Column(name = "ATENDENTE_ID")
    private Long atendenteId;

    @Column(name = "ID_MESA")
    private Long idMesa;

    @Column(name = "encerrada")
    private Boolean encerrada = false;

    @Column(name = "cancelada")
    private Boolean cancelada = false;

    @Column(name = "solicitado_conta")
    private Boolean solicitadoConta = false;

    @Column(name = "TAXA_GARCOM")
    private Double taxaGarcom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<VendaDetalhe> getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(List<VendaDetalhe> detalhes) {
        this.detalhes = detalhes;
    }

    public Long getAtendenteId() {
        return atendenteId;
    }

    public void setAtendenteId(Long atendenteId) {
        this.atendenteId = atendenteId;
    }

    public Long getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(Long idMesa) {
        this.idMesa = idMesa;
    }

    public Boolean getEncerrada() {
        return encerrada;
    }

    public void setEncerrada(Boolean encerrada) {
        this.encerrada = encerrada;
    }

    public Boolean getCancelada() {
        return cancelada;
    }

    public void setCancelada(Boolean cancelada) {
        this.cancelada = cancelada;
    }

    public Boolean getSolicitadoConta() {
        return solicitadoConta;
    }

    public void setSolicitadoConta(Boolean solicitadoConta) {
        this.solicitadoConta = solicitadoConta;
    }

    public Double getTaxaGarcom() {
        return taxaGarcom;
    }

    public void setTaxaGarcom(Double taxaGarcom) {
        this.taxaGarcom = taxaGarcom;
    }

}
