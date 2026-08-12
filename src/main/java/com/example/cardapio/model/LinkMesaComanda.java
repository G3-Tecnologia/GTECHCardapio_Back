package com.example.cardapio.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "gc_link_mesa_comanda")
public class LinkMesaComanda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_mesa")
    private Long idMesa;

    @Column(name = "id_gc_venda_cabecalho")
    private Long idGcVendaCabecalho;

    @Column(name = "data_hora_vinculo")
    private LocalDateTime dataHoraVinculo;

    @Column(name = "nome_cliente")
    private String nomeCliente;

    @Column(name = "telefone_cliente")
    private String telefoneCliente;

    public LinkMesaComanda() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(Long idMesa) {
        this.idMesa = idMesa;
    }

    public Long getIdGcVendaCabecalho() {
        return idGcVendaCabecalho;
    }

    public void setIdGcVendaCabecalho(Long idGcVendaCabecalho) {
        this.idGcVendaCabecalho = idGcVendaCabecalho;
    }

    public LocalDateTime getDataHoraVinculo() {
        return dataHoraVinculo;
    }

    public void setDataHoraVinculo(LocalDateTime dataHoraVinculo) {
        this.dataHoraVinculo = dataHoraVinculo;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getTelefoneCliente() {
        return telefoneCliente;
    }

    public void setTelefoneCliente(String telefoneCliente) {
        this.telefoneCliente = telefoneCliente;
    }
}
