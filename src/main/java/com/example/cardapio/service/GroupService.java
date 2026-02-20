package com.example.cardapio.service;

import com.example.cardapio.model.Grupo;
import com.example.cardapio.repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    private final GrupoRepository grupoRepository;

    @Autowired
    public GroupService(GrupoRepository grupoRepository) {
        this.grupoRepository = grupoRepository;
    }

    public List<Grupo> listarGrupos() {
        return grupoRepository.findAll();
    }

    public Grupo salvarGrupo(Grupo grupo) {
        return grupoRepository.save(grupo);
    }
}
