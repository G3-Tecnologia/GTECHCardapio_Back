package com.example.cardapio.controller;

import com.example.cardapio.dto.StatusItemPedidoDTO;
import com.example.cardapio.dto.VendaItemDTO;
import com.example.cardapio.service.SaleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/cardapio/venda")
@CrossOrigin(origins = "*")
public class SaleController {

    private final SaleService service;
    private final ObjectMapper objectMapper;

    /** Lista de emitters SSE conectados no momento. */
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @Autowired
    public SaleController(SaleService service) {
        this.service = service;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    /** Registra um novo pedido. */
    @PostMapping
    public com.example.cardapio.model.VendaCabecalho registrarVenda(
            @RequestParam("idMesa") Long idMesa,
            @RequestBody List<VendaItemDTO> itens) {
        return service.registrarVenda(idMesa, itens);
    }

    /**
     * GET /cardapio/venda/ativos
     * Retorna snapshot atual dos pedidos ativos com nome do produto de
     * produto.DESCRICAOPDV.
     */
    @GetMapping("/ativos")
    public List<StatusItemPedidoDTO> getPedidosAtivos() {
        return service.listarPedidosAtivos();
    }

    /**
     * GET /cardapio/venda/{id}/status
     * Retorna o status atual de cada item de um pedido específico.
     * Usado pelo front-end do cliente para acompanhar o pedido em tempo real.
     */
    @GetMapping("/{id}/status")
    public List<com.example.cardapio.dto.StatusItemPedidoDTO> getStatusPedido(@PathVariable Long id) {
        return service.getStatusPedido(id);
    }

    /**
     * GET /cardapio/venda/stream
     * Abre uma conexão SSE (Server-Sent Events) que recebe os pedidos ativos em
     * tempo real,
     * com atualização automática a cada 3 segundos.
     *
     * Uso no front-end:
     * const source = new
     * EventSource('http://localhost:8081/cardapio/venda/stream');
     * source.addEventListener('pedidos-ativos', e => { ... JSON.parse(e.data) ...
     * });
     */
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamPedidosAtivos() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);

        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        emitter.onError(e -> emitters.remove(emitter));

        emitters.add(emitter);

        // Envia o estado atual imediatamente ao conectar
        try {
            List<StatusItemPedidoDTO> ativos = service.listarPedidosAtivos();
            emitter.send(SseEmitter.event()
                    .name("pedidos-ativos")
                    .data(objectMapper.writeValueAsString(ativos)));
        } catch (IOException e) {
            emitters.remove(emitter);
            emitter.completeWithError(e);
        }

        return emitter;
    }

    /**
     * Tarefa agendada: a cada 3 segundos, faz broadcast dos pedidos ativos
     * para todos os clientes SSE conectados.
     */
    @Scheduled(fixedDelay = 3000)
    public void broadcastPedidosAtivos() {
        if (emitters.isEmpty())
            return;

        List<StatusItemPedidoDTO> ativos;
        try {
            ativos = service.listarPedidosAtivos();
        } catch (Exception e) {
            return;
        }

        String json;
        try {
            json = objectMapper.writeValueAsString(ativos);
        } catch (Exception e) {
            return;
        }

        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event()
                        .name("pedidos-ativos")
                        .data(json));
            } catch (IOException ex) {
                emitter.completeWithError(ex);
                emitters.remove(emitter);
            }
        }
    }
}
