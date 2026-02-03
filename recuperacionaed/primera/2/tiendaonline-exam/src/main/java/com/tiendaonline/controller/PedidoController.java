package com.tiendaonline.controller;

import com.tiendaonline.model.Pedido;
import com.tiendaonline.service.interfaces.IPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controlador REST para gestionar Pedidos.
 */
@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private IPedidoService pedidoService;

    /**
     * Crea un pedido para un cliente.
     * 
     * @param clienteId ID del cliente.
     * @param payload   cuerpo JSON con el campo "estado".
     * @return pedido creado o error.
     */
    @PostMapping("/cliente/{clienteId}")
    public ResponseEntity<?> createPedido(@PathVariable Integer clienteId, @RequestBody Map<String, String> payload) {
        String estado = payload.get("estado");
        if (estado == null) {
            return ResponseEntity.badRequest().body("Estado es requerido");
        }
        try {
            Pedido pedido = pedidoService.crearPedido(clienteId, estado);
            return new ResponseEntity<>(pedido, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Obtiene un pedido por su ID.
     * 
     * @param id identificador del pedido.
     * @return pedido encontrado o error 404.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> getPedidoById(@PathVariable Integer id) {
        try {
            Pedido pedido = pedidoService.findById(id);
            return ResponseEntity.ok(pedido);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
