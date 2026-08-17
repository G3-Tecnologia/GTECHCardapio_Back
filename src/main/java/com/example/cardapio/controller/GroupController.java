package com.example.cardapio.controller;

import com.example.cardapio.model.Grupo;
import com.example.cardapio.service.GroupService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cardapio/grupos")
public class GroupController {

    private final GroupService service;

    @Autowired
    public GroupController(GroupService service) {
        this.service = service;
    }

    @GetMapping
    public List<Grupo> listarGrupos() {
        return service.listarGrupos();
    }

    @PostMapping
    public Grupo adicionarGrupo(@Valid @RequestBody Grupo grupo) {
        return service.salvarGrupo(grupo);
    }
}
