package com.example.cardapio.controller;

import com.example.cardapio.dto.*;
import com.example.cardapio.service.SaleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.validation.Valid;
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

    /**
     * GET /cardapio/venda/mesa/{idMesa}/status
     * Retorna se a mesa possui uma comanda em aberto (via query SELECT * FROM gc_venda_cabecalho WHERE id_mesa = :idMesa AND NOT ENCERRADA AND NOT CANCELADA).
     */
    @GetMapping("/mesa/{idMesa}/status")
    public MesaStatusResponseDTO getMesaStatus(@PathVariable Long idMesa) {
        return service.verificarStatusMesa(idMesa);
    }

    /**
     * GET /cardapio/venda/mesa/{idMesa}/pedidos
     * Retorna todos os pedidos da mesa agrupados por cliente, trazendo o nome do cliente.
     */
    @GetMapping("/mesa/{idMesa}/pedidos")
    public List<PedidoMesaGroupDTO> getPedidosMesa(@PathVariable Long idMesa) {
        return service.listarPedidosMesa(idMesa);
    }

    /**
     * POST /cardapio/venda/mesa/entrar
     * Cadastra a entrada do cliente na mesa, gerando um registro em gc_link_mesa_comanda.
     */
    @PostMapping("/mesa/entrar")
    public ClienteEntradaResponseDTO entrarNaMesa(@Valid @RequestBody ClienteEntradaDTO dto) {
        return service.entrarNaMesa(dto);
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
     * Retorna snapshot atual dos pedidos ativos com nome do produto de produto.DESCRICAOPDV.
     */
    @GetMapping("/ativos")
    public List<StatusItemPedidoDTO> getPedidosAtivos() {
        return service.listarPedidosAtivos();
    }

    /**
     * GET /cardapio/venda/{id}/status
     * Retorna o status atual de cada item de um pedido específico.
     */
    @GetMapping("/{id}/status")
    public List<StatusItemPedidoDTO> getStatusPedido(@PathVariable Long id) {
        return service.getStatusPedido(id);
    }

    /**
     * POST /cardapio/venda/mesa/{idMesa}/solicitar-conta
     * Marca a venda atual da mesa como solicitadoConta = true
     */
    @PostMapping("/mesa/{idMesa}/solicitar-conta")
    public com.example.cardapio.model.VendaCabecalho solicitarConta(@PathVariable Long idMesa) {
        return service.solicitarConta(idMesa);
    }

    /**
     * POST /cardapio/venda/solicitar-conta-parcial
     * Solicita conta parcial ou total vinculada aos itens / cliente.
     */
    @PostMapping("/solicitar-conta-parcial")
    public com.example.cardapio.model.VendaCabecalho solicitarContaParcial(@RequestBody SolicitacaoContaParcialDTO dto) {
        return service.solicitarContaParcial(dto);
    }

    /**
     * GET /cardapio/venda/stream
     * Abre uma conexão SSE (Server-Sent Events) que recebe os pedidos ativos em tempo real.
     */
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamPedidosAtivos() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);

        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        emitter.onError(e -> emitters.remove(emitter));

        emitters.add(emitter);

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
     * Tarefa agendada: a cada 3 segundos, faz broadcast dos pedidos ativos.
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
