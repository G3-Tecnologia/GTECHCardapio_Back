package com.example.cardapio.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@org.hibernate.annotations.Immutable
@Table(name = "view_cheff_produto")
public class Produto {

    @Id
    private Long id;

    @Column(nullable = false, name = "name")
    @jakarta.validation.constraints.NotBlank(message = "O nome é obrigatório")
    private String name;

    @Column(name = "price")
    @jakarta.validation.constraints.NotNull(message = "O preço é obrigatório")
    @jakarta.validation.constraints.PositiveOrZero(message = "O preço deve ser positivo ou zero")
    private BigDecimal price;

    @Column(name = "preparationTime")
    private String preparationTime;

    private String imagem;

    @ManyToOne
    @JoinColumn(name = "groupId", nullable = false)
    @jakarta.validation.constraints.NotNull(message = "O grupo é obrigatório")
    private Grupo grupo;

    /*
     * TODO: Promoções serão reativadas no futuro na view
     * 
     * @Column(length = 1)
     * private String promocao;
     * 
     * @Column(name = "preco_promocional")
     * private BigDecimal precoPromocional;
     * 
     * @Column(name = "data_inicio_promocao")
     * private LocalDateTime dataInicioPromocao;
     * 
     * @Column(name = "data_fim_promocao")
     * private LocalDateTime dataFimPromocao;
     */

    public Produto() {
    }

    public Produto(String name, BigDecimal price, String preparationTime, String imagem, Grupo grupo) {
        this.name = name;
        this.price = price;
        this.preparationTime = preparationTime;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(String preparationTime) {
        this.preparationTime = preparationTime;
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

    /*
     * public String getPromocao() {
     * return promocao;
     * }
     * 
     * public void setPromocao(String promocao) {
     * this.promocao = promocao;
     * }
     * 
     * public BigDecimal getPrecoPromocional() {
     * return precoPromocional;
     * }
     * 
     * public void setPrecoPromocional(BigDecimal precoPromocional) {
     * this.precoPromocional = precoPromocional;
     * }
     * 
     * public LocalDateTime getDataInicioPromocao() {
     * return dataInicioPromocao;
     * }
     * 
     * public void setDataInicioPromocao(LocalDateTime dataInicioPromocao) {
     * this.dataInicioPromocao = dataInicioPromocao;
     * }
     * 
     * public LocalDateTime getDataFimPromocao() {
     * return dataFimPromocao;
     * }
     * 
     * public void setDataFimPromocao(LocalDateTime dataFimPromocao) {
     * this.dataFimPromocao = dataFimPromocao;
     * }
     */
}
