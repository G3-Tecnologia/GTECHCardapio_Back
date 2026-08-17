package com.example.cardapio.config;

import com.example.cardapio.model.Grupo;
import com.example.cardapio.repository.GrupoRepository;
import com.example.cardapio.repository.ProdutoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataLoader {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

    @Bean
    CommandLineRunner startDatabase(GrupoRepository grupoRepository, ProdutoRepository produtoRepository) {
        return args -> {
            if (grupoRepository.count() == 0) {
                // Criando Grupos Iniciais caso o banco esteja limpo
                Grupo grupoPizzas = new Grupo("Pizzas",
                                "https://images.unsplash.com/photo-1513104890138-7c749659a591");
                Grupo grupoBurgers = new Grupo("Hambúrgueres",
                                "https://images.unsplash.com/photo-1568901346375-23c9450c58cd");
                Grupo grupoBebidas = new Grupo("Bebidas",
                                "https://images.unsplash.com/photo-1613478223719-2ab802602423");

                grupoRepository.saveAll(List.of(grupoPizzas, grupoBurgers, grupoBebidas));
                log.info("Banco de dados inicializado com grupos padrão.");
            }
        };
    }
}
