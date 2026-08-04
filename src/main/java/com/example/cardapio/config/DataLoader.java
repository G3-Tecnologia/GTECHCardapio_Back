package com.example.cardapio.config;

import com.example.cardapio.model.Grupo;
import com.example.cardapio.repository.GrupoRepository;
import com.example.cardapio.repository.ProdutoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataLoader {

        @Bean
        CommandLineRunner startDatabase(GrupoRepository grupoRepository, ProdutoRepository produtoRepository) {
                return args -> {
                        if (grupoRepository.count() == 0) {
                                // Criando Grupos
                                Grupo grupoPizzas = new Grupo("Pizzas",
                                                "https://images.unsplash.com/photo-1513104890138-7c749659a591");
                                Grupo grupoBurgers = new Grupo("Hambúrgueres",
                                                "https://images.unsplash.com/photo-1568901346375-23c9450c58cd");
                                Grupo grupoBebidas = new Grupo("Bebidas",
                                                "https://images.unsplash.com/photo-1613478223719-2ab802602423");

                                grupoRepository.saveAll(List.of(grupoPizzas, grupoBurgers, grupoBebidas));

                                /*
                                // Criando Produtos para Pizzas
                                Produto pizza1 = new Produto("Pizza Margherita",
                                                new BigDecimal("45.00"),
                                                "https://images.unsplash.com/photo-1574071318508-1cdbad80ad50",
                                                grupoPizzas);

                                // Criando Produtos para Burgers
                                Produto burger1 = new Produto("Hambúrguer Gourmet",
                                                new BigDecimal("32.50"),
                                                "https://images.unsplash.com/photo-1568901346375-23c9450c58cd",
                                                grupoBurgers);

                                // Exemplo de Promoção Ativa para o Hambúrguer
                                burger1.setPromocao("S");
                                burger1.setPrecoPromocional(new BigDecimal("25.00"));
                                burger1.setDataInicioPromocao(LocalDateTime.now().minusDays(1)); // Começou ontem
                                burger1.setDataFimPromocao(LocalDateTime.now().plusDays(7)); // Termina em 7 dias

                                // Criando Produtos para Bebidas
                                Produto suco1 = new Produto("Suco de Laranja", 
                                                new BigDecimal("12.00"),
                                                "https://images.unsplash.com/photo-1613478223719-2ab802602423",
                                                grupoBebidas);

                                produtoRepository.saveAll(List.of(pizza1, burger1, suco1));
                                */

                                System.out.println("Banco de dados populado com grupos e produtos iniciais!");
                        }
                };
        }
}
