package com.example.cardapio.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/cardapio/config")
public class ConfigController {

    @Value("${app.config.view-only}")
    private boolean viewOnly;

    @GetMapping
    public Map<String, Boolean> getConfig() {
        Map<String, Boolean> config = new HashMap<>();
        config.put("viewOnly", viewOnly);
        return config;
    }
}
