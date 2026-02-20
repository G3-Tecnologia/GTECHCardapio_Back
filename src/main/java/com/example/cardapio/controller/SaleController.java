package com.example.cardapio.controller;

import com.example.cardapio.dto.VendaItemDTO;
import com.example.cardapio.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cardapio/venda")
@CrossOrigin(origins = "*")
public class SaleController {

    private final SaleService service;

    @Autowired
    public SaleController(SaleService service) {
        this.service = service;
    }

    @PostMapping
    public com.example.cardapio.model.VendaCabecalho registrarVenda(@RequestBody List<VendaItemDTO> itens) {
        return service.registrarVenda(itens);
    }
}
